package com.cloudera.sa.tsel.handler;

import javax.jms.TextMessage;

import java.io.IOException;
import javax.jms.JMSException;

public interface BaseHandler {

    public void processMessage(TextMessage message) throws IOException, JMSException;

}
