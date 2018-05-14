import { ArrayType, AstRoot, Field, GetOperation, IntPrimitiveType, OptionalType, StructType, TypeDefinition } from "../src/ast";
import { Lexer } from "../src/syntax/lexer";
import { Parser, primitiveToAstClass } from "../src/syntax/parser";

describe(Parser, () => {
    for (const p of Lexer.PRIMITIVES) {
        test(`handles primitive type '${p}'`, () => {
            expectParses(`
                type Foo {
                    foo: ${p}
                }
            `, new AstRoot([
                new TypeDefinition(
                    "Foo",
                    new StructType(
                        [
                            new Field("foo", new (primitiveToAstClass.get(p)!))
                        ],
                        []
                    )
                )
            ]));
        });

        test(`handles simple get operations for primitive type '${p}'`, () => {
            expectParses(`
                get foo(): ${p}
                get bar(): ${p}?
                get baz(): ${p}[]
            `, new AstRoot(
                [],
                [
                    new GetOperation(
                        "foo",
                        [],
                        new (primitiveToAstClass.get(p)!)
                    ),
                    new GetOperation(
                        "bar",
                        [],
                        new OptionalType(new (primitiveToAstClass.get(p)!))
                    ),
                    new GetOperation(
                        "baz",
                        [],
                        new ArrayType(new (primitiveToAstClass.get(p)!))
                    )
                ]
            ));
        });
    }

    for (const kw of ["type", "get", "function", "enum", "import", "error", "void"].concat(Lexer.PRIMITIVES)) {
        test(`handles '${kw}' on the name of a field`, () => {
            expectParses(`
                type Foo {
                    ${kw}: int
                }
            `, new AstRoot([
                new TypeDefinition(
                    "Foo",
                    new StructType(
                        [
                            new Field(kw, new IntPrimitiveType)
                        ],
                        []
                    )
                )
            ]));
        });
    }

    test("handles arrays and optionals", () => {
        expectParses(`
            type Foo {
                aa: string[]
                bbb: int?[]??
                cccc: int[][][]
                ddddd: uint[][][]??[]???[][]
            }
        `);
    });

    test("handles errors", () => {
        expectParses(`
            error Foo
            error Bar
            error FooBar
        `);
    });

    test("handles options on the top", () => {
        expectParses(`
            $url = "api.cubos.io/sdkgenspec"
        `);
    });

    test("handles combinations of all part", () => {
        expectParses(`
            $url = "api.cubos.io/sdkgenspec"

            error Foo
            error Bar

            type Baz {
                a: string?
                b: int
            }

            get baz(): Baz
        `);
    });

    test("fails when field happens twice", () => {
        expectDoesntParse(`
            type Baz {
                a: int
                b: bool
                a: int
            }
        `, "redeclare");

        expectDoesntParse(`
            type Baz {
                b: int
                xx: bool
                xx: int
            }
        `, "redeclare");

        expectDoesntParse(`
            function foo(a: string, a: int)
        `, "redeclare");
    });

    test("handles spreads in structs", () => {
        expectParses(`
            type Foo {
                ...Bar
                ...Baz
                aa: string
            }
        `);
    });
});

function expectParses(source: string, expectedAst?: AstRoot) {
    const parser = new Parser(new Lexer(source));
    const ast = parser.parse();

    if (expectedAst)
        expect(JSON.stringify(ast, null, 2)).toEqual(JSON.stringify(expectedAst, null, 2));
}

function expectDoesntParse(source: string, message: string) {
    const parser = new Parser(new Lexer(source));
    expect(() => parser.parse()).toThrowError(message);
}
