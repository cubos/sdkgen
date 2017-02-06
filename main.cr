require "./lexer"
require "./parser"
require "./ast_to_s"
require "./target_typescript_server"

Target.process("test-server/api.sdkgen", "test-server/api.ts", is_server: true)
