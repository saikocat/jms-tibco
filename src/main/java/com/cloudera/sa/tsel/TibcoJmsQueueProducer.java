package com.cloudera.sa.tsel;

import com.tibco.tibjms.TibjmsConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import javax.jms.JMSException;

public class TibcoJmsQueueProducer {
    private static final boolean AUTOCOMMIT = false;
    private static final boolean TRANSACTED = true;

    private final ConnectionFactory factory;
    private final Connection connection;
    private final Session session;
    private final MessageProducer producer;
    private final Destination destination;

    public TibcoJmsQueueProducer(
            final ConnectionFactory factory, final Connection connection,
            final Session session, final Destination destination,
            final MessageProducer producer) throws JMSException {
        this.factory = factory;
        this.connection = connection;
        this.session = session;
        this.destination = destination;
        this.producer = producer;
    }

    public void start() throws JMSException {
        connection.start();
    }

    public void commit() throws JMSException {
        session.commit();
    }

    public void rollback() throws JMSException {
        session.rollback();
    }

    public void close() throws JMSException {
        session.close();
        connection.close();
    }

    public void queue(final String event, boolean autocommit) throws JMSException {
        TextMessage message = session.createTextMessage();
        message.setText(event);
        producer.send(message);
        if (autocommit)
            session.commit();
    }

    public void queue(final String event) throws JMSException {
        queue(event, AUTOCOMMIT);
    }

    /*
     * To understand Transactional and Reliability with JMS queue as well as
     * other type of delivery and consuming method for JMS. Read these articles:
     *  - Transaction and redelivery in JMS @ http://www.javaworld.com/article/2074123/java-web-development/transaction-and-redelivery-in-jms.html
     *  - JMS Delivery Reliability and Acknowledge Patterns @ http://wso2.com/library/articles/2013/01/jms-message-delivery-reliability-acknowledgement-patterns/
     *  - Reliable JMS with Transactions @ https://www.atomikos.com/Publications/ReliableJmsWithTransactions
     *  - Java Doc for Session @ https://docs.oracle.com/javaee/7/api/javax/jms/Session.html
     */
    public static class Builder {
        private ConnectionFactory factory;
        private Connection connection;
        private Session session;
        private MessageProducer producer;
        private Destination destination;

        public Builder(
                final String serverUrl,
                final String userName, final String password) throws JMSException {
            this.factory = new com.tibco.tibjms.TibjmsConnectionFactory(serverUrl);
            this.connection = factory.createConnection(userName, password);
            this.session = connection.createSession(TRANSACTED, Session.SESSION_TRANSACTED);
        }

        public Builder withDestination(final String destination) throws JMSException {
            this.destination = this.session.createQueue(destination);
            return this;
        }

        public Builder withProducer(final Destination destination) throws JMSException {
            this.producer = this.session.createProducer(destination);
            return this;
        }

        public Builder withProducer() throws JMSException {
            this.producer = this.session.createProducer(this.destination);
            return this;
        }

        public TibcoJmsQueueProducer build() throws JMSException {
            return new TibcoJmsQueueProducer(
                this.factory, this.connection, this.session, this.destination,
                this.producer);
        }
    }
}
