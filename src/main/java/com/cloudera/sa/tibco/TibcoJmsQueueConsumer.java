package com.cloudera.sa.tibco;

import com.cloudera.sa.tibco.handler.BaseHandler;

import javax.jms.*;

// TODO: ExceptionListener

public class TibcoJmsQueueConsumer {
    private static final boolean TRANSACTED = true;

    private final ConnectionFactory factory;
    private final Connection connection;
    private final Session session;
    private final Destination destination;
    private final MessageConsumer consumer;

    public TibcoJmsQueueConsumer(
            final ConnectionFactory factory, final Connection connection,
            final Session session, final Destination destination,
            final MessageConsumer consumer) throws JMSException {
        this.factory = factory;
        this.connection = connection;
        this.session = session;
        this.destination = destination;
        this.consumer = consumer;
    }

    public void start() throws JMSException {
        connection.start();
    }

    public void close() throws JMSException {
        session.close();
        connection.close();
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
        private Destination destination;
        private MessageConsumer consumer;

        public Builder(
                final String serverUrl,
                final String userName, final String password) throws JMSException {
            this.factory = new com.tibco.tibjms.TibjmsConnectionFactory(serverUrl);
            this.connection = factory.createConnection(userName, password);
            this.session = this.connection.createSession(TRANSACTED, Session.SESSION_TRANSACTED);
        }

        public Builder withDestination(final String destination) throws JMSException {
            this.destination = this.session.createQueue(destination);
            return this;
        }

        public Builder withConsumer(final Destination destination) throws JMSException {
            this.consumer = this.session.createConsumer(destination);
            return this;
        }

        public Builder withConsumer() throws JMSException {
            this.consumer = this.session.createConsumer(this.destination);
            return this;
        }

        public Builder withMessageListener(BaseHandler handler) throws JMSException {
            this.consumer.setMessageListener(
                new TibcoRuleMessageListener(this.session, handler));
            return this;
        }

        public Builder withMessageListener(final MessageListener listener) throws JMSException {
            this.consumer.setMessageListener(listener);
            return this;
        }

        public TibcoJmsQueueConsumer build() throws JMSException {
            return new TibcoJmsQueueConsumer(
                this.factory, this.connection, this.session,
                this.destination, this.consumer);
        }
    }
}
