require "./target_swift"

class SwiftIosTarget < SwiftTarget
  def gen
    @io << <<-END
import Alamofire

class API {

END

    @ast.custom_types.each do |custom_type|
      @io << ident generate_custom_type_interface(custom_type)
      @io << "\n\n"
    end

    # static func logIn(email: String, callback: @escaping (_ result: String, _ error: APIInternal.Error?) -> Void) {

    # }
    @ast.operations.each do |op|
      args = op.args.map {|arg| "#{arg.name}: #{native_type arg.type}" }
      if op.return_type.is_a? AST::VoidPrimitiveType
        args << "callback: @escaping (_ error: APIInternal.Error?) -> Void"
      else
        args << "callback: @escaping (_ result: #{native_type op.return_type}, _ error: APIInternal.Error?) -> Void"
      end
      @io << ident(String.build do |io|
        io << "static public func #{op.fnName}(#{args.join(", ")}) {\n"
        io << ident(String.build do |io|
          io << <<-END
let args: NSMutableDictionary = [:]

END
      op.args.each do |arg|
        io << "args[\"#{arg.name}\"] = #{type_to_json arg.type, arg.name}\n"
      end
          io << <<-END

APIInternal.makeRequest(#{op.fnName.inspect}, args) {(result: AnyObject?, error: APIInternal.Error?) -> Void in
    if error {
        callback(nil, error);
    } else {
        callback.onResult(#{"#{type_from_json op.return_type, "result"}, " unless op.return_type.is_a? AST::VoidPrimitiveType}nil);
    }
}

END
        end)
        io << "}"
      end)
      @io << "\n\n"
    end

    @io << <<-END
}

class APIInternal {
    static var baseUrl = "api.nutriserie.com.br/user"

    class Error {
        var type: String
        var message: String

        init(type: String, message: String) {
            self.type = type
            self.message = message
        }
    }

    static func device() {
        let device = NSMutableDictionary()
        device["platform"] = "ios"
        device["fingerprint"] = "."
        device["platformVersion"] = "iOS " + UIDevice.current.systemVersion + " on " + UIDevice.current.model
        if let version = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String {
            device["version"] = version
        } else {
            device["version"] = "unknown"
        }
        device["language"] = NSLocale.preferredLanguages[0]
        if let deviceId = UserDefaults.standard.value(forKey: "device-id") as? String {
            device["id"] = deviceId
        }
        return device
    }

    static func randomBytesHex(len: Int32) {
        var randomBytes = [UInt8](count: len, repeatedValue: 0)
        SecRandomCopyBytes(kSecRandomDefault, len, &randomBytes)
        return randomBytes.map({String(format: "%02hhx", $0)}).joinWithSeparator("")
    }

    static func decodeDate(str: String) -> NSDate {
        let formatter = NSDateFormatter()
        formatter.calendar = NSCalendar(calendarIdentifier: NSCalendarIdentifierGregorian)
        formatter.timeZone = NSTimeZone(name: "UTC")
        formatter.locale = NSLocale(localeIdentifier: "en_US_POSIX")
        formatter.dateFormat = "yyyy-MM-dd"
        return formatter.dateFromString(str)!
    }

    static func encodeDate(date: NSDate) -> String {
        let formatter = NSDateFormatter()
        formatter.calendar = NSCalendar(calendarIdentifier: NSCalendarIdentifierGregorian)
        formatter.timeZone = NSTimeZone(name: "UTC")
        formatter.locale = NSLocale(localeIdentifier: "en_US_POSIX")
        formatter.dateFormat = "yyyy-MM-dd"
        return formatter.stringFromDate(date)
    }

    static func decodeDateTime(str: String) -> NSDate {
        let formatter = NSDateFormatter()
        formatter.calendar = NSCalendar(calendarIdentifier: NSCalendarIdentifierGregorian)
        formatter.timeZone = NSTimeZone(name: "UTC")
        formatter.locale = NSLocale(localeIdentifier: "en_US_POSIX")
        formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS"
        return formatter.dateFromString(str)!
    }

    static func encodeDateTime(date: NSDate) -> String {
        let formatter = NSDateFormatter()
        formatter.calendar = NSCalendar(calendarIdentifier: NSCalendarIdentifierGregorian)
        formatter.timeZone = NSTimeZone(name: "UTC")
        formatter.locale = NSLocale(localeIdentifier: "en_US_POSIX")
        formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS"
        return formatter.stringFromDate(date)
    }

    static func makeRequest(name: String, args: NSDictionary, callback: (result: AnyObject?, error: Error?) -> Void) {
        let body = [
            "id": randomBytesHex(16),
            "device": device(),
            "name": name,
            "args": args
        ]

        Alamofire.request(.POST, "https://\(baseUrl)/\(name)", parameters: body, encoding: ParameterEncoding.JSON, headers: header).validate().responseJSON { (response) in
            switch response.result {
            case .Failure(let err):
                callback(nil, Error("Connection", err))
                break
            case .Success(let body):
                if body["ok"] as Bool {
                    callback(result: body["result"], error: nil)
                } else {
                    let error = body["error"] as! NSDictionary
                    callback(result: nil, error: Error(error["type"], error["message"]))
                }
                break
            }
        }
    }
}
END
  end
end

Target.register(SwiftIosTarget, language: "swift", is_server: false)
