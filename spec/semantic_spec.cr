require "spec"
require "../src/syntax/parser"
require "../src/semantic/ast_semantic"

describe Semantic do
  it "fails when type definition appears twice" do
    expect_raises(Exception, "already defined") do
      parse <<-END
        type X {

        }

        type X {

        }
      END
    end
  end
end

def parse(code)
  parser = Parser.new(IO::Memory.new(code))
  ast = parser.parse
  ast.semantic
  ast
end
