require "./java"

class JavaAndroidTarget < JavaTarget
  def gen
    @io << <<-END

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.anupcowkur.reservoir.ReservoirPutCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class API {
    public interface GlobalRequestCallback {
        public void onResult(final String method, final Error error, final JSONObject result, final Callback<JSONObject> callback);
    };

    static public Calls calls = new Calls();
    static public Application application;
    static public boolean useStaging = false;
    static public Context serviceContext = null;
    static public GlobalRequestCallback globalCallback = new GlobalRequestCallback() {
        @Override
        public void onResult(final String method, final Error error, final JSONObject result, final Callback<JSONObject> callback) {
            callback.onResult(error, result);
        }
    };

    static public void initialize(Application application) {
        Internal.initialize(application);
    }

    static public int Default = 0;
    static public int Loading = 1;
    static public int Cache = 2;

END

    # INIT CREATING API'S CALLS INTERFACE
    @io << <<-END
public interface APICalls {

END

    @ast.operations.each do |op|
      args = op.args.map { |arg| "final #{native_type arg.type} #{mangle arg.name}" }
      args << "final #{callback_type op.return_type} callback"
      @io << ident(String.build do |io|
        io << "public void #{mangle op.pretty_name}(#{args.join(", ")});\n"
      end)
    end

    @io << <<-END
}
END
    # END CREATING API'S CALLS INTERFACE

    @ast.struct_types.each do |t|
      @io << ident generate_struct_type(t)
      @io << "\n\n"
    end

    @ast.enum_types.each do |t|
      @io << ident generate_enum_type(t)
      @io << "\n\n"
    end

    # INIT CREATING CALLS
    @io << <<-END

    public static class Calls implements APICalls {

END

    @ast.operations.each do |op|
      args = op.args.map { |arg| "final #{native_type arg.type} #{mangle arg.name}" }
      args << "final #{callback_type op.return_type} callback"
      @io << ident(String.build do |io|
        io << "@Override \n"
        io << "public void #{mangle op.pretty_name}(#{args.join(", ")}) {\n"
        io << "    #{mangle op.pretty_name}(#{(op.args.map { |arg| mangle arg.name } + ["0", "callback"]).join(", ")});\n"
        io << "}"
      end)
      @io << "\n\n"
      args = args[0..-2] + ["final int flags", args[-1]]
      @io << ident(String.build do |io|
        io << "public void #{mangle op.pretty_name}(#{args.join(", ")}) {\n"
        io << ident(String.build do |io|
          if op.args.size == 0
            io << "final JSONObject args = new JSONObject();"
          else
            io << <<-END
final JSONObject args;
try {
    args = new JSONObject() {{

END
            op.args.each do |arg|
              io << ident ident "put(\"#{arg.name}\", #{type_to_json arg.type, arg.name});\n"
            end
            io << <<-END
    }};
} catch (final JSONException e) {
    e.printStackTrace();
    globalCallback.onResult(#{op.pretty_name.inspect}, new Error() {{type = ErrorType.Fatal; message = e.getMessage();}}, null, new Callback<JSONObject>() {
        @Override
        public void onResult(final Error error,final JSONObject result) {

END
            if op.return_type.is_a? AST::VoidPrimitiveType
              io << <<-END
            callback.onResult(error);

END
            else
              io << <<-END
            if (error != null) {
                callback.onResult(error, null);
            } else {
                try {
                    callback.onResult(null, #{type_from_json op.return_type, "result", "result".inspect});
                } catch (final JSONException e) {
                    e.printStackTrace();
                    callback.onResult(new Error() {{type = ErrorType.Fatal; message = e.getMessage();}}, null);
                }
            }
END
            end
            io << <<-END

        }
    });
    return;
}

END
          end
          io << <<-END

Internal.RequestCallback reqCallback = new Internal.RequestCallback() {
    @Override
    public void onResult(final Error error, final JSONObject result) {

END
          if op.return_type.is_a? AST::VoidPrimitiveType
            io << <<-END
        globalCallback.onResult(#{op.pretty_name.inspect}, error, null, new Callback<JSONObject>() {
            @Override
            public void onResult(final Error error,final JSONObject result) {
                callback.onResult(error);
            }
        });


END
          else
            io << <<-END
        globalCallback.onResult(#{op.pretty_name.inspect}, error, result, new Callback<JSONObject>() {
            @Override
            public void onResult(final Error error,final JSONObject result) {
                if (error != null) {
                    callback.onResult(error, null);
                } else {
                    try {
                        callback.onResult(null, #{type_from_json op.return_type, "result", "result".inspect});
                    } catch (final JSONException e) {
                        e.printStackTrace();
                        callback.onResult(new Error() {{type = ErrorType.Fatal; message = e.getMessage();}}, null);
                    }
                }
            }
        });
END
          end
          io << <<-END
    }
};
Internal.initialize();
if ((flags & API.Loading) != 0) {
    reqCallback = Internal.withLoading(reqCallback);
}
if ((flags & API.Cache) != 0) {
    String signature = "#{mangle op.pretty_name}:" + Internal.hash(args.toString());
    final Internal.RequestCallback reqCallbackPure = reqCallback;
    final Internal.RequestCallback reqCallbackSaveCache = Internal.withSavingOnCache(signature, reqCallback);
    Reservoir.getAsync(signature, String.class, new ReservoirGetCallback<String>() {
        @Override
        public void onSuccess(String json) {
            try {
                JSONObject data = new JSONObject(json);
                Calendar time = Internal.decodeDateTime(data.getString("time"));
                callback.cacheAge = (int)((new GregorianCalendar().getTimeInMillis() - time.getTimeInMillis()) / 1000);
                callback.repeatWithoutCacheRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Internal.makeRequest(#{mangle(op.pretty_name).inspect}, args, new Internal.RequestCallback() {
                            @Override
                            public void onResult(Error error, JSONObject result) {
                                callback.cacheAge = 0;
                                reqCallbackSaveCache.onResult(error, result);
                            }
                        });
                    }
                };
                reqCallbackPure.onResult(null, data);
            } catch (JSONException e) {
                Internal.makeRequest(#{mangle(op.pretty_name).inspect}, args, reqCallbackSaveCache);
            }
        }

        @Override
        public void onFailure(Exception e) {
            Internal.makeRequest(#{mangle(op.pretty_name).inspect}, args, reqCallbackSaveCache);
        }
    });
} else {
    Internal.makeRequest(#{mangle(op.pretty_name).inspect}, args, reqCallback);
}

END
        end)
        io << "}"
      end)
      @io << "\n\n"
    end

    @io << <<-END

}

END

    # END CREATING CALLS

    @io << <<-END
    public static class Error {
        public ErrorType type;
        public String message;
    }

    public static class BaseCallback {
        public int cacheAge = 0;
        Runnable repeatWithoutCacheRunnable;

        protected void repeatWithoutCache() {
            repeatWithoutCacheRunnable.run();
        }
    }

    public abstract static class Callback<T> extends BaseCallback {
        public abstract void onResult(final Error error, final T result);
    }

    public abstract static class VoidCallback extends BaseCallback {
        public abstract void onResult(final Error error);
    }

    static public void getDeviceId(final Callback<String> callback) {
        SharedPreferences pref = Internal.context().getSharedPreferences("api", Context.MODE_PRIVATE);
        if (pref.contains("deviceId"))
            callback.onResult(null, pref.getString("deviceId", null));
        else {
            calls.ping(API.Default, new Callback<String>() {
                @Override
                public void onResult(Error error, String result) {
                    if (error != null)
                        callback.onResult(error, null);
                    else
                        getDeviceId(callback);
                }
            });
        }
    }

    static public OkHttpClient getHttpClient() {
        Internal.initialize();
        return Internal.getHttpClientForThirdParty();
    }

    private static class Internal {
        static String baseUrl = #{@ast.options.url.inspect};
        static OkHttpClient http = null;
        static ConnectionPool connectionPool;
        static SecureRandom random = new SecureRandom();
        static boolean initialized = false;
        static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
        static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        static Application application;

        static {
            dateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            createHttpClient();
        }

        static OkHttpClient getHttpClientForThirdParty() {
            if (http == null) {
                createHttpClient();
            }

            return http;
        }

        static void createHttpClient() {
            connectionPool = new ConnectionPool(100, 5, TimeUnit.MINUTES);

            TrustManagerFactory trustManagerFactory;
            try {
                trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init((KeyStore) null);
            } catch (NoSuchAlgorithmException | KeyStoreException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

            SSLSocketFactory sslSocketFactory;
            try {
                sslSocketFactory = new TLSSocketFactory();
            } catch (KeyManagementException | NoSuchAlgorithmException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(200);
            dispatcher.setMaxRequestsPerHost(200);

            http = new OkHttpClient.Builder()
                    .connectionPool(connectionPool)
                    .dispatcher(dispatcher)
                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .build();
        }

        static void initialize() {
            if (initialized) return;
            initialized = true;
            try {
                Reservoir.init(context(), 10 * 1024 * 1024 /* 10 MB */);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        static void initialize(Application app) {
            if (application == null && app != null) {
                application = app;
            }
            initialize();
        }

        static Context context() {
            if (application == null) {
                try {
                    Class<?> activityThreadClass =
                            Class.forName("android.app.ActivityThread");
                    Method method = activityThreadClass.getMethod("currentApplication");
                    Application app = (Application)method.invoke(null, (Object[]) null);
                    if (app == null) {
                        if (API.serviceContext != null)
                            return API.serviceContext;
                        else
                            throw new RuntimeException("Failed to get Application, use API.serviceContext to provide a Context");
                    }
                    return app;
                } catch (ClassNotFoundException | NoSuchMethodException |
                        IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException("Failed to get application from android.app.ActivityThread");
                }
            } else {
                return application;
            }
        }

        static Activity getCurrentActivity() {
            try {
                Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
                java.lang.Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
                Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
                activitiesField.setAccessible(true);

                @SuppressWarnings("unchecked")
                Map<java.lang.Object, java.lang.Object> activities = (Map<java.lang.Object, java.lang.Object>) activitiesField.get(activityThread);

                if (activities == null)
                    return null;

                for (java.lang.Object activityRecord : activities.values()) {
                    Class activityRecordClass = activityRecord.getClass();
                    Field pausedField = activityRecordClass.getDeclaredField("paused");
                    pausedField.setAccessible(true);
                    if (!pausedField.getBoolean(activityRecord)) {
                        Field activityField = activityRecordClass.getDeclaredField("activity");
                        activityField.setAccessible(true);
                        return (Activity) activityField.get(activityRecord);
                    }
                }

                return null;
            } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException |
                    IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException("Failed to get current activity from android.app.ActivityThread");
            }
        }

        static String language() {
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

        @SuppressLint("HardwareIds")
        static JSONObject device() throws JSONException {
            JSONObject device = new JSONObject();
            device.put("type", "android");
            device.put("fingerprint", "" + Settings.Secure.getString(context().getContentResolver(), Settings.Secure.ANDROID_ID));
            device.put("platform", new JSONObject() {{
                put("version", Build.VERSION.RELEASE);
                put("sdkVersion", Build.VERSION.SDK_INT);
                put("brand", Build.BRAND);
                put("model", Build.MODEL);
            }});
            try {
                device.put("version", context().getPackageManager().getPackageInfo(context().getPackageName(), 0).versionName);
            } catch (PackageManager.NameNotFoundException e) {
                device.put("version", "unknown");
            }
            device.put("language", language());
            device.put("screen", new JSONObject() {{
                WindowManager manager = (WindowManager) context().getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                put("width", size.x);
                put("height", size.y);
            }});
            SharedPreferences pref = context().getSharedPreferences("api", Context.MODE_PRIVATE);
            if (pref.contains("deviceId"))
                device.put("id", pref.getString("deviceId", null));
            return device;
        }

        final private static char[] hexArray = "0123456789abcdef".toCharArray();
        static String bytesToHex(byte[] bytes) {
            char[] hexChars = new char[bytes.length * 2];
            for ( int j = 0; j < bytes.length; j++ ) {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }
            return new String(hexChars);
        }

        static String randomBytesHex(int len) {
            byte[] bytes = new byte[len];
            random.nextBytes(bytes);
            return bytesToHex(bytes);
        }

        static String hash(String message) {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
            return bytesToHex(digest.digest(message.getBytes()));
        }

        interface RequestCallback {
            void onResult(Error error, JSONObject result);
        }

        static RequestCallback withLoading(final RequestCallback callback) {
            final ProgressDialog[] progress = new ProgressDialog[] {null};
            final Timer timer = new Timer();
            final TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Activity currentActivity = getCurrentActivity();
                            if (currentActivity != null) {
                                progress[0] = ProgressDialog.show(currentActivity, "Aguarde", "Carregando...", true, true);
                            }
                        }
                    });
                }
            };
            timer.schedule(task, 800);
            return new RequestCallback() {
                @Override
                public void onResult(Error error, JSONObject result) {
                    timer.cancel();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (progress[0] != null)
                                progress[0].dismiss();
                        }
                    });
                    callback.onResult(error, result);
                }
            };
        }

        static RequestCallback withSavingOnCache(final String signature, final RequestCallback callback) {
            return new RequestCallback() {
                @Override
                public void onResult(Error error, final JSONObject result) {
                    if (error == null) {
                        Reservoir.putAsync(signature, new JSONObject() {{
                            try {
                                put("time", encodeDateTime(new GregorianCalendar()));
                                put("result", result.getJSONObject("result"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }}.toString(), new ReservoirPutCallback() {
                            @Override
                            public void onSuccess() {}
                            @Override
                            public void onFailure(Exception e) {}
                        });
                    }
                    callback.onResult(error, result);
                }
            };
        }

        static void makeRequest(String name, JSONObject args, final RequestCallback callback) {
            initialize();

            JSONObject body = new JSONObject();
            try {
                body.put("id", randomBytesHex(8));
                body.put("device", device());
                body.put("name", name);
                body.put("args", args);
                body.put("staging", API.useStaging);
            } catch (final JSONException e) {
                e.printStackTrace();
                callback.onResult(new Error() {{type = ErrorType.Fatal; message = e.getMessage();}}, null);
            }

            final Request request = new Request.Builder()
                    .url("https://" + baseUrl + (API.useStaging ? "-staging" : "") + "/" + name)
                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body.toString()))
                    .build();

            final boolean shouldReceiveResponse[] = new boolean[] {true};
            final int sentCount[] = new int[] {0};
            final Timer timer = new Timer();
            final TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    sentCount[0] += 1;
                    if (sentCount[0] >= 22) {
                        return;
                    }
                    if (sentCount[0] >= 25) {
                        if (!shouldReceiveResponse[0]) return;
                        shouldReceiveResponse[0] = false;
                        timer.cancel();
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResult(new Error() {{type = ErrorType.Connection; message = "Erro de conexão, tente novamente mais tarde.";}}, null);
                            }
                        });
                        return;
                    }
                    if (sentCount[0] % 4 == 0) {
                        createHttpClient();
                    }
                    http.newCall(request).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, final IOException e) {
                            if (!shouldReceiveResponse[0]) return;
                            shouldReceiveResponse[0] = false;
                            timer.cancel();
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    e.printStackTrace();
                                    callback.onResult(new Error() {{type = ErrorType.Fatal; message = e.getMessage();}}, null);
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            if (!shouldReceiveResponse[0]) return;
                            if (response.code() != 200) {
                                Log.e("API", "HTTP " + response.code());
                                return;
                            }
                            shouldReceiveResponse[0] = false;
                            timer.cancel();
                            final String stringBody = response.body().string();
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject body = new JSONObject(stringBody);

                                        SharedPreferences pref = context().getSharedPreferences("api", Context.MODE_PRIVATE);
                                        pref.edit().putString("deviceId", body.getString("deviceId")).apply();

                                        if (!body.getBoolean("ok")) {
                                            JSONObject jsonError = body.getJSONObject("error");
                                            Error error = new Error();
                                            error.type = #{type_from_json(@ast.enum_types.find { |e| e.name == "ErrorType" }.not_nil!, "jsonError", "type".inspect)};
                                            error.message = jsonError.getString("message");
                                            Log.e("API Error", jsonError.getString("type") + " - " + error.message);
                                            callback.onResult(error, null);
                                        } else {
                                            callback.onResult(null, body);
                                        }
                                    } catch (final JSONException e) {
                                        e.printStackTrace();
                                        callback.onResult(new Error() {{type = ErrorType.Fatal; message = e.getMessage();}}, null);
                                    }
                                }
                            });
                        }
                    });
                }
            };

            timer.schedule(task, 0, 2000);
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

        static private class TLSSocketFactory extends SSLSocketFactory {
            private SSLSocketFactory internalSSLSocketFactory;

            TLSSocketFactory() throws KeyManagementException, NoSuchAlgorithmException {
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, null, null);
                internalSSLSocketFactory = context.getSocketFactory();
            }

            @Override
            public String[] getDefaultCipherSuites() {
                return internalSSLSocketFactory.getDefaultCipherSuites();
            }

            @Override
            public String[] getSupportedCipherSuites() {
                return internalSSLSocketFactory.getSupportedCipherSuites();
            }

            @Override
            public Socket createSocket() throws IOException {
                return enableTLSOnSocket(internalSSLSocketFactory.createSocket());
            }

            @Override
            public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
                return enableTLSOnSocket(internalSSLSocketFactory.createSocket(s, host, port, autoClose));
            }

            @Override
            public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
                return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port));
            }

            @Override
            public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
                return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port, localHost, localPort));
            }

            @Override
            public Socket createSocket(InetAddress host, int port) throws IOException {
                return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port));
            }

            @Override
            public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
                return enableTLSOnSocket(internalSSLSocketFactory.createSocket(address, port, localAddress, localPort));
            }

            private Socket enableTLSOnSocket(Socket socket) {
                if (socket != null && (socket instanceof SSLSocket)) {
                    ((SSLSocket)socket).setEnabledProtocols(new String[] {"TLSv1.2"});
                }
                return socket;
            }
        }
    }
}
END
  end

  def callback_type(t : AST::Type)
    t.is_a?(AST::VoidPrimitiveType) ? "VoidCallback" : "Callback<#{native_type_not_primitive(t)}>"
  end
end

Target.register(JavaAndroidTarget, target_name: "java_android")
