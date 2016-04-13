package com.cloudera.sa.tsel;

import javax.jms.Session;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.MessageListener;

import javax.jms.JMSException;
import java.io.IOException;

import com.cloudera.sa.tsel.handler.BaseHandler;

public class TibcoRuleMessageListener implements MessageListener {
    private Session session;
    private BaseHandler handler;

    public TibcoRuleMessageListener(final Session session, final BaseHandler handler) {
        this.session = session;
        this.handler = handler;
    }

    @Override
    public void onMessage(Message message) {
        TextMessage msg = null;
        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                processMessage(msg);
            } else {
                System.out.println("Message is not a " + "TextMessage");
            }
        } catch (JMSException e) {
            System.out.println("JMSException in onMessage(): " + e.toString());
        } catch (Throwable t) {
            System.out.println("Exception in onMessage():" + t.getMessage());
        }
    }

    // See TibcoJmsQueueConsumer.java for information about transactional delivery
    // and why commit and rollback is necessary here
    public void processMessage(TextMessage message) throws JMSException {
        try {
            handler.processMessage(message);
            session.commit();
        } catch (IOException ioe) {
            session.rollback();
        }
    }
}
