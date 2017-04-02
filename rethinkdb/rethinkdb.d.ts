
interface DBDevice {
    id: string;
    type: "android" | "ios" | "web";
    platform: any;
    screen: {width: number, height: number};
    version: string;
    language: string;
}

interface DBApiCall {
    id: string;
    name: string;
    args: any;
    executionId: string;
    running: boolean;
    device: DBDevice;
    date: Date;
    duration: number;
    host: string;
    ok: boolean;
    result: any;
    error: {type: string, message: string} | null;
}

interface R_Sorting {}

interface R extends RDB {
    db(name: string): RDB
    dbList(): RArray<string>
    dbCreate(name: string): RDatum<{}>
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
    not(obj: any): RDatum<boolean>
    and(...objs: any[]): RDatum<boolean>
    or(...objs: any[]): RDatum<boolean>
    now(): RDatum<Date>
    asc(name: string): R_Sorting
    desc(name: string): R_Sorting
    args(array: any): any
    row: RTableRow<any>
    minval: RDatum<never>
    maxval: RDatum<never>
    error(message: string): RDatum<never>
}

interface RDB {
    table(name: string): RTable<any>
    table(name: "devices"): RTable<DBDevice>;
    table(name: "api_calls"): RTable<DBApiCall>;
    tableList(): RArray<string>
    tableDrop(name: string): RDatum<{tables_dropped: 1, config_changes: {old_val: R_TableConfig, new_val: null}}>
    tableCreate(name: string, opts?: R_TableCreateOptions): RDatum<{tables_created: 1, config_changes: {old_val: null, new_val: R_TableConfig}}>
}

type RPrimitive = null | string | number | Date | boolean | Buffer

type RDatumfy<T> = {
    [P in keyof T]: T[P] | RDatum<T[P]> | RDatumfy<T[P]>;
};

type DeepPartial<T> = {
    [P in keyof T]?: DeepPartial<T[P]>;
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
    orderBy(field: string | R_Sorting): RArray<any>
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
    concatMap(func: (e: RDatum<any>) => any): RArray<any>
    sub(other: any): RDatum<any>
    add(...others: any[]): RDatum<any>
    append(other: any): RDatum<T>
    limit(other: any): RDatum<any>

    filter(criteria: (obj: any) => boolean | RDatum<boolean>): RDatum<T>
    filter(obj: any): RDatum<T>
    contains(obj: any): RDatum<boolean>

    eq(other: any): RDatum<boolean>
    ne(other: any): RDatum<boolean>
    gt(other: any): RDatum<boolean>
    lt(other: any): RDatum<boolean>
    ge(other: any): RDatum<boolean>
    le(other: any): RDatum<boolean>

    not(): RDatum<boolean>
    and(...objs: any[]): RDatum<boolean>
    or(...objs: any[]): RDatum<boolean>

    split(by: string): RArray<any>
    coerceTo(type: "array"): RArray<any>
    coerceTo(type: "string"): RDatum<string>

    setInsert(other: any): RArray<any>
    setUnion(other: any): RArray<any>
    setIntersection(other: any): RArray<any>
    setDifference(other: any): RArray<any>
    append(value: any): RArray<any>
    prepend(value: any): RArray<any>
    difference(other: any): RArray<any>

    sum(): RDatum<any>
    sum(idx: string): RDatum<any>
    avg(): RDatum<any>
    avg(idx: string): RDatum<any>
    min(): RDatum<any>
    min(idx: string): RDatum<any>
    max(): RDatum<any>
    max(idx: string): RDatum<any>

    ungroup(): RArray<{group: any, reduction: any}>
}

interface RArray<T> extends RDatum<T[]> {
    (idx: number | RDatum<any>): RDatum<T>
    <K extends keyof T>(idx: K): RArray<T[K]>
    map(func: (e: RDatum<T>) => any): RArray<any>
    concatMap(func: (e: RDatum<T>) => any): RArray<any>
    orderBy(field: string | R_Sorting): RArray<T>
    append(other: T): RArray<T>
    filter(criteria: (obj: RDatum<T>) => boolean | RDatum<boolean>): RArray<T>
    filter(obj: DeepPartial<RDatumfy<T>>): RArray<T>
    limit(other: any): RArray<T>
    contains(obj: T): RDatum<boolean>
    reduce(func: (a: RDatum<T>, b: RDatum<T>) => any): RDatum<T>

    setInsert(other: any): RArray<T>
    setUnion(other: any): RArray<T>
    setIntersection(other: any): RArray<T>
    setDifference(other: any): RArray<T>
    append(value: any): RArray<T>
    prepend(value: any): RArray<T>
    difference(other: any): RArray<T>

    sum(): RDatum<T>
    sum<K extends keyof T>(idx: K): RDatum<T[K]>
    avg(): RDatum<T>
    avg<K extends keyof T>(idx: K): RDatum<T[K]>
    min(): RDatum<T>
    min<K extends keyof T>(idx: K): RDatum<T[K]>
    max(): RDatum<T>
    max<K extends keyof T>(idx: K): RDatum<T[K]>

    group<K extends keyof T>(idx: K): RGroupedStream<T[K], T>
}

interface RStream<T> extends PromiseLike<T[]>, RStreamOrDatum<T[]> {
    (idx: number): RDatum<T>
    (field: string): RArray<any>
    map(func: (arg: RDatum<T>) => any): RStream<any>
    concatMap(func: (arg: RDatum<T>) => any): RStream<any>
    orderBy(field: string | R_Sorting): RArray<T>
    orderBy(options: {index: string | R_Sorting}): RStream<T>
    coerceTo(type: "array"): RArray<T>
    filter(criteria: (obj: RDatum<T>) => boolean | RDatum<boolean>): RStream<T>
    filter(obj: DeepPartial<RDatumfy<T>>): RStream<T>
    limit(other: any): RStream<T>
    reduce(func: (a: RDatum<T>, b: RDatum<T>) => any): RDatum<T>

    sum(): RDatum<T>
    sum<K extends keyof T>(idx: K): RDatum<T[K]>
    avg(): RDatum<T>
    avg<K extends keyof T>(idx: K): RDatum<T[K]>
    min(): RDatum<T>
    min<K extends keyof T>(idx: K): RDatum<T[K]>
    max(): RDatum<T>
    max<K extends keyof T>(idx: K): RDatum<T[K]>

    group<K extends keyof T>(idx: K): RGroupedStream<T[K], T>
}

interface RGroupedStream<G, T> extends RArray<T> {
    ungroup(): RArray<{group: G, reduction: T[]}>
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

type RUpdateObj<T> = RDatum<T> | DeepPartial<T>

interface RTableSlice<T extends object> extends RStream<T> {
    update<Opts extends R_UpdateOptions & {returnChanges: true | "always"}>(obj: (obj: RDatum<T>) => any, options: Opts): RDatum<R_UpdateResult & {changes: {new_val: T, old_val: T}[]}>
    update(obj: (obj: RDatum<T>) => any, options?: R_UpdateOptions): RDatum<R_UpdateResult>
    update<Opts extends R_UpdateOptions & {returnChanges: true | "always"}>(obj: RUpdateObj<T>, options: Opts): RDatum<R_UpdateResult & {changes: {new_val: T, old_val: T}[]}>
    update(obj: RUpdateObj<T>, options?: R_UpdateOptions): RDatum<R_UpdateResult>
    delete(): RDatum<{}>
}

interface RTableRow<T extends object> extends RDatum<T> {
    update<Opts extends R_UpdateOptions & {returnChanges: true | "always"}>(obj: (obj: RDatum<T>) => any, options: Opts): RDatum<R_UpdateResult & {changes: {new_val: T, old_val: T}[]}>
    update(obj: (obj: RDatum<T>) => any, options?: R_UpdateOptions): RDatum<R_UpdateResult>
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

    getAll(id: any, opts?: {index: string}): RTableSlice<T>
    getAll(id1: any, id2: any, opts?: {index: string}): RTableSlice<T>
    getAll(id1: any, id2: any, id3: any, opts?: {index: string}): RTableSlice<T>
    getAll(id1: any, id2: any, id3: any, id4: any, opts?: {index: string}): RTableSlice<T>
    between(lower: any, upper: any, opts?: {index: string}): RTableSlice<T>
}
