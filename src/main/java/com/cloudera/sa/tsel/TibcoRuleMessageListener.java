package com.cloudera.sa.tsel;

import javax.jms.Session;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.MessageListener;

import javax.jms.JMSException;
import java.io.IOException;

public class TibcoRuleMessageListener implements MessageListener {
    private Session session;

    public TibcoRuleMessageListener(final Session session) {
        this.session = session;
    }

    @Override
    public void onMessage(Message message) {
        TextMessage msg = null;
        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                processMessage(msg, session);
                session.commit();
            } else {
                System.out.println("Message is not a " + "TextMessage");
            }
        } catch (JMSException e) {
            System.out.println("JMSException in onMessage(): " + e.toString());
        } catch (Throwable t) {
            System.out.println("Exception in onMessage():" + t.getMessage());
        }
    }

    public void processMessage(TextMessage message, Session session) throws JMSException {
        try {
            TibcoRuleMessage ruleMessage =
                TibcoRuleMessageSerDes.deserialize(message.getText());
            System.out.println(ruleMessage);
        } catch (IOException ioe) {
            session.rollback();
        }
    }
}
