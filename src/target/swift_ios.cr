require "./swift"

class SwiftIosTarget < SwiftTarget
  def gen
    @io << <<-END
import Alamofire
import KeychainSwift

protocol ApiCallsLogic: class {\n
END

    @ast.operations.each do |op|
        args = op.args.map { |arg| "#{arg.name}: #{native_type arg.type}" }
        if op.return_type.is_a? AST::VoidPrimitiveType
            args << "callback: ((_ result: API.ApiInternal.Result<API.NoReply>) -> Void)?"
        else
            ret = op.return_type
            args << "callback: ((_ result: API.ApiInternal.Result<#{native_type ret}>) -> Void)?"
        end

        @io << ident "@discardableResult func #{op.pretty_name}(#{args.join(", ")}) -> DataRequest\n"
    end

    @io << "}\n\n" #CloseAPICallsProtocol
    @io << <<-END

class API {
    static var customUrl: String?
    static var useStaging = false
    static var globalCallback: (_ method: String, _ result: ApiInternal.Result<Any?>, _ callback: ((ApiInternal.Result<Any?>) -> Void)?) -> Void = { _, result, callback in
        callback?(result)
    }

    static var isEnabledAssertion = true
    static var calls: ApiCallsLogic = Calls()
    static var apiInternal = ApiInternal()

    static var decoder: JSONDecoder = {
        let currentDecoder = JSONDecoder()
        currentDecoder.dateDecodingStrategy = .custom({ (decoder) -> Date in
            let container = try decoder.singleValueContainer()
            let dateStr = try container.decode(String.self)

            var date: Date? = apiInternal.decodeDate(str: dateStr) ?? apiInternal.decodeDateTime(str: dateStr)

            guard let unwrapDate = date else {
                throw DecodingError.dataCorruptedError(in: container, debugDescription: "Cannot decode date string \\(dateStr)")
            }

            return unwrapDate
        })

        return currentDecoder
    }()

     // MARK: Struct and Enums
     struct NoReply: Codable {}\n\n
END
    #ApiCalls
    @io << ident(String.build do |io|
        io << "public class Calls: ApiCallsLogic {\n"
        @ast.operations.each do |op|
            args = op.args.map { |arg| "#{arg.name}: #{native_type arg.type}" }

            if op.return_type.is_a? AST::VoidPrimitiveType
              args << "callback: ((_ result: ApiInternal.Result<NoReply>) -> Void)?"
            else
              ret = op.return_type
              args << "callback: ((_ result: ApiInternal.Result<#{native_type ret}>) -> Void)?"
            end
            io << ident(String.build do |io|
                io << "@discardableResult public func #{op.pretty_name}(#{args.join(", ")}) -> DataRequest {\n"
                io << ident(String.build do |io|
                    if op.args.size != 0
                      io << "var args = [String: Any]()\n"
                    else
                      io << "let args = [String: Any]()\n"
                    end
                    op.args.each do |arg|
                      io << "args[\"#{arg.name}\"] = #{type_to_json arg.type, arg.name}\n"
                    end
                    io << "\n"
                    io << "return API.apiInternal.makeRequest(#{op.pretty_name.inspect}, args, callback: callback)\n"
                end) # end of function body indentation.
                  io << "}"
            end) # end of function indentation.
            io << "\n\n"
        end
        io << "}\n"
    end) # end of Calls indentation.
    @io << "\n"

    @ast.struct_types.each do |t|
      @io << ident generate_struct_type(t)
      @io << "\n\n"
    end

    @ast.enum_types.each do |t|
      @io << ident generate_enum_type(t)
      @io << "\n\n"
    end

    @io << <<-END

    class ApiInternal {
        var baseUrl = #{@ast.options.url.inspect}

        // MARK: ApiInternal Inner classes
        enum Result<T> {
            case success(T)
            case failure(Error)
        }

        class Error: Codable {
            var type: API.ErrorType
            var message: String

            init(type: API.ErrorType, message: String) {
                self.type = type
                self.message = message
            }
        }

        class HttpResponse<T: Codable>: Codable {
            var ok: Bool
            var deviceId: String?
            var result: T?
            var error: Error?

            required init(from decoder: Decoder) throws {
                let values = try decoder.container(keyedBy: CodingKeys.self)
                ok = try values.decode(Bool.self, forKey: .ok)
                deviceId = try? values.decode(String.self, forKey: .deviceId)
                result = (try? values.decode(T.self, forKey: .result)) ?? (API.NoReply() as? T)
                error = try? values.decode(Error.self, forKey: .error)
            }
        }

        // MARK: ApiInternal Date Handler
        let dateAndTimeFormatter: DateFormatter = {
            let formatter = DateFormatter()
            formatter.calendar = Calendar(identifier: .gregorian)
            formatter.timeZone = TimeZone(abbreviation: "UTC")
            formatter.locale = Locale(identifier: "en_US_POSIX")
            formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS"
            return formatter
        }()

        let dateFormatter: DateFormatter = {
            let formatter = DateFormatter()
            formatter.calendar = Calendar(identifier: .gregorian)
            formatter.locale = Locale(identifier: "en_US_POSIX")
            formatter.dateFormat = "yyyy-MM-dd"
            return formatter
        }()

        func decodeDate(str: String) -> Date? {
            return dateFormatter.date(from: str)
        }

        func encodeDate(date: Date) -> String {
            return dateFormatter.string(from: date)
        }

        func decodeDateTime(str: String) -> Date? {
            return dateAndTimeFormatter.date(from: str)
        }

        func encodeDateTime(date: Date) -> String {
            return dateAndTimeFormatter.string(from: date)
        }

        // MARK: ApiInternal Device Handler
        func device() -> [String: Any] {
            var device = [String: Any]()
            device["platform"] = "ios"
            device["fingerprint"] = phoneFingerprint()
            device["platformVersion"] = "iOS " + UIDevice.current.systemVersion + " on " + UIDevice.current.model
            if let version = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String {
                device["version"] = version
            } else {
                device["version"] = "unknown"
            }

            device["language"] = Locale.preferredLanguages[0]
            device["timezone"] = TimeZone.current.identifier
            if let currentId = deviceId {
                device["id"] = currentId
            }

            return device
        }

        func randomBytesHex(len: Int) -> String {
            var randomBytes = [UInt8](repeating: 0, count: len)
            let _ = SecRandomCopyBytes(kSecRandomDefault, len, &randomBytes)
            return randomBytes.map({String(format: "%02hhx", $0)}).joined(separator: "")
        }

        var deviceId: String? {
            return UserDefaults.standard.value(forKey: "device-id") as? String
        }

        func saveDeviceId(_ id: String) {
            UserDefaults.standard.setValue(id, forKey: "device-id")
            UserDefaults.standard.synchronize()
        }

        func phoneFingerprint() -> String {
            let keychain = KeychainSwift()
            guard let phoneFingerprint = keychain.get("phoneFingerprint") else {
                let newPhoneFingerprint = randomBytesHex(len: 32)
                keychain.set(newPhoneFingerprint, forKey: "phoneFingerprint", withAccess: .accessibleAlwaysThisDeviceOnly)
                return newPhoneFingerprint
            }

            return phoneFingerprint
        }

        // MARK: ApiInternal request functions
        func globalCallback<T: Codable>(result: Result<T>, method: String, callback: ((Result<T>) -> Void)?) {

            //Convert result to result of type Any
            let anyResult: Result<Any?>
            switch result {
            case .success(let value):
                anyResult = Result.success(value as Any?)
            case .failure(let error):
                anyResult = Result.failure(error)
            }

            API.globalCallback(method, anyResult) { result in
                //Convert result to result of type T.
                let tResult: Result<T>
                switch result {
                case .success(let value):

                    if let tValue = value as? T {
                        tResult = Result.success(tValue)
                    } else {
                        tResult = Result.failure(Error(type: API.ErrorType.Serialization,message: "Erro ao mapear os dados, tente novamente mais tarde"))
                    }

                case .failure(let error):
                    tResult = Result.failure(error)
                }

                callback?(tResult)
            }
        }

        @discardableResult
        func makeRequest<T: Codable>(_ name: String, _ args: [String: Any], callback: ((Result<T>) -> Void)?) -> DataRequest {
            let api = SessionManager.default

            let body = [
                "id": randomBytesHex(len: 8),
                "device": device(),
                "name": name,
                "args": args,
                "staging": API.useStaging
                ] as [String : Any]

            let url = API.customUrl ?? "https://\\(baseUrl)\\(API.useStaging ? "-staging" : "")"
            return api.request(
                "\\(url)/\\(name)",
                method: .post,
                parameters: body,
                encoding: JSONEncoding.default
                ).responseData { [weak self] response in
                    guard let this = self else { return }

                    let responseResult: Result<T>
                    switch response.result {
                    case .success(let data):

                        do {
                            let response = try API.decoder.decode(HttpResponse<T>.self, from: data)
                            if let deviceId = response.deviceId { this.saveDeviceId(deviceId) }
                            if let result = response.result, response.ok {
                                responseResult = Result.success(result)
                            } else if let error = response.error {
                                responseResult = Result.failure(error)
                            } else {
                                responseResult = Result.failure(Error(type: API.ErrorType.Serialization, message: "Algo de errado está acontecendo em nosso servidor, tente novamente mais tarde"))
                            }

                        } catch let error {
                            if API.isEnabledAssertion { assert(false, "Erro ao serializar dados, error: \\(error)") }
                            responseResult = Result.failure(Error(type: API.ErrorType.Serialization, message: "Erro ao carregar seus dados, tente novamente mais tarde"))
                        }

                    case .failure:
                        responseResult = Result.failure(Error(type: API.ErrorType.Connection, message: "Erro de Conexão, tente novamente mais tarde"))
                    }

                    this.globalCallback(result: responseResult, method: name, callback: callback)
            }
        }
    }
}
protocol DisplayableValue: RawRepresentable {
    var displayableValue: String { get }
}

extension DisplayableValue where RawValue == String {
    var displayableValue: String {
        return self.rawValue
    }
}

END
  end
end

Target.register(SwiftIosTarget, target_name: "swift_ios")
