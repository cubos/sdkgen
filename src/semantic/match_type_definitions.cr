require "./visitor"

module Semantic
  class MatchTypeDefinitions < Visitor
    def visit(ref : AST::TypeReference)
      definition = @ast.type_definitions.find { |t| t.name == ref.name }
      unless definition
        raise "Could not find type '#{ref.name}'"
      end
      ref.type = definition.type
      super
    end
  end
end
