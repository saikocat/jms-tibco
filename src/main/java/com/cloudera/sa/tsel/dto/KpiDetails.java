package com.cloudera.sa.tsel.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.List;

@JacksonXmlRootElement(localName = "KPI_Details")
public class KpiDetails {

    /*
     * Because of Type Erasure in Java (List, Set, Map), Jackson deserializer
     * will need to include TypeInference{} class initialization everytime
     * you invoke deserialize func. So keeping it as Array[T] instead to
     * maintain the type.
     */
    @JacksonXmlProperty(localName = "KPI_Detail")
    @JacksonXmlElementWrapper(useWrapping = false)
    public KpiDetail[] kpiDetails = new KpiDetail[0];

    private KpiDetails() {}

    public KpiDetails(final KpiDetail[] kpiDetails) {
        this.kpiDetails = kpiDetails;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("KPI_Detail", this.kpiDetails)
            .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final KpiDetails other = (KpiDetails) obj;
        return java.util.Arrays.deepEquals(this.kpiDetails, other.kpiDetails);
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
