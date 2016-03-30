package com.cloudera.sa.tsel.handler;

import com.cloudera.sa.tsel.dto.*;
import com.cloudera.sa.tsel.TibcoRuleMessageSerDes;

import javax.jms.TextMessage;

import java.io.IOException;
import javax.jms.JMSException;

public class KpiDetailHandler implements BaseHandler {

    @Override
    public void processMessage(TextMessage message) throws IOException, JMSException {
        KpiDetails kpiDetails =
            TibcoRuleMessageSerDes.deserializeKpiDetails(message.getText());
        System.out.println(kpiDetails);
    }
}
