require "./ast"
require "./lexer"
require "./parser"

abstract class Target
  @@targets = {} of {String, Bool} => Target.class

  def initialize(@output : String, @ast : AST::ApiDescription)
    @io = IO::Memory.new
  end

  def write
    @io.rewind
    File.write(@output, @io)
  end

  abstract def gen

  def self.register(target, language, is_server = false)
    @@targets[{language, is_server}] = target
  end

  def self.process(ast, output, is_server = false)
    match = output.match(/\.(\w+)$/)
    unless match
      raise "Unrecognized extension for '#{output}'"
    end
    language = match[1]
    target = @@targets[{language, is_server}]?
    unless target
      raise "Language extension '.#{language}' is not supported"
    end
    t = target.new(output, ast)
    t.gen
    t.write
  end

  def ident(code)
    code.split("\n").map {|line| "  " + line}.join("\n").gsub(/\n\s+$/m, "\n")
  end
end