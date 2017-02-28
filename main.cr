require "./lexer"
require "./parser"
require "./ast_to_s"
require "./target_typescript_server"
require "./target_typescript_web"

# lexer = Lexer.new("test.api")
# parser = Parser.new(lexer)
# ast = parser.parse

# Target.process(ast, "test-server/api-user/api.ts", is_server: true)





lexer = Lexer.new("../anaiti-api/src/user/api.sdkgen")
parser = Parser.new(lexer)
ast = parser.parse

Target.process(ast, "../anaiti-api/src/user/api.ts", is_server: true)
Target.process(ast, "../anaiti-web/src/api.ts", is_server: false)
