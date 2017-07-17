require "./target_swift"

class SwiftIosTarget < SwiftTarget
  def gen
    @io << <<-END
import Alamofire

class API {
    static var useStaging = false
    static var globalCallback: (_ method: String, _ result: APIInternal.Result<Any?>, _ callback: @escaping ((APIInternal.Result<Any?>) -> Void)) -> Void = { _, result, callback in
        callback(result)
    } 

END

    @ast.struct_types.each do |t|
      @io << ident generate_struct_type(t)
      @io << "\n\n"
    end

    @ast.enum_types.each do |t|
      @io << ident generate_enum_type(t)
      @io << "\n\n"
    end

    @ast.operations.each do |op|
      args = op.args.map {|arg| "#{arg.name}: #{native_type arg.type}" }

      if op.return_type.is_a? AST::VoidPrimitiveType
         args << "callback: ((_ result: APIInternal.Result<Void>) -> Void)?"
      else
        ret = op.return_type
        args << "callback: ((_ result: APIInternal.Result<#{native_type ret}>) -> Void)?"
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
        io << "args[\"#{arg.name}\"] = #{type_to_json arg.type, arg.name}\n\n"
      end

        io << "APIInternal.makeRequest(#{op.pretty_name.inspect}, args) {  response in \n"

        io << ident(String.build do |io|
            io << "switch response {\n"
            io << <<-END
            case .failure(let error):
                callback?(APIInternal.Result.failure(error))\n
            END

            if op.return_type.is_a? AST::VoidPrimitiveType
                io << <<-END
                case .success:
                    callback?(APIInternal.Result.success())\n
                END
            else
                io << <<-END
                case .success(let value):
                    let returnObject = #{type_from_json(op.return_type, "value")}
                    callback?(APIInternal.Result.success(returnObject))\n
                END
            end
            io << "}\n"
        end) # end of make request body indentation.



        io << "}\n\n"
       end)
        io << "}"
      end)
      @io << "\n\n"
    end


    @io << <<-END
}

class APIInternal {
    static var baseUrl = #{@ast.options.url.inspect}

    enum Result<T> {
        case success(T)
        case failure(Error)
    }
    class Error {
        var type: API.ErrorType
        var message: String

        init(_ type: API.ErrorType, _ message: String) {
            self.type = type
            self.message = message
        }

        init(json: [String: Any]) {
            self.type = API.ErrorType(rawValue: json["type"] as! String) ?? API.ErrorType.Fatal
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

    static func makeRequest(_ name: String, _ args: [String: Any], callback: @escaping (Result<Any?>) -> Void) {
        let api = SessionManager.default

        let body = [
            "id": randomBytesHex(len: 8),
            "device": device(),
            "name": name,
            "args": args,
            "staging": API.useStaging
        ] as [String : Any]

        api.request("https://\\(baseUrl)\\(API.useStaging ? "-staging" : "")/\\(name)", method: .post, parameters: body, encoding: JSONEncoding.default).responseJSON { response in
            guard let responseValue = response.result.value else {
                let error = Error(API.ErrorType.Connection, "Erro de Conexão, tente novamente mais tarde")
                API.globalCallback(name, Result.failure(error), callback)
                return
            }

            let response = HTTPResponse(json: responseValue as! [String: Any])
            saveDeviceID(response.deviceId)

            guard response.error == nil && response.ok else {
                API.globalCallback(name, Result.failure(response.error!), callback)
                return
            }
            API.globalCallback(name, Result.success(response.result), callback: callback)
        }
    }
}
END
  end
end

Target.register(SwiftIosTarget, target_name: "swift_ios")
