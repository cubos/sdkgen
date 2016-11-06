require "./lexer"
require "./parser"
require "./ast_to_s"

lexer = Lexer.new("experiment.api")
parser = Parser.new(lexer)

puts parser.parse.to_s

# while token = lexer.next_token
#   p token
# end

