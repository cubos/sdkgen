
let r: R;

export function connect(db: string, host: string = "rethinkdb") {
    r = require("rethinkdbdash")({
        db: db,
        pingInterval: 20,
        servers: [{
            host: host,
            port: 28015
        }]
    });

    return r;
}

export interface ConfigureOptions {
    tables: {
        [name: string]: {
            indices?: any[]
        }
    }
}

export async function configure(options: ConfigureOptions) {
    const tables = Object.keys(options.tables);
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

        const indicesCreationFunc = options.tables[table].indices || [];
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
