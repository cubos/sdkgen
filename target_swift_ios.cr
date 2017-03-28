require "./target_swift"

class SwiftIosTarget < SwiftTarget
  def gen
    @io << <<-END
import Alamofire

class API {

END

    #generate error index
    @io << "\n"
    @io << ident "enum ErrorType: String {\n"
    @ast.errors.each do |error|
        @io << ident ident "case #{error} = #{error.inspect}\n" 
    end
    @io << ident ident "case ConnectionError = \"ConnectionError\" \n"
    @io << ident ident "case UnknownError = \"UnknownError\" \n" 
    @io << ident "}"
    @io << "\n\n"

    @ast.type_definitions.each do |type_definition|
      @io << ident generate_type_definition_type(type_definition)
      @io << "\n\n"
    end

    @ast.operations.each do |op|
      args = op.args.map {|arg| "#{arg.name}: #{native_type arg.type}" }

      if op.return_type.is_a? AST::VoidPrimitiveType
        args << "callback:  ((_ error: APIInternal.Error?) -> Void)?"
      else
        ret = op.return_type
        unless ret.is_a? AST::OptionalType
          ret = AST::OptionalType.new ret
        end
        args << "callback: ((_ result: #{native_type ret}, _ error: APIInternal.Error?) -> Void)?"
      end
      @io << ident(String.build do |io|
        io << "static public func #{op.pretty_name}(#{args.join(", ")}) {\n"
        io << ident(String.build do |io|

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
        callback?(#{"nil, " unless op.return_type.is_a? AST::VoidPrimitiveType}error);
    } else {
        callback?(#{"(#{type_from_json op.return_type, "result"}), " unless op.return_type.is_a? AST::VoidPrimitiveType}nil);
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
        var type: API.ErrorType
        var message: String
        
        init(_ type: API.ErrorType, _ message: String) {
            self.type = type
            self.message = message
        }
        
        init(json: [String: Any]) {
            self.type = API.ErrorType(rawValue: json["type"] as! String) ?? API.ErrorType.UnknownError
            self.message = json["message"] as! String
        }
    }
    
    class HTTPResponse {
        var ok: Bool!
        var deviceId: String!
        var result: Any?
        var error: Error?
        
        init(json: [String: Any]) {
            self.ok = json["ok"] as! Bool
            self.deviceId = json["deviceId"] as! String
            self.result = json["result"] is NSNull ? nil : json["result"]!
            self.error = json["error"] is NSNull ? nil: (Error(json: json["error"] as! [String: Any]))
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
        if let deviceId = deviceID  {
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
    
    static var deviceID: String? {
        return UserDefaults.standard.value(forKey: "device-id") as? String
    }
    
    static func saveDeviceID(_ id: String) {
        UserDefaults.standard.setValue(id, forKey: "device-id")
        UserDefaults.standard.synchronize()
    }

    static func isNull(value: Any?) -> Bool {
        return value == nil || value is NSNull
    }

    static func makeRequest(_ name: String, _ args: [String: Any], callback: @escaping (_ result: Any?, _ error: Error?) -> Void) {
        let api = SessionManager.default
        
        let body = [
            "id": randomBytesHex(len: 16),
            "device": device(),
            "name": name,
            "args": args
            ] as [String : Any]
        
         api.request("https://\\(baseUrl)/\\(name)", method: .post, parameters: body, encoding: JSONEncoding.default).responseJSON { response in
            
            guard let responseValue = response.result.value else {
                callback(nil, Error(API.ErrorType.ConnectionError, "no result value"))
                return
            }
            
            let response = HTTPResponse(json: responseValue as! [String: Any])
            saveDeviceID(response.deviceId)
            
            guard response.error == nil && response.ok else {
                callback(nil, response.error)
                return
            }
            
            callback(response.result, nil)
        }
    }
}
END
  end
end

Target.register(SwiftIosTarget, language: "swift", is_server: false)
