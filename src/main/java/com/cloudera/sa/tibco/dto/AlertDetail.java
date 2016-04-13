package com.cloudera.sa.tibco.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@JacksonXmlRootElement(localName = "Alert_Detail")
public class AlertDetail {

    private AlertDetail() { }

    public AlertDetail(
            final String alertId,
            final String kpiNumber,
            final String msisdn) {
        this.alertId = alertId;
        this.kpiNumber = kpiNumber;
        this.msisdn = msisdn;
    }

    @JacksonXmlProperty(localName = "Alert_ID")
    public String alertId;

    @JacksonXmlProperty(localName = "KPI_Number")
    public String kpiNumber;

    @JacksonXmlProperty(localName = "MSISDN")
    public String msisdn;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("Alert_ID", this.alertId)
            .add("KPI_Number", this.kpiNumber)
            .add("MSISDN", this.msisdn)
            .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final AlertDetail other = (AlertDetail) obj;
        return Objects.equal(this.alertId, other.alertId)
            && Objects.equal(this.kpiNumber, other.kpiNumber)
            && Objects.equal(this.msisdn, other.msisdn);
    }

    public static class Builder {
        private String alertId;
        private String kpiNumber;
        private String msisdn;

        public Builder() {
        }

        public Builder withAlertId(final String alertId) {
            this.alertId = alertId;
            return this;
        }

        public Builder withKpiNumber(final String kpiNumber) {
            this.kpiNumber = kpiNumber;
            return this;
        }

        public Builder withMsisdn(final String msisdn) {
            this.msisdn = msisdn;
            return this;
        }

        public AlertDetail build() {
            return new AlertDetail(this.alertId, this.kpiNumber, this.msisdn);
        }
    }
}
