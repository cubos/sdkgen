require "./target_java"

class JavaAndroidTarget < JavaTarget
  def gen
    @io << <<-END

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class API {

END

    @ast.type_definitions.each do |type_definition|
      @io << ident generate_type_definition_interface(type_definition)
      @io << "\n\n"
    end

    @ast.operations.each do |op|
      args = op.args.map {|arg| "final #{native_type arg.type} #{arg.name}" }
      args << "final #{callback_type op.return_type} callback"
      @io << ident(String.build do |io|
        io << "static public void #{op.pretty_name}(#{args.join(", ")}) {\n"
        io << ident(String.build do |io|
          if op.args.size == 0
            io << "JSONObject args = new JSONObject();"
          else
            io << <<-END
JSONObject args;
try {
    args = new JSONObject() {{

END
      op.args.each do |arg|
        io << ident ident "put(\"#{arg.name}\", #{type_to_json arg.type, arg.name});\n"
      end
          io << <<-END
    }};
} catch (JSONException e) {
    e.printStackTrace();
    callback.onFinished();
    callback.onError("bug", e.getMessage());
    return;
}
END
          end
          io << <<-END

Internal.makeRequest(#{op.pretty_name.inspect}, args, new Internal.RequestCallback() {
    @Override
    public void onResult(final JSONObject result) {
        callback.onFinished();

END
          if op.return_type.is_a? AST::VoidPrimitiveType
            io << <<-END
        callback.onResult();

END
          else
            io << <<-END
        try {
            callback.onResult(#{type_from_json op.return_type, get_field_from_json_object(op.return_type, "result", "result".inspect)});
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onError("bug", e.getMessage());
        }
END
          end
          io << <<-END
    }

    @Override
    public void onError(String type, String message) {
        callback.onFinished();
        callback.onError(type, message);
    }

    @Override
    public void onFailure(String message) {
        callback.onFinished();
        callback.onError("Connection", message);
    }
});
END
        end)
        io << "}"
      end)
      @io << "\n\n"
    end

    @io << <<-END

    public interface Callback<T> {
        void onFinished();
        void onResult(T result);
        void onError(String type, String message);
    }

    public interface IntCallback {
        void onFinished();
        void onResult(int result);
        void onError(String type, String message);
    }

    public interface DoubleCallback {
        void onFinished();
        void onResult(double result);
        void onError(String type, String message);
    }

    public interface BooleanCallback {
        void onFinished();
        void onResult(boolean result);
        void onError(String type, String message);
    }

    public interface VoidCallback {
        void onFinished();
        void onResult();
        void onError(String type, String message);
    }

    private static class Internal {
        private static final String baseUrl = #{@ast.options.url.inspect};
        private static final OkHttpClient http = new OkHttpClient();
        private static final SecureRandom random = new SecureRandom();

        private static Context context() {
            try {
                final Class<?> activityThreadClass =
                        Class.forName("android.app.ActivityThread");
                final Method method = activityThreadClass.getMethod("currentApplication");
                Application app = (Application)method.invoke(null, (Object[]) null);
                if (app == null)
                    throw new RuntimeException("");
                return app;
            } catch (final ClassNotFoundException | NoSuchMethodException |
                    IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException("");
            }
        }

        private static String language() {
            Locale loc = Locale.getDefault();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return loc.toLanguageTag();
            }

            final char SEP = '-';
            String language = loc.getLanguage();
            String region = loc.getCountry();
            String variant = loc.getVariant();

            if (language.equals("no") && region.equals("NO") && variant.equals("NY")) {
                language = "nn";
                region = "NO";
                variant = "";
            }

            if (language.isEmpty() || !language.matches("\\\\p{Alpha}{2,8}")) {
                language = "und";
            } else if (language.equals("iw")) {
                language = "he";
            } else if (language.equals("in")) {
                language = "id";
            } else if (language.equals("ji")) {
                language = "yi";
            }

            if (!region.matches("\\\\p{Alpha}{2}|\\\\p{Digit}{3}")) {
                region = "";
            }

            if (!variant.matches("\\\\p{Alnum}{5,8}|\\\\p{Digit}\\\\p{Alnum}{3}")) {
                variant = "";
            }

            StringBuilder bcp47Tag = new StringBuilder(language);
            if (!region.isEmpty()) {
                bcp47Tag.append(SEP).append(region);
            }
            if (!variant.isEmpty()) {
                bcp47Tag.append(SEP).append(variant);
            }

            return bcp47Tag.toString();
        }

        private static JSONObject device() throws JSONException {
            JSONObject device = new JSONObject();
            device.put("platform", "android");
            device.put("fingerprint", "" + Settings.Secure.ANDROID_ID);
            device.put("platformVersion", "Android " + Build.VERSION.RELEASE + "(API " + Build.VERSION.SDK_INT + ") on " + Build.BRAND + " " + Build.MODEL);
            try {
                device.put("version", context().getPackageManager().getPackageInfo(context().getPackageName(), 0).versionName);
            } catch (PackageManager.NameNotFoundException e) {
                device.put("version", "unknown");
            }
            device.put("language", language());
            SharedPreferences pref = context().getSharedPreferences("api", Context.MODE_PRIVATE);
            if (pref.contains("deviceId"))
                device.put("id", pref.getString("deviceId", null));
            return device;
        }

        private static String randomBytesHex(int len) {
            String str = new BigInteger(8 * len, random).toString(16);
            while (str.length() < len) str = "0" + str;
            return str;
        }

        private interface RequestCallback {
            void onResult(JSONObject result);
            void onError(String type, String message);
            void onFailure(String message);
        }

        private static void makeRequest(String name, JSONObject args, final RequestCallback callback) {
            JSONObject body = new JSONObject();
            try {
                body.put("id", randomBytesHex(16));
                body.put("device", device());
                body.put("name", name);
                body.put("args", args);
            } catch (JSONException e) {
                e.printStackTrace();
                callback.onError("bug", e.getMessage());
            }

            Request request = new Request.Builder()
                    .url("https://" + baseUrl + "/" + name)
                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body.toString()))
                    .build();

            http.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            e.printStackTrace();
                            callback.onFailure(e.getMessage());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (response.code() >= 500) {
                                try {
                                    Log.e("API Fatal", response.body().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                callback.onFailure("HTTP " + response.code());
                                return;
                            }

                            try {
                                JSONObject body = new JSONObject(response.body().string());

                                SharedPreferences pref = context().getSharedPreferences("api", Context.MODE_PRIVATE);
                                pref.edit().putString("deviceId", body.getString("deviceId")).apply();

                                if (!body.getBoolean("ok")) {
                                    String type = body.getJSONObject("error").getString("type");
                                    String message = body.getJSONObject("error").getString("message");
                                    callback.onError(type, message);
                                } else {
                                    callback.onResult(body);
                                }
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                                callback.onError("bug", e.getMessage());
                            }
                        }
                    });
                }
            });
        }

        static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
        static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        static {
            dateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        }

        static Calendar toCalendar(Date date){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }

        static Calendar decodeDateTime(String str) {
            try {
                return toCalendar(dateTimeFormat.parse(str));
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        static Calendar decodeDate(String str) {
            try {
                return toCalendar(dateFormat.parse(str));
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        static String encodeDateTime(Calendar cal) {
            return dateTimeFormat.format(cal.getTime());
        }

        static String encodeDate(Calendar cal) {
            return dateFormat.format(cal.getTime());
        }
    }
}
END
  end

  def callback_type(t : AST::Type)
    case t
    when AST::IntPrimitiveType;   "IntCallback"
    when AST::UIntPrimitiveType;  "IntCallback"
    when AST::FloatPrimitiveType; "DoubleCallback"
    when AST::BoolPrimitiveType;  "BooleanCallback"
    when AST::VoidPrimitiveType;  "VoidCallback"
    else
      "Callback<#{native_type_not_primitive(t)}>"
    end
  end
end

Target.register(JavaAndroidTarget, language: "java", is_server: false)
