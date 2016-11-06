class Lexer

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

    return make_token(TypeKeywordToken.new) if literal_match("type")
    return make_token(GetKeywordToken.new) if literal_match("get")
    return make_token(FunctionKeywordToken.new) if literal_match("function")
    return make_token(SubscribeKeywordToken.new) if literal_match("subscribe")
    return make_token(PrimitiveTypeToken.new("bool")) if literal_match("bool")
    return make_token(PrimitiveTypeToken.new("int")) if literal_match("int")
    return make_token(PrimitiveTypeToken.new("uint")) if literal_match("uint")
    return make_token(PrimitiveTypeToken.new("float")) if literal_match("float")
    return make_token(PrimitiveTypeToken.new("string")) if literal_match("string")
    return make_token(PrimitiveTypeToken.new("date")) if literal_match("date")
    return make_token(PrimitiveTypeToken.new("bytes")) if literal_match("bytes")
    return make_token(PrimitiveTypeToken.new("void")) if literal_match("void")

    if current_char!.alpha?
      while current_char && (current_char!.alpha? || current_char!.digit? || current_char! == '_')
        next_char
      end
      return make_token(IdentifierToken.new(@raw[@start...@pos]))
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

class TypeKeywordToken < Token
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