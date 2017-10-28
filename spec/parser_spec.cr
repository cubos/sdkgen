require "spec"
require "../src/syntax/lexer"
require "../src/syntax/parser"
require "../src/syntax/ast_to_s"

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

PRIMITIVES = %w[string int uint date datetime bytes float bool]
describe Parser do
  {% for p in PRIMITIVES %}
    it "handles primitive type '#{{{p}}}'" do
      check_parses <<-END
      type Foo {
        foo: #{{{p}}}
      }
      END
    end
  {% end %}

  {% for kw in PRIMITIVES + %w[type get function enum import error void] %}
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

  it "handles errors" do
    check_parses <<-END
    error Foo
    error Bar
    error FooBar
    END
  end

  it "handles simple get operations" do
    PRIMITIVES.each do |primitive|
      check_parses <<-END
      get foo(): #{primitive}
      get bar(): #{primitive}?
      get baz(): #{primitive}[]
      END
    end
  end

  it "handles options on the top" do
    check_parses <<-END
    $url = "api.cubos.io/sdkgenspec"
    END
  end

  it "handles combinations of all parts" do
    check_parses <<-END
    $url = "api.cubos.io/sdkgenspec"

    error Foo
    error Bar

    type Baz {
      a: string?
      b: int
    }

    get baz(): Baz
    END
  end
end
