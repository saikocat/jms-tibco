package com.cloudera.sa.tibco;

import com.tibco.tibjms.TibjmsConnectionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.jms.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TibcoJmsQueueProducerTest {

    private TibcoJmsQueueProducer queueProducer;
    private Session session;
    private MessageProducer producer;

    private static final String INPUT_TEXT = "<node>Content</node>";

    @Before
    public void setup() throws Exception {
        session = mock(Session.class);
        producer = mock(MessageProducer.class);

        Destination destination = mock(Destination.class);
        Connection connection = mock(Connection.class);
        ConnectionFactory connectionFactory = mock(TibjmsConnectionFactory.class);

        this.queueProducer = new TibcoJmsQueueProducer(
                connectionFactory, connection, session, destination, producer);
    }

    @Test
    public void shouldQueueTextMessage() throws Exception {
        when(session.createTextMessage(INPUT_TEXT)).thenAnswer(new Answer<TextMessage>() {
            @Override
            public TextMessage answer(InvocationOnMock invocationOnMock) throws Throwable {
                TextMessage mockTextMessage = mock(TextMessage.class);
                when(mockTextMessage.getText()).thenReturn((String) invocationOnMock.getArguments()[0]);
                return mockTextMessage;
            }
        });

        this.queueProducer.queue(INPUT_TEXT, true);

        ArgumentCaptor<TextMessage> argument = ArgumentCaptor.forClass(TextMessage.class);
        verify(producer, times(1)).send(argument.capture());
        assertEquals(argument.getValue().getText(), INPUT_TEXT);
    }

    @Test
    public void shouldCommit() throws JMSException {
        final boolean[] isCommit = {false};
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                isCommit[0] = true;
                return null;
            }
        }).when(session).commit();

        this.queueProducer.commit();
        assertTrue("is commited", isCommit[0]);

    }

    @Test
    public void shouldRollback() throws JMSException {
        final boolean[] isRollback = {false};
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                isRollback[0] = true;
                return null;
            }
        }).when(session).rollback();

        this.queueProducer.rollback();
        assertTrue("is rollbacked", isRollback[0]);
    }

}
