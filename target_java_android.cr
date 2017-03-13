require "./target_java"

class JavaAndroidTarget < JavaTarget
  def gen
    @io << <<-END

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class API {

END

    @ast.custom_types.each do |custom_type|
      @io << ident generate_custom_type_interface(custom_type)
      @io << "\n\n"
    end

    @ast.operations.each do |op|
      args = op.args.map {|arg| "#{native_type arg.type} #{arg.name}" }
      args << "#{callback_type op.return_type} callback"
      @io << ident(String.build do |io|
        io << "void #{op.fnName}(#{args.join(", ")}) {\n"
        #   if op.args.size > 0
        #     @io << "  const args = {\n"
        #     op.args.each do |arg|
        #       @io << ident ident "#{arg.name}: #{type_to_json(arg.type, arg.name)},"
        #       @io << "\n"
        #     end
        #     @io << "  };\n"
        #   end

        #   @io << "  "
        #   @io << "const ret = " unless op.return_type.is_a? AST::VoidPrimitiveType
        #   @io << "await makeRequest({name: #{operation_name(op).inspect}, #{op.args.size > 0 ? "args" : "args: {}"}});\n"
        #   @io << ident "return " + type_from_json(op.return_type, "ret") + ";"
        #   @io << "\n"
        io << "}"
      end)
      @io << "\n\n"
    end

    @io << <<-END

    public interface Callback<T> {

    }

    public interface IntCallback {

    }

    public interface DoubleCallback {

    }

    public interface BooleanCallback {

    }

    public interface VoidCallback {

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

        private JSONObject device() throws JSONException {
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

        private String randomBytesHex(int len) {
            String str = new BigInteger(8 * len, random).toString(16);
            while (str.length() < len) str = "0" + str;
            return str;
        }

        private interface RequestCallback {
            void onResult(JSONObject result);
            void onError(String type, String message);
            void onFailure();
        }

        private void makeRequest(String name, JSONObject args, final RequestCallback callback) throws JSONException {
            JSONObject body = new JSONObject();
            body.put("id", randomBytesHex(16));
            body.put("device", device());
            body.put("name", name);
            body.put("args", args);

            Request request = new Request.Builder()
                    .url("https://" + baseUrl + "/" + name)
                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body.toString()))
                    .build();

            http.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    callback.onFailure();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() >= 500) {
                        Log.e("API Fatal", response.body().string());
                        callback.onFailure();
                        return;
                    }

                    try {
                        JSONObject body = new JSONObject(response.body().string());
                        if (!body.getBoolean("ok")) {
                            String type = body.getJSONObject("error").getString("type");
                            String message = body.getJSONObject("error").getString("message");
                            callback.onError(type, message);
                        } else {
                            callback.onResult(body.getJSONObject("result"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                }
            });
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
