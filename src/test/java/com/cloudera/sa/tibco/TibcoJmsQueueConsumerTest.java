package com.cloudera.sa.tibco;

import com.cloudera.sa.tibco.handler.BaseHandler;
import com.tibco.tibjms.TibjmsConnectionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.jms.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


// the 3 in 1 test :|
public class TibcoJmsQueueConsumerTest {

    private TibcoJmsQueueConsumer queueConsumer;
    private Session session;
    private MessageConsumer consumer;
    private TibcoRuleMessageListener listener;
    private BaseHandler handler;

    final AtomicInteger counter = new AtomicInteger(0);
    final CountDownLatch done = new CountDownLatch(1);

    @Before
    public void setup() throws Exception {
        session = mock(Session.class);
        consumer = mock(MessageConsumer.class);
        when(consumer.getMessageListener()).thenReturn(
            new TibcoRuleMessageListener(session, new BaseHandler() {
                public void processMessage(TextMessage message) {
                    counter.incrementAndGet();
                    if (counter.get() == 1) {
                        done.countDown();
                    }
                }
            }));

        Destination destination = mock(Destination.class);
        Connection connection = mock(Connection.class);
        ConnectionFactory connectionFactory = mock(TibjmsConnectionFactory.class);

        this.queueConsumer = new TibcoJmsQueueConsumer(
                connectionFactory, connection, session, destination, consumer);
    }


    @Test
    public void shouldConsumeMessage() throws Exception {
        TibcoJmsQueueConsumer spy = spy(queueConsumer);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                final TextMessage mockTextMessage = mock(TextMessage.class);
                consumer.getMessageListener().onMessage(mockTextMessage);
                return null;
            }
        }).when(spy).start();

        spy.start();

        // These 2 asserts ensure that our Listener and Handler are called
        // aka the lazy test
        assertTrue(done.await(1, TimeUnit.SECONDS));
        assertEquals(1, counter.get());
    }

    // Rollback and commit is the same as producer test
}
