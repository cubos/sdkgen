require "./visitor"

module Semantic
  class ApplyStructSpreads < Visitor
    # Here we may visit the same struct multiple times
    # We must make sure we only process each one once
    @processed = Set(AST::StructType).new

    def visit(t : AST::StructType)
      return if @processed.includes? t
      @processed << t

      super

      t.spreads.map(&.type).each do |other|
        unless other.is_a? AST::StructType
          raise SemanticException.new("A spread operator in a struct can't refer to something that is not a struct, in '#{t.name}'.")
        end
        visit other # recursion!

        other.fields.each do |other_field|
          if t.fields.find { |f| f.name == other_field.name }
            raise SemanticException.new("The field '#{other_field.name}' happens on both '#{t.name}' and '#{other.name}'.")
          end
          t.fields << other_field
        end
      end
    end
  end
end
