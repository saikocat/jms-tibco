# JMS Tibco

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

See `Runner.java` for sample code on how to setup and teardown

    $ mvn package
    $ java -jar target/JmsWithTibco-0.1.0-SNAPSHOT-jar-with-dependencies.jar

## Transactional Delivery and Ack Pattern JMS

To understand Transactional and Reliability with JMS queue as well as other type of deliveries and consuming methods for JMS. Read these articles for background information:

* [Transaction and redelivery in JMS](http://www.javaworld.com/article/2074123/java-web-development/transaction-and-redelivery-in-jms.html)
* [JMS Delivery Reliability and Acknowledge Patterns](http://wso2.com/library/articles/2013/01/jms-message-delivery-reliability-acknowledgement-patterns/)
* [Reliable JMS with Transactions](https://www.atomikos.com/Publications/ReliableJmsWithTransactions)
* [Java Doc for Session](https://docs.oracle.com/javaee/7/api/javax/jms/Session.html)

Our producer is sync (allow batching with this function `queue(String text, Autocommit=false)`) while our consumer is async in nature (using MessageListener). Producer must `commit()` after every batch. When exception happened, `rollback()` is called for consumer or producer.

## New Type of Handler

For new type of XML message, you can create the POJOs as normal then implement the interface `BaseHandler` and its method `processMessage`. And for the consumer initialization, create a `MessageListener` with your new handler as parameter.

## Jackson XML deserializer and JAVA gotcha

### Type Erasure
Because of Type Erasure in Java (List, Set, Map), Jackson deserializer will need to include TypeInference{} class initialization everytime you invoke deserialize func. So keeping it as Array[T] instead to maintain the type.

### Data Transfer Object (DTO)
For DTO, public visibility is used for fields instead of getter and setter. By right they should be final, but jackson doesn't like final fields without default constructor and setter method. A compromise on public fields and immutable object marred by Jackson limitation.

### Efficient SerDes util class
`TibcoRuleMessageSerDes` is a util class (no initialization) with a bunch of useful static method without side effect. Notably, `E deserialize(String text, Class<E> clazz)` is useful for generic type inference. Save a lots of time writing individual POJO deserializer (but if your POJOs require complex logics, go ahead and create your own signature)
>>>>>>> 62ba763... Update README with (rehashed) doc and other notes (#TIBCO-22)
