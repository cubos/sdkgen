
let r: R;

export function connect(db: string) {
    r = require("rethinkdbdash")({
        db: db,
        pingInterval: 20,
        servers: [{
            host: "rethinkdb",
            port: 28015
        }]
    });

    return r;
}

export interface Description {
    [name: string]: {
        indices?: any[]
    }
}

export async function updateDb(dbDescr: any) {
    const tables = Object.keys(dbDescr);
    const realTables = await r.tableList();

    for (let i = 0; i < realTables.length; ++i) {
        if (tables.indexOf(realTables[i]) >= 0) continue;
        console.log(`Dropping table ${realTables[i]}...`);
        await r.tableDrop(realTables[i]);
    }

    for (let i = 0; i < tables.length; ++i) {
        const table = tables[i];
        if (realTables.indexOf(table) < 0) {
            console.log(`Creating table ${table}...`);
            await r.tableCreate(table);
        }

        const indicesCreationFunc = dbDescr[table].indices || [];
        const indices = indicesCreationFunc.map((creationFunc: any) => {
            const match = /^r\.table\("([^"]+)"\)\.indexCreate\("([^"]+)"/.exec(creationFunc.toString());
            if (match === null)
                throw "Invalid index expression: creationFunc.toString()";
            if (match[1] !== table)
                throw `Invalid index expression: Should use table ${table}, but uses ${match[1]}: creationFunc.toString()`;

            return match[2];
        });

        const realIndices = await r.table(table).indexList();

        for (let i = 0; i < realIndices.length; ++i) {
            if (indices.indexOf(realIndices[i]) >= 0) continue;
            console.log(`Dropping index ${table}.${realIndices[i]}...`);
            await r.table(table).indexDrop(realIndices[i]);
        }

        for (let i = 0; i < indices.length; ++i) {
            if (realIndices.indexOf(indices[i]) < 0) {
                console.log(`Creating index ${table}.${indices[i]}...`);
                await indicesCreationFunc[i];
            } else {
                const status = await r.table(table).indexStatus(indices[i])(0);

                let realDescr = status.query;
                let descr = indicesCreationFunc[i].toString();

                {
                    const match = /(function[\s\S]*\})/.exec(realDescr);
                    if (!match) throw `Index function doesn't contain a function??? ${JSON.stringify(realDescr)}`;
                    realDescr = match[1];
                }

                {
                    const match = /(function[\s\S]*\})/.exec(descr);
                    if (!match) descr = `function (var_1) { return var_1("${indices[i]}") }`;
                    else descr = match[1];
                }

                realDescr = global["eval"](`(${realDescr})`).toString();
                descr = global["eval"](`(${descr})`).toString();

                descr = descr.replace(/\n\s*/g, " ");
                realDescr = realDescr.replace(/\.getField/g, "");
                realDescr = realDescr.replace(/;/g, "");
                const varMapping: {[orig: string]: string} = {};
                const varMatches = /(_?var\d+)/.exec(realDescr) || [];
                for (let i = 0; i < varMatches.length; ++i) {
                    if (varMapping[varMatches[i]] === undefined) {
                        varMapping[varMatches[i]] = "var_" + (Object.keys(varMapping).length + 1);
                    }
                    realDescr = realDescr.replace(varMatches[i], varMapping[varMatches[i]]);
                }

                if (realDescr !== descr) {
                    console.log(`Recreating index ${table}.${indices[i]}...`);
                    await r.table(table).indexDrop(indices[i]);
                    await indicesCreationFunc[i];
                }
            }
        }
    }

    console.log("Database structure is ready");
}

export interface R extends RDB {
    db(name: string): RDB
    expr<X>(obj: any): RDatum<X>
    uuid(): RDatum<string>
    range(): RStream<number>
    range(count: number): RStream<number>
    range(initial: number, count: number): RStream<number>
    epochTime(epoch: number): RDatum<Date>
}

export interface RDB {
    table(name: string): RTable<any>
    tableList(): RArray<string>
    tableDrop(name: string): RDatum<{tables_dropped: 1, config_changes: {old_val: R_TableConfig, new_val: null}}>
    tableCreate(name: string, opts?: R_TableCreateOptions): RDatum<{tables_created: 1, config_changes: {old_val: null, new_val: R_TableConfig}}>
}

export type RPrimitive = null | string | number | Date | boolean | Buffer

export type RDatumfy<T> = {
    [P in keyof T]: T[P] | RDatum<T[P]>;
};

export interface R_TableConfig {

}

export interface R_TableCreateOptions {
    primaryKey?: string
    durability?: "soft" | "hard"
    shards?: number
    replicas?: number | {[server: string]: number}
    primaryReplicaTag?: string
}

export interface RStreamOrDatum<T> {
    count(): RDatum<number>
    avg(): RDatum<number>
    min(): RDatum<any>
    max(): RDatum<any>
    orderBy(field: string): RArray<any>
}

export interface RDatum<T> extends RStreamOrDatum<T>, PromiseLike<T> {
    do<X extends RPrimitive>(func: (obj: this) => X): RDatum<X>
    do<X>(func: (obj: this) => any): RDatum<X>
    default<X extends RPrimitive>(val: X): RDatum<T|X>
    default<X>(val: any): RDatum<X>
    <K extends keyof T>(idx: K): RDatum<T[K]>
    (idx: number | RDatum<any>): RDatum<any>
    merge(op: (e: RDatum<any>) => any): RDatum<any>
    merge(op: any): RDatum<any>
    map(func: (e: RDatum<any>) => any): RArray<any>
    sub(other: any): RDatum<any>
    add(...others: any[]): RDatum<any>
    append(other: any): RDatum<T>
}

export interface RArray<T> extends RDatum<T[]> {
    (idx: number | RDatum<any>): RDatum<T>
    map(func: (e: RDatum<T>) => any): RArray<any>
    orderBy(field: string): RArray<T>
    append(other: T): RArray<T>
}

export interface RStream<T> extends PromiseLike<T[]>, RStreamOrDatum<T[]> {
    (idx: number): RDatum<T>
    map(func: (arg: RDatum<T>) => any): RStream<any>
    orderBy(field: string): RArray<T>
    coerceTo(type: "array"): RArray<T>
}

export interface R_UpdateOptions {
    durability?: "hard" | "soft"
    returnChanges?: true | false | "always"
    nonAtomic?: boolean
}

export interface R_UpdateResult {
    replaced: number
    unchanged: number
    skipped: number
    errors: number
    first_error?: string
    deleted: 0
    inserted: 0
}

export type RUpdateObj<T> = Partial<T> | RDatum<T> | ((obj: RDatum<T>) => any)

export interface RTableSlice<T extends object> extends RStream<T> {
    update<Opts extends R_UpdateOptions & {returnChanges: true | "always"}>(obj: RUpdateObj<T>, options: Opts): RDatum<R_UpdateResult & {changes: {new_val: T, old_val: T}[]}>
    update(obj: RUpdateObj<T>, options?: R_UpdateOptions): RDatum<R_UpdateResult>
    delete(): RDatum<{}>
}

export interface RTableRow<T extends object> extends RDatum<T> {
    update<Opts extends R_UpdateOptions & {returnChanges: true | "always"}>(obj: RUpdateObj<T>, options: Opts): RDatum<R_UpdateResult & {changes: {new_val: T, old_val: T}[]}>
    update(obj: RUpdateObj<T>, options?: R_UpdateOptions): RDatum<R_UpdateResult>
    delete(): RDatum<{}>
}

export interface R_InsertOptions<T extends object> {
    durability?: "hard" | "soft"
    returnChanges?: true | false | "always"
    conflict?: "error" | "replace" | "update" | ((id: string, oldDoc: RDatum<T>, newDoc: RDatum<T>) => any)
}

export interface R_InsertResult {
    inserted: number
    replaced: number
    unchanged: number
    errors: number
    first_error?: string
    deleted: 0
    skipped: 0
    generated_keys: string[]
    warnings?: string[]
}

export interface R_IndexStatus {
    index: string
    ready: boolean
    progress?: number
    function: Buffer
    multi: boolean
    geo: boolean
    outdated: boolean
    query: string
}

export type RInsertObj<T> = RDatumfy<T> | RDatum<T> | RStream<T> | RDatum<T[]> | RDatumfy<T>[] | (() => RInsertObj<T>)

export interface RTable<T extends object> extends RTableSlice<T> {
    get(id: any): RTableRow<T>
    insert<Opts extends R_InsertOptions<T> & {returnChanges: true | "always"}>(obj: RInsertObj<T>, options: Opts): RDatum<R_InsertResult & {changes: {new_val: T, old_val: T}[]}>
    insert(obj: RInsertObj<T>, options?: R_InsertOptions<T>): RDatum<R_InsertResult>

    indexList(): RArray<string>
    indexCreate(name: string, func: (obj: RDatum<T>) => any, opts?: {multi?: boolean, geo?: boolean}): RDatum<{created: 1}>
    indexCreate<K extends keyof T>(name: K, opts?: {multi?: boolean, geo?: boolean}): RDatum<{created: 1}>
    indexDrop(name: string): RDatum<{dropped: 1}>
    indexStatus(...names: string[]): RArray<R_IndexStatus>

    getAll(id: any, opts: {index: string}): RStream<T>
    getAll(id1: any, id2: any, opts: {index: string}): RStream<T>
    getAll(id1: any, id2: any, id3: any, opts: {index: string}): RStream<T>
    getAll(id1: any, id2: any, id3: any, id4: any, opts: {index: string}): RStream<T>
}
