module AST
  class Type
    def typescript_decode(expr)
      raise "TODO: #{self.class}"
    end

    def typescript_encode(expr)
      raise "TODO: #{self.class}"
    end

    def typescript_native_type
      raise "TODO: #{self.class}"
    end
  end
end
