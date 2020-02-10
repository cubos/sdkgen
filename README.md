# sdkgen 

---
---
---

**THIS REPOSITORY IS DEPRECATED**. See https://github.com/sdkgen/sdkgen.

---
---
---

![Zé Polvinho](assets/ze_polvinho_sdkgen_small.png)

The sdkgen is a tool that aims on solving client-server communication and data layout sharing between server, web, android and ios using a description language also called sdkgen.

[![Build Status](https://travis-ci.org/cubos/sdkgen.svg?branch=master)](https://travis-ci.org/cubos/sdkgen)

## How to use it

1) Install Crystal following instructions from: https://crystal-lang.org/docs/installation/;
2) Download this repository;
3) Run sdkgen in order to get the desired `sdk`. For more details about the command, check the next section:
`crystal run main.cr -- api.sdkgen -o api.ts -t typescript_nodeserver`

## Options

`crystal run main.cr -- <SDKGEN FILE> -o <OUTPUT FILE> -t <TARGET>`

- `<SDKGEN FILE>`: .sdkgen file containing the API contract, check the next section to see more details about the syntax;
- `<OUTPUT FILE>`: filename for the generated file;
- `<TARGET>`: the target platform for where you are generating the sdk. Options avaiable are:
    - `java_android`
    - `kt_android`
    - `swift_ios`
    - `typescript_nodeclient`
    - `typescript_nodeserver`
    - `typescript_servertest`
    - `typescript_web`

## Syntax

The .sdkgen file is responsible for declaring the API contract and for that purpose it implements the following syntax:

```
type Message {
    date: date
    author: User
    mentions: User[]
    text: string?
}

// Sends a message, returns nothing
function sendMessage(target: string, message: Message): void
```

The primitive types available are:

- `bool`: Either `true` or `false`
- `int`: an integer from -2147483648 to 2147483647
- `uint`: a positive integer, from 0 to 4294967295
- `float`: a floating-point number, equivalent to a `double` or `float64` from other languages
- `string`: an UTF-8 encoded sequence of characters. This is not meant to store binary data, only printable/readable characters. There is a soft limit of 65535 chars in a string.
- `date`: A point in time with millisecond precision. Timezone information is not preserved and will be converted to the local timezone of the receiver always.
- `bytes`: Arbitrary binary data.
- `void`: Special type that means the lack of value. Can only be used as the return type of a `function` operation (more on that later)

You can check the complete description on [language.md](language.md).
