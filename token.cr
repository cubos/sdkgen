
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