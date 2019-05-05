import { Token } from "./syntax/token";

export class AstRoot {
    structTypes: StructType[] = [];
    enumTypes: EnumType[] = [];

    constructor(
        public typeDefinitions: TypeDefinition[] = [],
        public operations: Operation[] = [],
        public options: Options = new Options,
        public errors: string[] = []
    ) {}
}

export abstract class AstNode {
    public filename = "?"
    public line = 0
    public column = 0

    constructor() {
        Object.defineProperty(this, "filename", { enumerable: false });
        Object.defineProperty(this, "line", { enumerable: false });
        Object.defineProperty(this, "column", { enumerable: false });
    }

    at(token: Token): this {
        this.filename = token.filename;
        this.line = token.line;
        this.column = token.column;
        return this;
    }

    get location() {
        return `${this.filename}:${this.line}:${this.column}`;
    }
}

export abstract class Type extends AstNode {}
export abstract class PrimitiveType extends Type {}
export class StringPrimitiveType extends PrimitiveType {}
export class IntPrimitiveType extends PrimitiveType {}
export class UIntPrimitiveType extends PrimitiveType {}
export class FloatPrimitiveType extends PrimitiveType {}
export class DatePrimitiveType extends PrimitiveType {}
export class DateTimePrimitiveType extends PrimitiveType {}
export class BoolPrimitiveType extends PrimitiveType {}
export class BytesPrimitiveType extends PrimitiveType {}
export class VoidPrimitiveType extends PrimitiveType {}
export class MoneyPrimitiveType extends PrimitiveType {}
export class CpfPrimitiveType extends PrimitiveType {}
export class CnpjPrimitiveType extends PrimitiveType {}
export class EmailPrimitiveType extends PrimitiveType {}
export class PhonePrimitiveType extends PrimitiveType {}
export class CepPrimitiveType extends PrimitiveType {}
export class LatLngPrimitiveType extends PrimitiveType {}
export class UrlPrimitiveType extends PrimitiveType {}
export class UuidPrimitiveType extends PrimitiveType {}
export class HexPrimitiveType extends PrimitiveType {}
export class Base64PrimitiveType extends PrimitiveType {}
export class SafeHtmlPrimitiveType extends PrimitiveType {}
export class XmlPrimitiveType extends PrimitiveType {}

export class OptionalType extends Type {
    constructor(public base: Type) { super(); }
}

export class ArrayType extends Type {
    constructor(public base: Type) { super(); }
}

export class EnumType extends Type {
    name!: string
    constructor(public values: string[]) { super(); }
}

export class StructType extends Type {
    name!: string
    constructor(public fields: Field[], public spreads: TypeReference[]) { super(); }
}

export class TypeDefinition extends AstNode {
    constructor(public name: string, public type: Type) { super(); }
}

export class TypeReference extends Type {
    type!: Type
    constructor(public name: string) { super(); }
}

export class Options extends AstNode {
    constructor(
        public url = "",
        public useRethink = true,
        public strict = false,
        public syntheticDefaultImports = true,
        public retryRequest = true
    ) { super(); }
}

export class Field extends AstNode {
    constructor(
        public name: string,
        public type: Type,
        public secret = false
    ) { super(); }
}

export abstract class Operation extends AstNode {
    constructor(
        public name: string,
        public args: Field[],
        public returnType: Type
    ) { super(); }
}

export class GetOperation extends Operation {}
export class FunctionOperation extends Operation {}
