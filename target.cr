require "./ast"
require "./lexer"
require "./parser"

module Target
  @@targets = {} of {String, Bool} => (ApiDescription, String)->

  def self.add_target(language, is_server = false, &block : (ApiDescription, String)->)
    @@targets[{language, is_server}] = block
  end

  def self.process(input, output, is_server = false)
    match = output.match(/\.(\w+)$/)
    unless match
      raise "Unrecognized extension for '#{output}'"
    end
    language = match[1]
    target = @@targets[{language, is_server}]?
    unless target
      raise "Language extension '.#{language}' is not supported"
    end
    lexer = Lexer.new(input)
    parser = Parser.new(lexer)
    target.call(parser.parse, output)
  end
end