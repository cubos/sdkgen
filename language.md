The sdkgen has a language to describe the client-server communication, all available functions/operations and the data layout.

    // This is a comment

## Primitive types

The primitive types are:

- `bool`: Either `true` or `false`
- `int`: an integer from -2147483648 to 2147483647
- `uint`: a positive integer, from 0 to 4294967295
- `real`: a floating-point number, equivalent to a `double` or `float64` from other languages
- `string`: an UTF-8 encoded sequence of characters. This is not meant to store binary data, only printable/readable characters. There is a soft limit of 65535 in a string.
- `date`: A point in time with millisecond precision. Timezone information is not preserved and will be converted to the local timezone of the receiver always.
- `bytes`: Arbitrary binary data.
- `void`: Special type that means the lack of value. Can only be used as the return type of a `function` operation (more on that later)

## Array types

An array can be made of any type by appending `[]` at the end. For example:

    string[]     // An array of strings
    int[]        // An array of ints
    string[][]   // An array of arrays of strings

## Optional types

Types can be made optional by appending `?` at the end. If the type is an array, it must be `[]?`, not `?[]`. For example:

    string?      // Maybe a string
    bool[]?      // Maybe a bool array

## Classes

Custom types can be created with a collection of fields. Each field have a name and a type. A class name starts with a upper case letter. The field syntax is `name: type`. The class name can then be used as a type anywhere.

For example:

    class Message {
        date: date
        author: User
        mentions: User[]
        text: string?
    }

## Client and Server

The client targets are:

- `android`: Java
- `ios`: Swift
- `web`: TypeScript
- `desktop`: C++/Qt

Every running client must have a version following [Semantic Versioning](https://semver.org/). Not that a older client should be able to communicate with a newer server. The other way around (newer client with older server) is not garanteed.

The communication protocol is HTTP (either 1.0, 1.1 or 2.0) with JSON messages. A request can expect a direct response or a stream of partial responses. This is implemented with HTTP Streamming.

#### Note on HTTP Streamming

A normal HTTP request is made, from that the server can send an incomplete response and keep the connection open for sending the rest of it. The client can then read partial data and act on it.

When in stream mode, the server will send several JSON responses separated by a newline character (`\n`). The client will read and parse the JSON everytime it sees the newline. Periodically the server will send a simple JSON ping just to make sure the connection is kept open.

- **Pros**: This works anywhere and do not depend on any special support from browsers, proxies and etc. It works best on HTTP2, but will fallback to HTTP1 without code. It is fully transparent.

- **Cons**: Exclusively for browsers: Due to limitations on the `XMLHttpRequest` api, all data ever sent on that stream will be kept in memory until the stream is closed. Other plataforms will discard data as they are read. The impact of this is not that significant because streams are not meant to stay open for days, and will not handle too much data. They are intended for very short messages. This can be worked around using HTTP2 ServerSentEvents, but code becomes significantly more complex.

## Operations:

Operations are represented as functions called by the client and executed by the server. They come in three kinds:

- `get`: This operation will fetch some data from the server into the client, it is meant to be a read-only operation. This type of operation can support cache. A connection failure by default should a non blocking warning to the end user. This can be safely retried on failure.
- `function`: This operation will do some action and optionally return some information about it. It is not cacheable, but supports "do eventually" semantics. On a connection failure, a more intrusive error is shown to the end user by default. There will be no automatic retries.
- `subscribe`: This request is always stream based and will keep a connection open receiving events from the server.

Examples:

    // Fetchs a profile by id if it exists
    get userProfile(userId: string): Profile?
    
    // Sends a message, returns nothing
    function sendMessage(target: string, message: Message): void

    // Subscribes for new messages
    // The "return type" is the event
    subscribe messages(since: date): Message

## Marks

A mark can be added after a field or a operation parameter to add some special meaning. A starts with an exclamation and is followed by some word. Supported marks are:

- `!secret`: Specifies that this value is of sensitive nature and should be omited from all logs.

For example:

    class CardData {
        cardNumber: string !secret
        holder: string
        month: uint
        year: uint
        ccv: string !secret
    }

    function logIn(username: string, password: string !secret): User

## Annotations

Operations and classes can be annoted with extra information. Each annotation has a different meaning. All of them start with `@` followed by a word, followed by parenthesis and some argument. Here are the supported annotations:

- `@version`: Restrict this class or operation to only exist in one particular client version. You can create multiple classes/operations with the same name as long as they don't exist in the same version. The argument is a list of restrictions per plataform. Each plataform can have a conditional checking for versions. Possible forms:
  - `android > 1.2`: This matches `1.3` | `1.5.2` | `2.1`, but not `1.0` | `1.2` | `1.2.1`.
  - `android >= 1.2`: This matches `1.3` | `1.5.2` | `2.1` | `1.2` | `1.2.1`, but not `1.0`.
  - `android == 1.3`: This matches `1.3` | `1.3.5`, but not `1.0` | `1.4`.
  - `android <= 1.2`: This matches `1.0` | `1.2` | `1.2.1` but not `1.3` | `1.5.2` | `2.1`.
  - `android < 1.2`: This matches `1.0`, but not `1.3` | `1.5.2` | `2.1` | `1.2` | `1.2.1`.
  - `1.1 < android < 1.5`: Similar as above. Both `<` and `<=` can be used
  - `android`: Any version of android

  Plataforms can be `android`, `ios`, `web` and `desktop`. Multiple conditionals can be combined with `||`. Example:

      @version(android <= 1.2 || ios <= 1.7)
      class Message {
          text: string
      }

      @version(android > 1.2 || ios > 1.7)
      class Message {
          text: bytes
      }

      @version(android) function doThing(): void
      @version(ios) function doThing(value: int): void
    
  Note: when creating multiple things with the same name, you will need to use `@name` to create a unique name for them (used in server code).
  
- `@name`: Specifies a name for this class or operation. This is only necessary if you have multiple things with the same name in the code.

  Example:

      @version(android) @name(doThingForAndroid) function doThing(): void
      @version(ios) @name(doThingForIos) function doThing(value: int): void

  This name only affects server code (that have to handle all client versions). The client generated code is always targeted at one specific plataform/version and has no name conflicts. This annotation is ignored for them.

- `@cache`: Only appliable to `get` operations. The argument can be `weak`, which menas the function will use cache if available but always try to get the lastest information online. Or the argument can be a time duration for how long the cache is valid. Syntax:
  - `7d`: one week
  - `5h`: five hours
  - `10m`: ten minutes
  - `2d 12h`: two and a half days

  Example:

      @cache(weak) get currentUser(): User?
      @cache(1d) get privacyPolicy(): string
