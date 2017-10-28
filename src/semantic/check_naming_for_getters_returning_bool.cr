require "./visitor"

module Semantic
  class CheckNamingForGettersReturningBool < Visitor
    def visit(op : AST::GetOperation)
      super
      is_bool = op.return_type.is_a? AST::BoolPrimitiveType
      has_bool_name = op.name =~ /^(is|has|can|may|should)/
      if is_bool && !has_bool_name
        raise SemanticException.new("Get operation '#{op.name}' returns bool but isn't named accordingly")
      elsif !is_bool && has_bool_name
        raise SemanticException.new("Get operation '#{op.name}' doesn't return bool but its name suggest it does")
      end
    end
  end
end
