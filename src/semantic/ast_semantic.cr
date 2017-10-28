require "./visitor"
require "./match_type_definitions"
require "./check_no_recursive_types"
require "./check_dont_return_secret"
require "./check_naming_for_getters_returning_bool"
require "./check_empty_enum"
require "./give_struct_and_enum_names"
require "./collect_struct_and_enum_types"
require "./check_multiple_declaration"

module Semantic
  class SemanticException < Exception
  end
end

module AST
  class ApiDescription
    property struct_types = [] of AST::StructType
    property enum_types = [] of AST::EnumType

    def semantic
      errors << "Fatal"
      errors << "Connection"
      error_types_enum = AST::EnumType.new
      error_types_enum.values = errors
      error_types_enum_def = AST::TypeDefinition.new
      error_types_enum_def.name = "ErrorType"
      error_types_enum_def.type = error_types_enum
      type_definitions << error_types_enum_def

      op = AST::FunctionOperation.new
      op.name = "ping"
      op.return_type = AST::StringPrimitiveType.new
      operations << op

      op = AST::FunctionOperation.new
      op.name = "setPushToken"
      op.args = [AST::Field.new]
      op.args[0].name = "token"
      op.args[0].type = AST::StringPrimitiveType.new
      op.return_type = AST::VoidPrimitiveType.new
      operations << op

      Semantic::CheckMultipleDeclaration.visit(self)
      Semantic::MatchTypeDefinitions.visit(self)
      Semantic::CheckNoRecursiveTypes.visit(self)
      Semantic::CheckDontReturnSecret.visit(self)
      Semantic::CheckNamingForGettersReturningBool.visit(self)
      Semantic::GiveStructAndEnumNames.visit(self)
      Semantic::CheckEmptyEnum.visit(self)
      Semantic::CollectStructAndEnumTypes.visit(self)
    end
  end

  class TypeReference
    property! type : Type
  end

  class StructType
    property! name : String
  end

  class EnumType
    property! name : String
  end

  abstract class Operation
    def pretty_name
      name
    end
  end

  class GetOperation < Operation
    def pretty_name
      return_type.is_a?(BoolPrimitiveType) ? name : "get" + name[0].upcase + name[1..-1]
    end
  end
end
