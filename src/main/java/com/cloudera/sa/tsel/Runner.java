package com.cloudera.sa.tsel;

import java.io.InputStreamReader;

import com.cloudera.sa.tsel.dto.KpiDetail;
import com.cloudera.sa.tsel.dto.KpiDetails;
import com.cloudera.sa.tsel.handler.KpiDetailHandler;

import com.google.common.collect.ImmutableList;

public class Runner {
    public static void main(String[] args) throws Exception {
        /*
        String serverUrl = "tcp://10.53.68.135:8222";
        String userName = "fms_hadoop";
        String password = "fms_hadoop";
        String destination = "test.hadoop";

        System.out.println("----- Input Data Debug -----");
        KpiDetail kd = new KpiDetail.Builder()
            .withPriority("High")
            .withNumber("tst 123")
            .withName("kpi name")
            .withCategory("cat 1")
            .withFraudType("Fraud Cat")
            .withServiceCustType("Service Cat 1")
            .withEnableSms(true)
            .withEnableEmail(false)
            .withThreshold(12345)
            .withDurationFrequency(123)
            .withEnableKpi("kpi cat")
            .withDescription("kpi description")
            .build();

        String rule = TibcoRuleMessageSerDes.serialize(kd);
        System.out.println(rule);
        KpiDetails kds = new KpiDetails.Builder()
            .withKpiDetails(ImmutableList.<KpiDetail>of(kd, kd).asList())
            .build();
        String rule2 = TibcoRuleMessageSerDes.serialize(kds);
        System.out.println(rule2);
        System.out.println(TibcoRuleMessageSerDes.deserialize(rule2, KpiDetails.class));
        System.out.println("----- End Input Data Debug -----");

        TibcoJmsQueueProducer.Builder producerBuilder =
           new TibcoJmsQueueProducer.Builder(serverUrl, userName, password);
        TibcoJmsQueueProducer producer = producerBuilder
            .withDestination(destination)
            .withProducer()
            .build();
        producer.start();
        String event = TibcoRuleMessageSerDes.serialize(kds);
        producer.queue(event);
        producer.commit();
        producer.close();
        System.out.println("* Message sent...");

        TibcoJmsQueueConsumer.Builder consumerBuilder =
           new TibcoJmsQueueConsumer.Builder(serverUrl, userName, password);
        TibcoJmsQueueConsumer consumer = consumerBuilder
            .withDestination(destination)
            .withConsumer()
            .withMessageListener(new KpiDetailHandler())
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
        */
    }
}
