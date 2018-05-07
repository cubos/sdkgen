import * as commandLineArgs from "command-line-args";
import * as commandLineUsage from "command-line-usage";

const optionDefinitions = [
    {name: "source", defaultOption: true, description: "Specifies the source file"},
    {name: "output", alias: "o", description: "Specifies the output file"},
    {name: "target", alias: "t", description: "Specifies the target platform and language"},
    {name: "help", alias: "h", type: Boolean, description: "Display this usage guide."}
];

const options: {
    source?: string
    output?: string
    target?: string
    help?: boolean
    _unknown?: string[];
} = commandLineArgs(optionDefinitions);

if (options.help) {
    console.log(commandLineUsage([
        {
            header: "Typical Example",
            content: "sdkgen src/api.sdkgen -o src/api.ts -t typescript_nodeserver"
        },
        {
            header: "Options",
            optionList: optionDefinitions
        },
        {
            content: "Project home: {underline https://github.com/cubos/sdkgen}"
        }
    ]));
    process.exit(0);
}

if (!options.source) {
    console.error("Error: Missing source option.");
    process.exit(1);
}

if (!options.output) {
    console.error("Error: Missing output option.");
    process.exit(1);
}

if (!options.target) {
    console.error("Error: Missing target option.");
    process.exit(1);
}
