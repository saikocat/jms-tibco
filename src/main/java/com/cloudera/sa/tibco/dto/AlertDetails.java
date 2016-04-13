package com.cloudera.sa.tibco.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.List;

@JacksonXmlRootElement(localName = "Alert_Details")
public class AlertDetails {
    @JacksonXmlProperty(localName = "Alert_Detail")
    @JacksonXmlElementWrapper(useWrapping = false)
    public AlertDetail[] alertDetails = new AlertDetail[0];

    private AlertDetails() {}

    public AlertDetails(final AlertDetail[] alertDetails) {
        this.alertDetails = alertDetails;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("KPI_Detail", this.alertDetails)
            .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final AlertDetails other = (AlertDetails) obj;
        return java.util.Arrays.deepEquals(this.alertDetails, other.alertDetails);
    }

    public static class Builder {
        private AlertDetail[] alertDetails;

        public Builder() {
        }

        public Builder withAlertDetails(List<AlertDetail> alertDetails) {
            this.alertDetails = new ImmutableList.Builder<AlertDetail>()
                .addAll(alertDetails)
                .build()
                .toArray(new AlertDetail[0]);
            return this;
        }

        public AlertDetails build() {
            return new AlertDetails(this.alertDetails);
        }
    }
}
