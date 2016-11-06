require "./lexer"
require "./ast"

class Parser

  class ParserException < Exception
  end

  @token : Token | Nil

  def initialize(@lexer : Lexer)
    @token = @lexer.next_token
  end

  def parse
    api = ApiDescription.new
    while @token
      case multi_expect(TypeKeywordToken, GetKeywordToken, FunctionKeywordToken, SubscribeKeywordToken)
      when TypeKeywordToken
        api.custom_types << parse_custom_type_definition
      when GetKeywordToken, FunctionKeywordToken, SubscribeKeywordToken
        api.operations << parse_operation
      end
    end
    api
  end

  def next_token
    @token = @lexer.next_token
  end

  macro multi_expect(*token_types)
    token = @token
    unless token
      raise ParserException.new "Expected #{{{token_types.map{|t| t.stringify}.join(" or ")}}}, but found end of file"
    end

    result = nil

    {% for token_type in token_types %}
      {% if token_type.stringify == "IdentifierToken" %}
        token = token.try_ident
      {% end %}
      if token.is_a?({{token_type}})
        result = token
      end
    {% end %}

    unless result
      raise ParserException.new "Expected #{{{token_types.map{|t| t.stringify}.join(" or ")}}} at #{token.filename}:#{token.line}:#{token.col}, but found #{token.class}"
    end

    result
  end

  macro expect(token_type)
    token = @token
    unless token
      raise ParserException.new "Expected #{{{token_type}}}, but found end of file"
    end
    {% if token_type.stringify == "IdentifierToken" %}
      token = token.try_ident
    {% end %}
    unless token.is_a?({{token_type}})
      raise ParserException.new "Expected #{{{token_type}}} at #{token.filename}:#{token.line}:#{token.col}, but found #{token.class}"
    end
    token
  end

  def parse_custom_type_definition
    expect TypeKeywordToken
    next_token

    t = CustomType.new
    t.name = expect(IdentifierToken).name
    next_token

    expect CurlyOpenSymbolToken
    next_token

    while true
      case token = multi_expect(IdentifierToken, CurlyCloseSymbolToken)
      when IdentifierToken
        t.fields << parse_field
      when CurlyCloseSymbolToken
        next_token
        return t
      end
    end
  end

  def parse_operation
    op = nil
    case token = multi_expect(GetKeywordToken, FunctionKeywordToken, SubscribeKeywordToken)
    when GetKeywordToken
      op = GetOperation.new
    when FunctionKeywordToken
      op = FunctionOperation.new
    when SubscribeKeywordToken
      op = SubscribeOperation.new
    else
      raise "never"
    end

    next_token
    op.name = expect(IdentifierToken).name
    next_token
    expect ParensOpenSymbolToken
    next_token

    while true
      case token = multi_expect(IdentifierToken, ParensCloseSymbolToken, CommaSymbolToken)
      when IdentifierToken
        op.args << parse_field
      when ParensCloseSymbolToken
        next_token
        break
      when CommaSymbolToken
        next_token
        next
      end
    end

    expect ColonSymbolToken
    next_token
    op.return_type = parse_type

    op
  end

  def parse_field
    field = Field.new
    field.name = expect(IdentifierToken).name
    next_token
    expect ColonSymbolToken
    next_token
    field.type = parse_type

    while @token.is_a?(ExclamationMarkSymbolToken)
      next_token
      case (token = expect(IdentifierToken)).name
      when "secret"
        field.secret = true
      else
        raise ParserException.new "Unknown field mark !#{token.name} at #{token.filename}:#{token.line}:#{token.col}"
      end
      next_token
    end

    field
  end

  def parse_type
    result = case token = multi_expect(IdentifierToken, PrimitiveTypeToken)
    when IdentifierToken
      CustomTypeReference.new(token.name)
    when PrimitiveTypeToken
      case token.name
      when "string"; StringPrimitiveType.new
      when "int";    IntPrimitiveType.new
      when "uint";   UIntPrimitiveType.new
      when "date";   DatePrimitiveType.new
      when "real";   RealPrimitiveType.new
      when "bool";   BoolPrimitiveType.new
      when "bytes";  BytesPrimitiveType.new
      when "void";   VoidPrimitiveType.new
      else
        raise "BUG!"
      end
    else
      raise "never"
    end
    next_token

    if @token.is_a?(OptionalSymbolToken)
      next_token
      result = OptionalType.new(result)
    end

    result
  end
end
