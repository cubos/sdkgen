require "./target"

Target.add_target(language: "ts", is_server: true) do |description, output|
  io = IO::Memory.new

  io << <<-END
import http from "http"


END

  io << "interface Implementation {\n"
  description.operations.each do |operation|
    io << "  "
    generate_operation_declaration(io, operation)
  end
  io << "}\n\n"

  description.custom_types.each do |custom_type|
    generate_custom_type_interface(io, custom_type)
    io << "\n"
  end

  io << <<-END
export interface Device {
  id: string;
  platform: "android" | "ios" | "web" | "qt";
  version: string;
}

export interface Context {
  device: Device;
  ip: string;
  date: Date;
}

export function start(implementation: Implementation) {

}

END

  io.rewind
  File.write(output, io)
end

def native_type(t : PrimitiveType)
  case t
  when StringPrimitiveType
    "string"
  when IntPrimitiveType
    "number"
  when UIntPrimitiveType
    "number"
  when FloatPrimitiveType
    "number"
  when DatePrimitiveType
    "Date"
  when BoolPrimitiveType
    "boolean"
  when BytesPrimitiveType
    "Buffer"
  when VoidPrimitiveType
    "void"
  else
    raise "BUG! Should handle primitive #{t.class}"
  end
end

def native_type(t : OptionalType)
  native_type(t.base) + " | null"
end

def native_type(t : CustomTypeReference)
  t.name
end

def generate_custom_type_interface(io, custom_type)
  io << "export interface #{custom_type.name} {\n"
  custom_type.fields.each do |field|
    io << "  #{field.name}: #{native_type field.type};\n"
  end
  io << "}\n"
end

def native_operation_name(op : GetOperation)
  "get" + op.name[0].upcase + op.name[1..-1]
end

def native_operation_name(op : FunctionOperation | SubscribeOperation)
  op.name
end

def generate_operation_declaration(io, op : Operation)
  args = ["ctx: Context"]
  op.args.each {|arg| args << "#{arg.name}: #{native_type arg.type}" }
  ret = native_type op.return_type
  if op.is_a? SubscribeOperation
    args << "callback: (result: #{ret}) => void"
    ret = "void"
  end
  io << native_operation_name(op) << "(#{args.join(", ")}): #{ret};\n"
end