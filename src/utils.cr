module RandomGen
    @@gen = Random::PCG32.new(17)
    def self.random_u
        @@gen.next_u
    end
end

def random_var
    "x#{RandomGen.random_u}"
end

def ident(code)
  code.split("\n").map { |line| "    " + line }.join("\n").gsub(/\n\s+$/m, "\n")
end
