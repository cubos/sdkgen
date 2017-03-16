
interface R extends RDB {
    db(name: string): RDB
    expr<X>(obj: any): RDatum<X>
    uuid(): RDatum<string>
    range(): RStream<number>
    range(count: number): RStream<number>
    range(initial: number, count: number): RStream<number>
    epochTime(epoch: number): RDatum<Date>
    add(...objs: any[]): RDatum<any>
    branch(cond1: any, case1: any, otherwise: any): RDatum<any>
    branch(cond1: any, case1: any, cond2: any, case2: any, otherwise: any): RDatum<any>
    branch(cond1: any, case1: any, cond2: any, case2: any, cond3: any, case3: any, otherwise: any): RDatum<any>
}

interface RDB {
    table(name: string): RTable<any>
    tableList(): RArray<string>
    tableDrop(name: string): RDatum<{tables_dropped: 1, config_changes: {old_val: R_TableConfig, new_val: null}}>
    tableCreate(name: string, opts?: R_TableCreateOptions): RDatum<{tables_created: 1, config_changes: {old_val: null, new_val: R_TableConfig}}>
}

type RPrimitive = null | string | number | Date | boolean | Buffer

type RDatumfy<T> = {
    [P in keyof T]: T[P] | RDatum<T[P]>;
};

interface R_TableConfig {

}

interface R_TableCreateOptions {
    primaryKey?: string
    durability?: "soft" | "hard"
    shards?: number
    replicas?: number | {[server: string]: number}
    primaryReplicaTag?: string
}

interface RStreamOrDatum<T> {
    count(): RDatum<number>
    avg(): RDatum<number>
    min(): RDatum<any>
    max(): RDatum<any>
    orderBy(field: string): RArray<any>
}

interface RDatum<T> extends RStreamOrDatum<T>, PromiseLike<T> {
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

interface RArray<T> extends RDatum<T[]> {
    (idx: number | RDatum<any>): RDatum<T>
    map(func: (e: RDatum<T>) => any): RArray<any>
    orderBy(field: string): RArray<T>
    append(other: T): RArray<T>
}

interface RStream<T> extends PromiseLike<T[]>, RStreamOrDatum<T[]> {
    (idx: number): RDatum<T>
    map(func: (arg: RDatum<T>) => any): RStream<any>
    orderBy(field: string): RArray<T>
    coerceTo(type: "array"): RArray<T>
}

interface R_UpdateOptions {
    durability?: "hard" | "soft"
    returnChanges?: true | false | "always"
    nonAtomic?: boolean
}

interface R_UpdateResult {
    replaced: number
    unchanged: number
    skipped: number
    errors: number
    first_error?: string
    deleted: 0
    inserted: 0
}

type RUpdateObj<T> = Partial<T> | RDatum<T> | ((obj: RDatum<T>) => any)

interface RTableSlice<T extends object> extends RStream<T> {
    update<Opts extends R_UpdateOptions & {returnChanges: true | "always"}>(obj: RUpdateObj<T>, options: Opts): RDatum<R_UpdateResult & {changes: {new_val: T, old_val: T}[]}>
    update(obj: RUpdateObj<T>, options?: R_UpdateOptions): RDatum<R_UpdateResult>
    delete(): RDatum<{}>
}

interface RTableRow<T extends object> extends RDatum<T> {
    update<Opts extends R_UpdateOptions & {returnChanges: true | "always"}>(obj: RUpdateObj<T>, options: Opts): RDatum<R_UpdateResult & {changes: {new_val: T, old_val: T}[]}>
    update(obj: RUpdateObj<T>, options?: R_UpdateOptions): RDatum<R_UpdateResult>
    delete(): RDatum<{}>
}

interface R_InsertOptions<T extends object> {
    durability?: "hard" | "soft"
    returnChanges?: true | false | "always"
    conflict?: "error" | "replace" | "update" | ((id: string, oldDoc: RDatum<T>, newDoc: RDatum<T>) => any)
}

interface R_InsertResult {
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

interface R_IndexStatus {
    index: string
    ready: boolean
    progress?: number
    function: Buffer
    multi: boolean
    geo: boolean
    outdated: boolean
    query: string
}

type RInsertObj<T> = RDatumfy<T> | RDatum<T> | RStream<T> | RDatum<T[]> | RDatumfy<T>[] | (() => RInsertObj<T>)

interface RTable<T extends object> extends RTableSlice<T> {
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
