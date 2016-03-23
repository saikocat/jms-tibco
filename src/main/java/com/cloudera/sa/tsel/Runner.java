package com.cloudera.sa.tsel;

import java.io.InputStreamReader;

public class Runner {
    public static void main(String[] args) throws Exception {
        String serverUrl = "tcp://10.53.68.135:8222";
        String userName = "fms_hadoop";
        String password = "fms_hadoop";
        String destination = "test.hadoop";

        TibcoJmsQueueProducer.Builder producerBuilder =
           new TibcoJmsQueueProducer.Builder(serverUrl, userName, password);
        TibcoJmsQueueProducer producer = producerBuilder
            .withDestination(destination)
            .withProducer()
            .build();
        producer.start();
        String event = TibcoRuleMessageSerDes.serialize(new TibcoRuleMessage(5, 6));
        producer.queue(event);
        producer.commit();
        producer.close();

        TibcoJmsQueueConsumer.Builder consumerBuilder =
           new TibcoJmsQueueConsumer.Builder(serverUrl, userName, password);
        TibcoJmsQueueConsumer consumer = consumerBuilder
            .withDestination(destination)
            .withConsumer()
            .withMessageListener()
            .build();

        consumer.start();
        char answer = ' ';
        System.out.println("To end program, type Q or q, " + "then <return>");
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        while (!((answer == 'q') || (answer == 'Q'))) { 
            try { 
                answer = (char) inputStreamReader.read(); 
            } catch (java.io.IOException e) { 
                System.out.println("I/O exception: " + e.toString()); 
            }
        }
        consumer.close();
    }
}
