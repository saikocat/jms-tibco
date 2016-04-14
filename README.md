# JMS TIBCO

<<<<<<< HEAD
<<<<<<< HEAD
Transactional (Exact-One semantic) for Tibco JMS

See `Runner.java` to customize your need

*See INSTALL.md to install local repo for tibco jar*

```
$ mvn package
$ java -jar target/JmsWithTibco-0.1.0-SNAPSHOT-jar-with-dependencies.jar
```
=======
Note that the Tibco jar is taken from
`hadooppdn36:/var/lib/flume-ng/plugins.d/tibco-jms/lib/tibjms.jar`. It's checked into this repo for convenience - it's only 300KB.
=======
Java code that sends and receives messages from TIBCO JMS queues.
>>>>>>> eb83fb9... Tweak README

## Usage

### Compile and run

    $ mvn package
    $ java -jar target/JmsWithTibco-0.1.0-SNAPSHOT-jar-with-dependencies.jar

See `Runner.java` for sample code on how to setup and teardown.

### Run unit tests

    $ mvn test

## Background

### TIBCO JAR

The TIBCO JAR is taken from
`hadooppdn36:/var/lib/flume-ng/plugins.d/tibco-jms/lib/tibjms.jar`. It's
checked into this repo for convenience - it's only 300KB.

### Transactional Delivery and ACK Pattern

Refer to the following resources to better understand the Transactional and
Reliability of JMS queues:

* [Transaction and redelivery in JMS](http://www.javaworld.com/article/2074123/java-web-development/transaction-and-redelivery-in-jms.html)
* [JMS Delivery Reliability and Acknowledge Patterns](http://wso2.com/library/articles/2013/01/jms-message-delivery-reliability-acknowledgement-patterns/)
* [Reliable JMS with Transactions](https://www.atomikos.com/Publications/ReliableJmsWithTransactions)
* [Java Doc for Session](https://docs.oracle.com/javaee/7/api/javax/jms/Session.html)

Our producer is synchronous (allows batching with `queue(String text,
Autocommit=false)`), while our consumer is asynchronous (using
`MessageListener`). The producer must `commit()` after every batch. When there
are exceptions, `rollback()` should be called for the consumer or producer.

## Handling new XML messages

For new XML message types, create the POJOs as per normal and then implement
the interface `BaseHandler` and its method `processMessage`. For consumer
initialization, create a `MessageListener` with your new handler as the
parameter.

## Jackson XML deserializer and Java gotchas

### Type Erasure

Because of Type Erasure in Java (List, Set, Map), the Jackson deserializer
needs to include the `TypeInference{}` class initialization every time the
deserialize function is invoked. So we keep it as `Array[T]` instead to
maintain the type.

### Data Transfer Object (DTO)

<<<<<<< HEAD
### Efficient SerDes util class
`TibcoRuleMessageSerDes` is a util class (no initialization) with a bunch of useful static method without side effect. Notably, `E deserialize(String text, Class<E> clazz)` is useful for generic type inference. Save a lots of time writing individual POJO deserializer (but if your POJOs require complex logics, go ahead and create your own signature)
>>>>>>> 62ba763... Update README with (rehashed) doc and other notes (#TIBCO-22)
=======
For DTO, public visibility is used for fields instead of getters and setters.
These should be `final`, but Jackson dislikes `final` fields that do not have
default constructor and setter methods. A compromise on public fields and
immutable objects marred by Jackson's limitations.

### Efficient SerDes utilities class

`TibcoRuleMessageSerDes` is an utilities class (without initialization) with a
number of useful static methods that do not have any side effects. Notably, `E
deserialize(String text, Class<E> clazz)` is useful for generic type inference.
It saves a lots of time when writing individual POJO deserializers. However, if
your POJOs require complex logic, go ahead and write your own custom code.
>>>>>>> eb83fb9... Tweak README
