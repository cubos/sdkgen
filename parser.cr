require "./lexer"
require "./ast"
require "./ast_semantic"

class Parser
  class ParserException < Exception
  end

  @lexers = [] of Lexer
  @token : Token | Nil

  def initialize(source : String)
    @lexers << Lexer.new(source)
    next_token
  end

  def next_token
    while @lexers.size > 0
      @token = @lexers.last.next_token
      if @token
        return
      else
        @lexers.pop
      end
    end
  end

  def current_source
    @lexers.last.filename if @lexers.size > 0
  end

  def parse
    api = AST::ApiDescription.new
    while @token
      case multi_expect(ImportKeywordToken, TypeKeywordToken, GetKeywordToken, FunctionKeywordToken, SubscribeKeywordToken, GlobalOptionToken, ErrorKeywordToken)
      when ImportKeywordToken
        next_token
        token = expect StringLiteralToken
        source = File.expand_path(token.str + ".sdkgen", File.dirname(current_source.not_nil!))
        @lexers << Lexer.new(source)
        next_token
      when TypeKeywordToken
        api.type_definitions << parse_type_definition
      when GetKeywordToken, FunctionKeywordToken, SubscribeKeywordToken
        api.operations << parse_operation
      when GlobalOptionToken
        parse_option(api.options)
      when ErrorKeywordToken
        next_token
        token = expect IdentifierToken
        next_token
        api.errors << token.name
      end
    end
    api
  end

  macro multi_expect(*token_types)
    token = @token
    unless token
      raise ParserException.new "Expected #{{{token_types.map{|t| t.stringify.gsub(/Token$/, "")}.join(" or ")}}}, but found end of file"
    end

    result = nil

    {% for token_type in token_types %}
      {% if token_type.stringify == "IdentifierToken" %}
        token = token.try_ident
      {% end %}
      if !result && token.is_a?({{token_type}})
        result = token
      end
    {% end %}

    unless result
      raise ParserException.new "Expected #{{{token_types.map{|t| t.stringify.gsub(/Token$/, "")}.join(" or ")}}} at #{token.location}, but found #{token.class.to_s.gsub(/Token$/, "")}"
    end

    result
  end

  macro expect(token_type)
    token = @token
    unless token
      raise ParserException.new "Expected #{{{token_type.stringify.gsub(/Token$/, "")}}}, but found end of file"
    end
    {% if token_type.stringify == "IdentifierToken" %}
      token = token.try_ident
    {% end %}
    unless token.is_a?({{token_type}})
      raise ParserException.new "Expected #{{{token_type.stringify.gsub(/Token$/, "")}}} at #{token.location}, but found #{token.class.to_s.sub(/Token$/, "")}"
    end
    token
  end

  def parse_enum
    expect EnumKeywordToken
    next_token

    e = AST::EnumType.new

    expect CurlyOpenSymbolToken
    next_token

    while true
      case token = multi_expect(IdentifierToken, CurlyCloseSymbolToken)
      when IdentifierToken
        unless token.name[0].lowercase?
          raise ParserException.new "The enum value must start with an lowercase letter, but found '#{token.name}' at #{token.location}"
        end
        e.values << token.name
        next_token
      when CurlyCloseSymbolToken
        next_token
        return e
      end
    end
  end

  def parse_type_definition
    expect TypeKeywordToken
    next_token

    t = AST::TypeDefinition.new
    name_token = expect(IdentifierToken)
    unless name_token.name[0].uppercase?
      raise ParserException.new "The custom type name must start with an uppercase letter, but found '#{name_token.name}' at #{name_token.location}"
    end
    t.name = name_token.name
    next_token

    t.type = parse_type
    t
  end

  def parse_struct
    expect CurlyOpenSymbolToken
    next_token

    t = AST::StructType.new

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
      op = AST::GetOperation.new
    when FunctionKeywordToken
      op = AST::FunctionOperation.new
    when SubscribeKeywordToken
      op = AST::SubscribeOperation.new
    else
      raise "never"
    end

    next_token
    op.name = expect(IdentifierToken).name
    next_token

    if @token.is_a? ParensOpenSymbolToken
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
    end

    if @token.is_a? ColonSymbolToken
      expect ColonSymbolToken
      next_token
      op.return_type = parse_type
    else
      op.return_type = AST::VoidPrimitiveType.new
    end

    op
  end

  def parse_option(options)
    var = expect GlobalOptionToken
    next_token
    expect EqualSymbolToken
    next_token

    case var.name
    when "url"
      token = expect StringLiteralToken
      next_token
      options.url = token.str
    else
      raise ParserException.new("Unknown option $#{var.name} at #{var.location}")
    end
  end

  def parse_field
    field = AST::Field.new
    field.name = expect(IdentifierToken).name
    next_token
    expect ColonSymbolToken
    next_token
    field.type = parse_type(allow_void: false)

    while @token.is_a?(ExclamationMarkSymbolToken)
      next_token
      case (token = expect(IdentifierToken)).name
      when "secret"
        field.secret = true
      else
        raise ParserException.new "Unknown field mark !#{token.name} at #{token.location}"
      end
      next_token
    end

    field
  end

  def parse_type(allow_void = true)
    result = case token = multi_expect(CurlyOpenSymbolToken, EnumKeywordToken, PrimitiveTypeToken, IdentifierToken)
    when CurlyOpenSymbolToken
      return parse_struct
    when EnumKeywordToken
      return parse_enum
    when IdentifierToken
      unless token.name[0].uppercase?
        raise ParserException.new "Expected a type but found '#{token.name}', at #{token.location}"
      end
      AST::TypeReference.new(token.name)
    when PrimitiveTypeToken
      case token.name
      when "string";   AST::StringPrimitiveType.new
      when "int";      AST::IntPrimitiveType.new
      when "uint";     AST::UIntPrimitiveType.new
      when "date";     AST::DatePrimitiveType.new
      when "datetime"; AST::DateTimePrimitiveType.new
      when "float";    AST::FloatPrimitiveType.new
      when "bool";     AST::BoolPrimitiveType.new
      when "bytes";    AST::BytesPrimitiveType.new
      when "void"
        if allow_void
          AST::VoidPrimitiveType.new
        else
          raise ParserException.new "Can't use 'void' here, at #{token.location}"
        end
      else
        raise "BUG! Should handle primitive #{token.name}"
      end
    else
      raise "never"
    end
    next_token

    while @token.is_a? ArraySymbolToken || @token.is_a? OptionalSymbolToken
      if @token.is_a? ArraySymbolToken
        next_token
        result = AST::ArrayType.new(result)
      end

      if @token.is_a?(OptionalSymbolToken)
        next_token
        result = AST::OptionalType.new(result)
      end
    end

    result
  end
end
