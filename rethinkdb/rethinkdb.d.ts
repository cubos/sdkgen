
interface DBDevice {
    id: string;
    type: "android" | "ios" | "web";
    platform: any;
    screen: {width: number, height: number};
    version: string;
    language: string;
    push?: string
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

interface R_Sorting<T> { __dummy: string }

interface RPoint {
    coordinates: [number, number];
    type: "Point";
}

interface R extends RDB {
    db(name: string): RDB
    dbList(): RArray<string>
    dbCreate(name: string): RDatum<{}>
    expr(obj: any): RDatum<any>
    point(longitude: number, latitude: number): RPoint;
    uuid(): RDatum<string>
    range(): RStream<number>
    range(count: number): RStream<number>
    range(initial: number, count: number): RStream<number>
    epochTime(epoch: number): RDatum<Date>
    time(year: number | RDatum<number>, month: number | RDatum<number>, day: number | RDatum<number>, tz: string | RDatum<string>): RDatum<Date>
    time(year: number | RDatum<number>, month: number | RDatum<number>, day: number | RDatum<number>, hour: number | RDatum<number>, minute: number | RDatum<number>, second: number | RDatum<number>, tz: string | RDatum<string>): RDatum<Date>
    add(...objs: any[]): RDatum<any>
    branch(c1: any, v1: any, otherwise: any): RDatum<any>
    branch(c1: any, v1: any, c2: any, v2: any, otherwise: any): RDatum<any>
    branch(c1: any, v1: any, c2: any, v2: any, c3: any, v3: any, otherwise: any): RDatum<any>
    branch(c1: any, v1: any, c2: any, v2: any, c3: any, v3: any, c4: any, v4: any, otherwise: any): RDatum<any>
    branch(c1: any, v1: any, c2: any, v2: any, c3: any, v3: any, c4: any, v4: any, c5: any, v5: any, otherwise: any): RDatum<any>
    branch(c1: any, v1: any, c2: any, v2: any, c3: any, v3: any, c4: any, v4: any, c5: any, v5: any, c6: any, v6: any, otherwise: any): RDatum<any>
    branch(c1: any, v1: any, c2: any, v2: any, c3: any, v3: any, c4: any, v4: any, c5: any, v5: any, c6: any, v6: any, c7: any, v7: any, otherwise: any): RDatum<any>
    not(obj: any): RDatum<boolean>
    and(...objs: any[]): RDatum<boolean>
    or(...objs: any[]): RDatum<boolean>
    now(): RDatum<Date>
    asc<T>(name: T): R_Sorting<T>
    desc<T>(name: T): R_Sorting<T>
    args(array: any): any
    row: RTableRow<any>
    minval: RDatum<never>
    maxval: RDatum<never>
    error(message: string): RDatum<never>
    union<T1, T2>(stream1: RStream<T1>, stream2: RStream<T2>): RStream<T1 | T2>
    union<T1, T2, T3>(stream1: RStream<T1>, stream2: RStream<T2>, stream3: RStream<T3>): RStream<T1 | T2 | T3>
    union(...streams: any[]): RArray<any>
    js(code: string): RDatum<any>
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
}

interface RDatum<T> extends RStreamOrDatum<T>, PromiseLike<T> {
    run(): PromiseLike<T>
    do<X extends RPrimitive>(func: (obj: this) => X): RDatum<X>
    do(func: (obj: this) => any): RDatum<any>
    default<X extends RPrimitive>(val: X): RDatum<T|X>
    default(val: any): RDatum<any>
    <K extends keyof T>(idx: K): RDatum<T[K]>
    (idx: number | RDatum<any>): RDatum<any>
    orderBy(field: string | R_Sorting<string> | ((e: RDatum<any>) => any)): RArray<any>
    merge(op: (e: RDatum<any>) => any): RDatum<any>
    merge(op: any): RDatum<any>
    map(func: (e: RDatum<any>) => any): RArray<any>
    map<U>(other: RArray<U> | RStream<U>, func: (e: RDatum<any>, x: RDatum<U>) => any): RArray<any>
    map(other: any, func: (e: RDatum<any>, x: RDatum<any>) => any): RArray<any>
    concatMap(func: (e: RDatum<any>) => any): RArray<any>
    sub(other: any): RDatum<any>
    div(other: any): RDatum<number>
    add(...others: any[]): RDatum<any>
    mul(...others: any[]): RDatum<number>
    append(other: any): RDatum<T>
    limit(other: any): RDatum<any>
    round(): RDatum<number>
    floor(): RDatum<number>
    ceil(): RDatum<number>
    without(field: any): RDatum<any>
    pluck(...field: any[]): RDatum<any>
    match(regex: string | RDatum<string>): RDatum<any>

    filter(criteria: (obj: any) => boolean | RDatum<boolean>): RDatum<T>
    filter(obj: any): RDatum<T>
    contains(obj: any): RDatum<boolean>

    eq(other: T | RDatum<T>): RDatum<boolean>
    ne(other: T | RDatum<T>): RDatum<boolean>
    gt(other: T | RDatum<T>): RDatum<boolean>
    lt(other: T | RDatum<T>): RDatum<boolean>
    ge(other: T | RDatum<T>): RDatum<boolean>
    le(other: T | RDatum<T>): RDatum<boolean>

    not(): RDatum<boolean>
    and(...objs: any[]): RDatum<boolean>
    or(...objs: any[]): RDatum<boolean>

    year(): RDatum<number>
    month(): RDatum<number>
    day(): RDatum<number>
    hours(): RDatum<number>
    minutes(): RDatum<number>
    seconds(): RDatum<number>

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

    group(idx: string): RGroupedStream<any, any>
    group(func: (obj: RDatum<any>) => any): RGroupedStream<any, any>
    ungroup(): RArray<{group: any, reduction: any}>
    forEach(func: (e: RDatum<any>) => any): RDatum<{}>

    fold(base: any, func: (acc: RDatum<any>, row: RDatum<any>) => any, options?: {emit: (state: RDatum<any>, row: RDatum<any>, newState: RDatum<any>) => any}): RDatum<any>

    hasFields(fields: Array<keyof T>): RDatum<T>
    hasFields(field: keyof T): RDatum<T>
}

interface RArray<T> extends RDatum<T[]> {
    (idx: number | RDatum<any>): RDatum<T>
    <K extends keyof T>(idx: K): RArray<T[K]>
    map(func: (e: RDatum<T>) => any): RArray<any>
    map<U>(other: RArray<U> | RStream<U>, func: (e: RDatum<T>, x: RDatum<U>) => any): RArray<any>
    map(other: any, func: (e: RDatum<T>, x: RDatum<any>) => any): RArray<any>
    merge(func: (e: RDatum<T>) => any): RArray<any>
    concatMap(func: (e: RDatum<T>) => any): RArray<any>
    orderBy(field: keyof T | R_Sorting<keyof T> | ((e: RDatum<T>) => any)): RArray<T>
    append(other: T): RArray<T>
    filter(criteria: (obj: RDatum<T>) => boolean | RDatum<boolean>): RArray<T>
    filter(obj: DeepPartial<RDatumfy<T>>): RArray<T>
    limit(other: any): RArray<T>
    contains(obj: T): RDatum<boolean>
    reduce(func: (a: RDatum<T>, b: RDatum<T>) => any): RDatum<T>
    distinct(): RArray<T>
    sample(count: number | RDatum<number>): RArray<T>

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
    group(func: (obj: RDatum<T>) => any): RGroupedStream<any, T>
    forEach(func: (e: RDatum<T>) => any): RDatum<{}>
}

interface RStream<T> extends PromiseLike<T[]>, RStreamOrDatum<T[]> {
    run(): PromiseLike<T[]>
    (idx: number): RDatum<T>
    (field: string): RArray<any>
    map(func: (arg: RDatum<T>) => any): RStream<any>
    map<U>(other: RArray<U> | RStream<U>, func: (e: RDatum<T>, x: RDatum<U>) => any): RArray<any>
    map(other: any, func: (e: RDatum<T>, x: RDatum<any>) => any): RArray<any>
    merge(func: (arg: RDatum<T>) => any): RStream<any>
    concatMap(func: (arg: RDatum<T>) => any): RStream<any>
    orderBy(field: keyof T | R_Sorting<keyof T> | ((e: RDatum<T>) => any)): RArray<T>
    orderBy(options: {index: string | R_Sorting<any>}): RStream<T>
    coerceTo(type: "array"): RArray<T>
    filter(criteria: (obj: RDatum<T>) => boolean | RDatum<boolean>): RStream<T>
    filter(obj: DeepPartial<RDatumfy<T>>): RStream<T>
    limit(other: any): RStream<T>
    reduce(func: (a: RDatum<T>, b: RDatum<T>) => any): RDatum<T>
    distinct(): RArray<T>
    sample(count: number | RDatum<number>): RStream<T>

    sum(): RDatum<T>
    sum<K extends keyof T>(idx: K): RDatum<T[K]>
    avg(): RDatum<T>
    avg<K extends keyof T>(idx: K): RDatum<T[K]>
    min(): RDatum<T>
    min<K extends keyof T>(idx: K): RDatum<T[K]>
    max(): RDatum<T>
    max<K extends keyof T>(idx: K): RDatum<T[K]>

    group<K extends keyof T>(idx: K): RGroupedStream<T[K], T>
    group(func: (obj: RDatum<T>) => any): RGroupedStream<any, T>
    forEach(func: (e: RDatum<T>) => any): RDatum<{}>
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
    replace(obj: RInsertObj<T>): RDatum<R_InsertResult>
    delete(): RDatum<{}>
    filter(criteria: (obj: RDatum<T>) => boolean | RDatum<boolean>): RTableSlice<T>
    filter(obj: DeepPartial<RDatumfy<T>>): RTableSlice<T>
    hasFields(fields: Array<keyof T>): RTableSlice<T>
    hasFields(field: keyof T): RTableSlice<T>
}

interface RTableRow<T extends object> extends RDatum<T> {
    update<Opts extends R_UpdateOptions & {returnChanges: true | "always"}>(obj: (obj: RDatum<T>) => any, options: Opts): RDatum<R_UpdateResult & {changes: {new_val: T, old_val: T}[]}>
    update(obj: (obj: RDatum<T>) => any, options?: R_UpdateOptions): RDatum<R_UpdateResult>
    update<Opts extends R_UpdateOptions & {returnChanges: true | "always"}>(obj: RUpdateObj<T>, options: Opts): RDatum<R_UpdateResult & {changes: {new_val: T, old_val: T}[]}>
    update(obj: RUpdateObj<T>, options?: R_UpdateOptions): RDatum<R_UpdateResult>
    replace(obj: RInsertObj<T>): RDatum<R_InsertResult>
    delete(): RDatum<{}>
    eq(other: T | RDatum<T> | null): RDatum<boolean>
    ne(other: T | RDatum<T> | null): RDatum<boolean>
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

type RInsertObj<T> = RDatum<T> | RStream<T> | RDatum<T[]> | RDatumfy<T>[] | (() => RInsertObj<T>) | RDatumfy<T>

interface RTable<T extends object> extends RTableSlice<T> {
    get(id: any): RTableRow<T>
    insert<Opts extends R_InsertOptions<T> & {returnChanges: true | "always"}>(obj: RInsertObj<T>, options: Opts): RDatum<R_InsertResult & {changes: {new_val: T, old_val: T}[]}>
    insert(obj: RInsertObj<T>, options?: R_InsertOptions<T>): RDatum<R_InsertResult>

    indexList(): RArray<string>
    indexCreate(name: string, func: (obj: RDatum<T>) => any, opts?: {multi?: boolean, geo?: boolean}): RDatum<{created: 1}>
    indexCreate(name: keyof T, opts?: {multi?: boolean, geo?: boolean}): RDatum<{created: 1}>
    indexDrop(name: string): RDatum<{dropped: 1}>
    indexStatus(...names: string[]): RArray<R_IndexStatus>

    getAll(id: any, opts?: {index: string}): RTableSlice<T>
    getAll(id1: any, id2: any, opts?: {index: string}): RTableSlice<T>
    getAll(id1: any, id2: any, id3: any, opts?: {index: string}): RTableSlice<T>
    getAll(id1: any, id2: any, id3: any, id4: any, opts?: {index: string}): RTableSlice<T>
    between(lower: any, upper: any, opts?: {index: string, leftBound?: "closed" | "opened", rightBound?: "closed" | "opened"}): RTableSlice<T>

    getNearest(id: RPoint, opts: { index: string, maxResults?: number, unit?: "m" | "km" | "mi" | "nm" | "ft", maxDist?: number, geoSystem?: "WGS84" | "unit_sphere" }): RTableSlice<T>;
}
