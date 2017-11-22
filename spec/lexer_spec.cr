require "spec"
require "../src/syntax/lexer"

describe Lexer do
  it_lexes "", [] of Token

  it_lexes "type", [
    TypeKeywordToken.new,
  ]

  it_doesnt_lex "23", "Unexpected character '2' at -:1:1"

  it_doesnt_lex "2a", "Unexpected character '2' at -:1:1"

  it_lexes "type2", [
    IdentifierToken.new("type2"),
  ]

  it_lexes "aaa", [
    IdentifierToken.new("aaa"),
  ]

  it_lexes "...aaa", [
    SpreadSymbolToken.new,
    IdentifierToken.new("aaa"),
  ]

  it_lexes "a b c", [
    IdentifierToken.new("a"),
    IdentifierToken.new("b"),
    IdentifierToken.new("c"),
  ]

  it_doesnt_lex "aa_bb", "Unexpected character '_' at -:1:3"

  it_lexes "type type", [
    TypeKeywordToken.new,
    TypeKeywordToken.new,
  ]

  it_lexes "enum", [
    EnumKeywordToken.new,
  ]

  it_lexes "error", [
    ErrorKeywordToken.new,
  ]

  it_lexes "import", [
    ImportKeywordToken.new,
  ]

  it_lexes "get", [
    GetKeywordToken.new,
  ]

  it_lexes "Get", [
    IdentifierToken.new("Get"),
  ]

  it_lexes "function", [
    FunctionKeywordToken.new,
  ]

  it_lexes "enuma", [
    IdentifierToken.new("enuma"),
  ]

  it_lexes "errorh", [
    IdentifierToken.new("errorh"),
  ]

  %w[enum type error import get function].each do |kw|
    it_lexes kw, [
      IdentifierToken.new(kw),
    ]
  end

  Lexer::PRIMITIVES.each do |primitive|
    it_lexes primitive, [
      PrimitiveTypeToken.new(primitive),
    ]

    it_lexes primitive, [
      IdentifierToken.new(primitive),
    ]

    it_lexes primitive + "a", [
      IdentifierToken.new(primitive + "a"),
    ]
  end

  it_lexes "err", [
    IdentifierToken.new("err"),
  ]

  it_lexes "{", [
    CurlyOpenSymbolToken.new,
  ]

  it_lexes "{{", [
    CurlyOpenSymbolToken.new,
    CurlyOpenSymbolToken.new,
  ]

  it_lexes "}{", [
    CurlyCloseSymbolToken.new,
    CurlyOpenSymbolToken.new,
  ]

  it_lexes " } { ", [
    CurlyCloseSymbolToken.new,
    CurlyOpenSymbolToken.new,
  ]

  it_lexes "({!:?,=})", [
    ParensOpenSymbolToken.new,
    CurlyOpenSymbolToken.new,
    ExclamationMarkSymbolToken.new,
    ColonSymbolToken.new,
    OptionalSymbolToken.new,
    CommaSymbolToken.new,
    EqualSymbolToken.new,
    CurlyCloseSymbolToken.new,
    ParensCloseSymbolToken.new,
  ]

  it_lexes " [][] ", [
    ArraySymbolToken.new,
    ArraySymbolToken.new,
  ]

  it_lexes "nice[]", [
    IdentifierToken.new("nice"),
    ArraySymbolToken.new,
  ]

  it_lexes "nice\n[]", [
    IdentifierToken.new("nice"),
    ArraySymbolToken.new,
  ]

  it_doesnt_lex "[", "Unexpected end of file"

  it_lexes "type Str string", [
    TypeKeywordToken.new,
    IdentifierToken.new("Str"),
    PrimitiveTypeToken.new("string"),
  ]

  it_lexes "$url", [
    GlobalOptionToken.new("url"),
  ]

  it_lexes "$F", [
    GlobalOptionToken.new("F"),
  ]

  it_lexes "$x123", [
    GlobalOptionToken.new("x123"),
  ]

  it_lexes "$ah[]?", [
    GlobalOptionToken.new("ah"),
    ArraySymbolToken.new,
    OptionalSymbolToken.new,
  ]

  it_doesnt_lex "$", "Unexpected end of file"

  it_doesnt_lex "$_a", "Unexpected character '_'"

  it_doesnt_lex "$ a", "Unexpected character ' '"

  it_lexes "\"ab\"", [
    StringLiteralToken.new("ab"),
  ]

  it_lexes "\"\"", [
    StringLiteralToken.new(""),
  ]

  it_lexes "\"aa\\nbb\"", [
    StringLiteralToken.new("aa\nbb"),
  ]

  it_lexes "\"aa\\bb\"", [
    StringLiteralToken.new("aabb"),
  ]

  it_lexes "\"'\"", [
    StringLiteralToken.new("'"),
  ]

  it_lexes "\"\\n\\t\\\"\"", [
    StringLiteralToken.new("\n\t\""),
  ]

  it_lexes "//hmmm", [] of Token

  it_lexes "x//hmmm", [
    IdentifierToken.new("x"),
  ]

  it_lexes "a//hmmm\nb", [
    IdentifierToken.new("a"),
    IdentifierToken.new("b"),
  ]

  it_lexes "a  //  hmmm  \n  b", [
    IdentifierToken.new("a"),
    IdentifierToken.new("b"),
  ]

  it_lexes "// héýça\n z", [
    IdentifierToken.new("z"),
  ]

  # Add multi-line comments tests

  it_lexes "/**/", [] of Token

  it_lexes "/**/", [] of Token
end

def it_lexes(code, expected_tokens)
  it "lexes '#{code}' as [#{expected_tokens.map(&.to_s).join(" ")}]" do
    lexer = Lexer.new(code)

    tokens = [] of Token
    while token = lexer.next_token
      tokens << token
    end
    tokens.map_with_index! do |token, i|
      expected_tokens[i]?.is_a?(IdentifierToken) ? token.try_ident : token
    end

    tokens.should eq expected_tokens
  end
end

def it_doesnt_lex(code, message)
  it "doesn't lex '#{code}'" do
    lexer = Lexer.new(code)
    expect_raises(Lexer::LexerException, message) do
      while lexer.next_token
      end
    end
  end
end
