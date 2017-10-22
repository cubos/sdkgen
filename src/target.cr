require "./ast"
require "./lexer"
require "./parser"
require "./codegen_types/**"

abstract class Target
  @@targets = {} of String => Target.class

  def initialize(@output : String, @ast : AST::ApiDescription)
    @io = IO::Memory.new
  end

  def write
    @io.rewind
    File.write(@output, @io)
  end

  abstract def gen

  def self.register(target, target_name)
    @@targets[target_name] = target
  end

  def self.process(ast, output, target_name)
    target = @@targets[target_name]?
    unless target
      raise "Target '#{target_name}' is not supported"
    end
    t = target.new(output, ast)
    t.gen
    t.write
  end

  def ident(code)
    code.split("\n").map {|line| "  " + line}.join("\n").gsub(/\n\s+$/m, "\n")
  end
end
