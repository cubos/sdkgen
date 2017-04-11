class Lexer
  property filename : String

  class LexerException < Exception
  end

  def initialize(@filename : String)
    @raw = File.read(filename)
    @start = 0
    @pos = 0
    @line = 1
    @col = 0
  end

  private def current_char
    @raw[@pos]?
  end

  private def current_char!
    @raw[@pos]
  end

  private def peek_next_char
    @raw[@pos+1]?
  end

  private def next_char
    @pos += 1
    current_char
  end

  def next_token
    skip
    start_token

    return nil unless current_char

    return make_token(CurlyOpenSymbolToken.new) if literal_match("{")
    return make_token(CurlyCloseSymbolToken.new) if literal_match("}")
    return make_token(ParensOpenSymbolToken.new) if literal_match("(")
    return make_token(ParensCloseSymbolToken.new) if literal_match(")")
    return make_token(OptionalSymbolToken.new) if literal_match("?")
    return make_token(ArraySymbolToken.new) if literal_match("[]")
    return make_token(ColonSymbolToken.new) if literal_match(":")
    return make_token(ExclamationMarkSymbolToken.new) if literal_match("!")
    return make_token(CommaSymbolToken.new) if literal_match(",")
    return make_token(EqualSymbolToken.new) if literal_match("=")

    return make_token(ImportKeywordToken.new) if word_match("import")
    return make_token(TypeKeywordToken.new) if word_match("type")
    return make_token(EnumKeywordToken.new) if word_match("enum")
    return make_token(GetKeywordToken.new) if word_match("get")
    return make_token(FunctionKeywordToken.new) if word_match("function")
    return make_token(SubscribeKeywordToken.new) if word_match("subscribe")
    return make_token(ErrorKeywordToken.new) if word_match("error")
    return make_token(PrimitiveTypeToken.new("bool")) if word_match("bool")
    return make_token(PrimitiveTypeToken.new("int")) if word_match("int")
    return make_token(PrimitiveTypeToken.new("uint")) if word_match("uint")
    return make_token(PrimitiveTypeToken.new("float")) if word_match("float")
    return make_token(PrimitiveTypeToken.new("string")) if word_match("string")
    return make_token(PrimitiveTypeToken.new("datetime")) if word_match("datetime")
    return make_token(PrimitiveTypeToken.new("date")) if word_match("date")
    return make_token(PrimitiveTypeToken.new("bytes")) if word_match("bytes")
    return make_token(PrimitiveTypeToken.new("void")) if word_match("void")

    if current_char!.letter?
      while current_char && (current_char!.letter? || current_char!.number?)
        next_char
      end
      return make_token(IdentifierToken.new(@raw[@start...@pos]))
    end

    if current_char! == '$'
      next_char
      while current_char && (current_char!.letter? || current_char!.number?)
        next_char
      end
      return make_token(GlobalOptionToken.new(@raw[@start+1...@pos]))
    end

    if current_char! == '"'
      return string_match
    end

    until [' ', '\t', '\r', '\n'].includes? current_char
      next_char
    end

    raise LexerException.new("Invalid token at #{@filename}:#{@line}:#{@col}: \"#{@raw[@start...@pos]}\"")
  end

  private def skip
    while true
      if [' ', '\t', '\r', '\n'].includes? current_char
        next_char
        next
      end

      if current_char == '/' && peek_next_char == '/'
        while true
          break if next_char == '\n'
        end
        next_char
        next
      end

      break
    end
  end

  private def start_token
    while @start < @pos
      if @raw[@start] == '\n'
        @line += 1
        @col = 0
      else
        @col += 1
      end
      @start += 1
    end
  end

  private def make_token(token)
    token.filename = @filename
    token.line = @line
    token.col = @col
    token
  end

  private def literal_match(str : String)
    str.each_char.with_index.each do |c, i|
      return false unless @raw[@pos+i]? == c
    end

    @pos += str.size
  end

  private def word_match(str : String)
    after = @raw[@pos+str.size]?
    return false if after && after.letter?
    return literal_match(str)
  end

  private def string_match
    unless current_char != "\""
      raise LexerException.new("BUG: Expected string start here")
    end

    next_char
    contents = String::Builder.new;
    while true
      c = current_char
      unless c
        raise LexerException.new("Unexpected end of input inside string literal at #{@filename}:#{@line}:#{@col}")
      end
      if c == '"'
        next_char
        break
      end
      if c == '\\'
        next_char
        case current_char
        when 'n'
          contents << '\n'
        when 'r'
          contents << '\r'
        when 't'
          contents << '\t'
        when '"'
          contents << '\"'
        when '\\'
          contents << '\\'
        when nil
          raise LexerException.new("Unexpected end of input on escape sequence inside string literal at #{@filename}:#{@line}:#{@col}")
        else
          contents << current_char!
        end
        next_char
        next
      end
      contents << c
      next_char
    end
    return make_token(StringLiteralToken.new(contents.to_s))
  end
end

class Token
  property! filename : String
  property! line : Int32
  property! col : Int32

  def try_ident
    self
  end

  def location
    "#{filename}:#{line}:#{col}"
  end
end

class ImportKeywordToken < Token
  def try_ident
    IdentifierToken.new("import")
  end
end

class TypeKeywordToken < Token
  def try_ident
    IdentifierToken.new("type")
  end
end

class EnumKeywordToken < Token
  def try_ident
    IdentifierToken.new("type")
  end
end

class GetKeywordToken < Token
  def try_ident
    IdentifierToken.new("get")
  end
end

class FunctionKeywordToken < Token
  def try_ident
    IdentifierToken.new("function")
  end
end

class SubscribeKeywordToken < Token
  def try_ident
    IdentifierToken.new("subscribe")
  end
end

class ErrorKeywordToken < Token
  def try_ident
    IdentifierToken.new("error")
  end
end

class PrimitiveTypeToken < Token
  property name : String
  def initialize(@name)
  end

  def try_ident
    IdentifierToken.new(@name)
  end
end

class IdentifierToken < Token
  property name : String
  def initialize(@name)
  end
end

class GlobalOptionToken < Token
  property name : String
  def initialize(@name)
  end
end

class StringLiteralToken < Token
  property str : String
  def initialize(@str)
  end
end

class EqualSymbolToken < Token
end

class ExclamationMarkSymbolToken < Token
end

class CurlyOpenSymbolToken < Token
end

class CurlyCloseSymbolToken < Token
end

class ParensOpenSymbolToken < Token
end

class ParensCloseSymbolToken < Token
end

class ColonSymbolToken < Token
end

class OptionalSymbolToken < Token
end

class ArraySymbolToken < Token
end

class CommaSymbolToken < Token
end