export class Token {
    public filename!: string
    public line!: number
    public column!: number

    constructor(public value: string = "") { }

    get location() {
        return `${this.filename}:${this.line}:${this.column}`;
    }

    tryIdent(): Token {
        return this;
    }

    toString() {
        const name = (this.constructor as any).name.replace("Token", "");
        return this.value === "" ? name : `${name}(${JSON.stringify(this.value)})`;
    }

    equal(other: Token) {
        return this.constructor === other.constructor && this.value === other.value;
    }
}

export class ImportKeywordToken extends Token {
    tryIdent() {
        return new IdentifierToken("import");
    }
}

export class TypeKeywordToken extends Token {
    tryIdent() {
        return new IdentifierToken("type");
    }
}

export class EnumKeywordToken extends Token {
    tryIdent() {
        return new IdentifierToken("enum");
    }
}

export class GetKeywordToken extends Token {
    tryIdent() {
        return new IdentifierToken("get");
    }
}

export class FunctionKeywordToken extends Token {
    tryIdent() {
        return new IdentifierToken("function");
    }
}

export class ErrorKeywordToken extends Token {
    tryIdent() {
        return new IdentifierToken("error");
    }
}

export class TrueKeywordToken extends Token {
    tryIdent() {
        return new IdentifierToken("true");
    }
}

export class FalseKeywordToken extends Token {
    tryIdent() {
        return new IdentifierToken("false");
    }
}

export class PrimitiveTypeToken extends Token {
    tryIdent() {
        return new IdentifierToken(this.value);
    }
}

export class IdentifierToken extends Token {}
export class GlobalOptionToken extends Token {}
export class StringLiteralToken extends Token {}
export class EqualSymbolToken extends Token {}
export class ExclamationMarkSymbolToken extends Token {}
export class CurlyOpenSymbolToken extends Token {}
export class CurlyCloseSymbolToken extends Token {}
export class ParensOpenSymbolToken extends Token {}
export class ParensCloseSymbolToken extends Token {}
export class ColonSymbolToken extends Token {}
export class OptionalSymbolToken extends Token {}
export class ArraySymbolToken extends Token {}
export class CommaSymbolToken extends Token {}
export class SpreadSymbolToken extends Token {}
