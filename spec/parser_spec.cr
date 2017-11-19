require "spec"
require "../src/syntax/parser"
require "../src/syntax/ast_to_s"

describe Parser do
  Lexer::PRIMITIVES.each do |p|
    it "handles primitive type '#{p}'" do
      check_parses <<-END
      type Foo {
        foo: #{p}
      }
      END
    end
  end

  (Lexer::PRIMITIVES + %w[type get function enum import error void]).each do |kw|
    it "handles '#{kw}' on the name of a field" do
      check_parses <<-END
      type Foo {
        #{kw}: int
      }
      END
    end
  end

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
    Lexer::PRIMITIVES.each do |primitive|
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

  it "fails when field happens twice" do
    check_doesnt_parse(<<-END
    type Baz {
      a: int
      b: bool
      a: int
    }
    END
    , "redeclare")

    check_doesnt_parse(<<-END
    type Baz {
      b: int
      xx: bool
      xx: int
    }
    END
    , "redeclare")

    check_doesnt_parse(<<-END
    function foo(a: string, a: int)
    END
    , "redeclare")
  end

  it "handles spreads in structs" do
    check_parses <<-END
    type Foo {
      ...Bar
      ...Baz
      aa: string
    }
    END
  end
end

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

def check_doesnt_parse(code, message)
  parser = Parser.new(IO::Memory.new(code))
  expect_raises(Parser::ParserException, message) { parser.parse }
end
