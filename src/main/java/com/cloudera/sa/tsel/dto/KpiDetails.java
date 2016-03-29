package com.cloudera.sa.tsel.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.List;

@JacksonXmlRootElement(localName = "KPI_Details")
public class KpiDetails {
    @JacksonXmlProperty(localName = "KPI_Detail")
    @JacksonXmlElementWrapper(useWrapping = false)
    public KpiDetail[] kpiDetails = new KpiDetail[0];

    private KpiDetails() {}

    public KpiDetails(final KpiDetail[] kpiDetails) {
        this.kpiDetails = kpiDetails;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("KPI_Detail", this.kpiDetails)
            .toString();
    }

    public static class Builder {
        private KpiDetail[] kpiDetails; 

        public Builder() {
        }

        public Builder withKpiDetails(List<KpiDetail> kpiDetails) {
            this.kpiDetails = new ImmutableList.Builder<KpiDetail>()
                .addAll(kpiDetails)
                .build()
                .toArray(new KpiDetail[0]);
            return this;
        }

        public KpiDetails build() {
            return new KpiDetails(this.kpiDetails);
        }
    }
}
