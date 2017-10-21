require "spec"
require "../lexer"
require "../parser"
require "../ast_to_s"

def clear(code)
  code = code.strip
  code = code.gsub /\/\/.*/, ""
  code = code.gsub /\n\s+/, "\n"
  code = code.gsub /\n+/, "\n"
  code
end

def check_parses(code)
  parser = Parser.new(IO::Memory.new(code))
  ast = parser.parse
  clear(ast.to_s).should eq clear(code)
end

describe Parser do
  {% for p in %w[string int uint date datetime bytes float bool] %}
    it "handles primitive type '#{{{p}}}'" do
      check_parses <<-END
      type Foo {
        foo: #{{{p}}}
      }
      END
    end
  {% end %}

  {% for kw in %w[string int uint date datetime bytes float bool type get function enum import error void] %}
    it "handles '#{{{kw}}}' on the name of a field" do
      check_parses <<-END
      type Foo {
        #{{{kw}}}: int
      }
      END
    end
  {% end %}

  it "handles arrays and optionals" do
    check_parses <<-END
    type Foo {
      aa: string[]
      bbb: int?[]??
      cccc: int[][][]
      ddddd: uint[][][]??[]???[][]
    }
    END
  end
end
