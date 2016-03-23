package com.cloudera.sa.tsel;

import com.tibco.tibjms.Tibjms;
import com.tibco.tibjms.TibjmsConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.TextMessage;

import javax.jms.JMSException;

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

        public Builder withMessageListener() throws JMSException {
            this.consumer.setMessageListener(
                new TibcoRuleMessageListener(this.session));
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
