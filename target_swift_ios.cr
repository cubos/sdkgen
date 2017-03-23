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
        ret = op.return_type
        unless ret.is_a? AST::OptionalType
          ret = AST::OptionalType.new ret
        end
        args << "callback: @escaping (_ result: #{native_type ret}, _ error: APIInternal.Error?) -> Void"
      end
      @io << ident(String.build do |io|
        io << "static public func #{op.pretty_name}(#{args.join(", ")}) {\n"
        io << ident(String.build do |io|
#           io << <<-END
# var args = [String:Any]()

# END
      if op.args.size != 0
        io << "var args = [String: Any]()\n"
      else
        io << "let args = [String: Any]()\n"
      end

      op.args.each do |arg|
        io << "args[\"#{arg.name}\"] = #{type_to_json arg.type, arg.name}\n"
      end
          io << <<-END

APIInternal.makeRequest(#{op.pretty_name.inspect}, args) { result, error in
    if error != nil {
        callback(#{"nil, " unless op.return_type.is_a? AST::VoidPrimitiveType}error);
    } else {
        callback(#{"(#{type_from_json op.return_type, "result"}), " unless op.return_type.is_a? AST::VoidPrimitiveType}nil);
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
        
        init(_ type: String, _ message: String) {
            self.type = type
            self.message = message
        }
    }
    
    static func device() -> [String: Any] {
        var device = [String: Any]()
        device["platform"] = "ios"
        device["fingerprint"] = "."
        device["platformVersion"] = "iOS " + UIDevice.current.systemVersion + " on " + UIDevice.current.model
        if let version = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String {
            device["version"] = version
        } else {
            device["version"] = "unknown"
        }
        device["language"] = Locale.preferredLanguages[0]
        if let deviceId = UserDefaults.standard.value(forKey: "device-id") as? String {
            device["id"] = deviceId
        }
        return device
    }
    
    static func randomBytesHex(len: Int) -> String {
        var randomBytes = [UInt8](repeating: 0, count: len)
        let _ = SecRandomCopyBytes(kSecRandomDefault, len, &randomBytes)
        return randomBytes.map({String(format: "%02hhx", $0)}).joined(separator: "")
    }
    
    static func decodeDate(str: String) -> Date {
        let formatter = DateFormatter()
        formatter.calendar = Calendar(identifier: .gregorian)
        formatter.locale = Locale(identifier: "en_US_POSIX")
        formatter.dateFormat = "yyyy-MM-dd"
        return formatter.date(from: str)!
    }
    
    static func encodeDate(date: Date) -> String {
        let formatter = DateFormatter()
        formatter.calendar = Calendar(identifier: .gregorian)
        formatter.locale = Locale(identifier: "en_US_POSIX")
        formatter.dateFormat = "yyyy-MM-dd"
        return formatter.string(from: date)
    }
    
    static func decodeDateTime(str: String) -> Date {
        let formatter = DateFormatter()
        formatter.calendar = Calendar(identifier: .gregorian)
        formatter.timeZone = TimeZone(abbreviation: "UTC")
        formatter.locale = Locale(identifier: "en_US_POSIX")
        formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS"
        return formatter.date(from: str)!
    }
    
    static func encodeDateTime(date: Date) -> String {
        let formatter = DateFormatter()
        formatter.calendar = Calendar(identifier: .gregorian)
        formatter.timeZone = TimeZone(abbreviation: "UTC")
        formatter.locale = Locale(identifier: "en_US_POSIX")
        formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS"
        return formatter.string(from: date)
    }
    
    static func makeRequest(_ name: String, _ args: [String: Any], callback: @escaping (_ result: Any?, _ error: Error?) -> Void) {
        
        let body = [
            "id": randomBytesHex(len: 16),
            "device": device(),
            "name": name,
            "args": args
            ] as [String : Any]
        
         Alamofire.request("https://\\(baseUrl)/\\(name)", method: .post, parameters: body, encoding: JSONEncoding.default).validate().responseJSON { response in
            switch response.result {
            case .failure(let err):
                callback(nil, Error("Connection", err.localizedDescription))
                break
            case .success(let value):
                let body = value as! [String: Any]
                if body["ok"] as! Bool {
                    callback(body["result"]!, nil)
                } else {
                    let error = body["error"] as! [String: String]
                    callback(nil, Error(error["type"]!, error["message"]!))
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
