# GNIP ~ PING

Simple host checking application.
Check process is performed with *ICMP, TCP/IP* and *TRACE*.

## Usage
    java -jar gnip-app-1.0-jar-with-dependencies.jar [options]

#### Options
    -h          Help.
    -d          Run with default properties.
    -g          Generates an example properties file.
    -p file     Run with one specific properties file.


```mermaid
sequenceDiagram
Alice ->> Bob: Hello Bob, how are you?
Bob-->>John: How about you John? 
Bob--x Alice: I am good thanks!
Bob-x John: I am good thanks!
Note right of John: Bob thinks a long<br/>long time, so long<br/>that the text does<br/>not fit on a row.

Bob-->Alice: Checking with John...
Alice->John: Yes... John, how are you?
```