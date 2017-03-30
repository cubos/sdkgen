require "./lexer"
require "./parser"
require "./ast_to_s"
require "./target_java_android"
require "./target_swift_ios"
require "./target_typescript_server"
require "./target_typescript_web"
require "option_parser"
require "file_utils"

is_server = false
destination = ""
sources = [] of String

OptionParser.parse! do |parser|
  parser.banner = "Usage: salute [arguments]"
  parser.on("-s", "--server", "Generates server-side code") { is_server = true }
  parser.on("-o NAME", "--output=NAME", "Specifies the output file") { |name| destination = name }
  parser.on("-h", "--help", "Show this help") { puts parser }
  parser.unknown_args {|args| sources = args }
end

if sources.size == 0
  STDERR.puts "You must specify one source file"
  exit
elsif sources.size > 1
  STDERR.puts "You must specify only one source file"
  exit
end

source = sources[0]

parser = Parser.new(source)
ast = parser.parse
ast.semantic

if destination == ""
  STDERR.puts "You must specify an output file"
  exit
end

FileUtils.mkdir_p(File.dirname(destination))
Target.process(ast, destination, is_server: is_server)
