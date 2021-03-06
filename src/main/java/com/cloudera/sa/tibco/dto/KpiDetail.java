package com.cloudera.sa.tibco.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import com.google.common.base.Objects;
import com.google.common.base.MoreObjects;

@JacksonXmlRootElement(localName = "KPI_Detail")
public class KpiDetail {
    /*
     * We want a private constructor since we want to have an immutable object
     * (params are final) once created with our builder class
     */
    private KpiDetail() { }

    /*
     * Public visibility for fields instead of getter and setter since they are
     * Data Transfer Object (DTO). By right they should be final, but jackson
     * doesn't like final fields without default constructor and setter method.
     * A compromise on public fields and immutable object marred by Jackson
     * limitation.
     */
    public KpiDetail(
            final String priority,
            final String number,
            final String name,
            final String category,
            final String fraudType,
            final String serviceCustType,
            final boolean enableSms,
            final boolean enableEmail,
            final int threshold,
            final int durationFrequency,
            final String enableKpi,
            final String description) {
        this.priority = priority;
        this.number = number;
        this.name = name;
        this.category = category;
        this.fraudType = fraudType;
        this.serviceCustType = serviceCustType;
        this.enableSms = enableSms;
        this.enableEmail = enableEmail;
        this.threshold = threshold;
        this.durationFrequency = durationFrequency;
        this.enableKpi = enableKpi;
        this.description = description;
    }

    @JacksonXmlProperty(localName = "KPI_Priority")
    public String priority = null;

    @JacksonXmlProperty(localName = "KPI_Number")
    public String number = null;

    @JacksonXmlProperty(localName = "KPI_Name")
    public String name = null;

    @JacksonXmlProperty(localName = "KPI_Category")
    public String category = null;

    @JacksonXmlProperty(localName = "Fraud_Type")
    public String fraudType = null;

    @JacksonXmlProperty(localName = "Service_Cust_Type")
    public String serviceCustType = null;

    @JacksonXmlProperty(localName = "Enable_SMS")
    public boolean enableSms = false;

    @JacksonXmlProperty(localName = "Enable_Email")
    public boolean enableEmail = false;

    @JacksonXmlProperty(localName = "Threshold")
    public int threshold = 0;

    @JacksonXmlProperty(localName = "Duration_Frequency")
    public int durationFrequency = 0;

    @JacksonXmlProperty(localName = "Enable_KPI")
    public String enableKpi = "hmph?";

    @JacksonXmlProperty(localName = "Description")
    public String description = null;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("KPI_Priority", this.priority)
            .add("KPI_Number", this.number)
            .add("KPI_Name", this.name)
            .add("KPI_Category", this.category)
            .add("Fraud_Type", this.fraudType)
            .add("Service_Cust_Type", this.serviceCustType)
            .add("Enable_SMS", this.enableSms)
            .add("Enable_Email", this.enableEmail)
            .add("Threshold", this.threshold)
            .add("Duration_Frequency", this.durationFrequency)
            .add("Enable_KPI", this.enableKpi)
            .add("Description", this.description)
            .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final KpiDetail other = (KpiDetail) obj;
        return Objects.equal(this.priority, other.priority)
            && Objects.equal(this.number, other.number)
            && Objects.equal(this.name, other.name)
            && Objects.equal(this.category, other.category)
            && Objects.equal(this.fraudType, other.fraudType)
            && Objects.equal(this.serviceCustType, other.serviceCustType)
            && Objects.equal(this.enableSms, other.enableSms)
            && Objects.equal(this.enableEmail, other.enableEmail)
            && Objects.equal(this.threshold, other.threshold)
            && Objects.equal(this.durationFrequency, other.durationFrequency)
            && Objects.equal(this.enableKpi, other.enableKpi)
            && Objects.equal(this.description, other.description);
    }

    public static class Builder {
        private String priority;
        private String number;
        private String name;
        private String category;
        private String fraudType;
        private String serviceCustType;
        private boolean enableSms;
        private boolean enableEmail;
        private int threshold;
        private int durationFrequency;
        private String enableKpi;
        private String description;

        public Builder() {
        }

        public Builder withPriority(final String priority) {
            this.priority = priority;
            return this;
        }

        public Builder withNumber(final String number) {
            this.number = number;
            return this;
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withCategory(final String category) {
            this.category = category;
            return this;
        }
        public Builder withFraudType(final String fraudType) {
            this.fraudType = fraudType;
            return this;
        }

        public Builder withServiceCustType(final String serviceCustType) {
            this.serviceCustType = serviceCustType;
            return this;
        }

        public Builder withEnableSms(final boolean enableSms) {
            this.enableSms = enableSms;
            return this;
        }

        public Builder withEnableEmail(final boolean enableEmail) {
            this.enableEmail = enableEmail;
            return this;
        }

        public Builder withThreshold(final int threshold) {
            this.threshold = threshold;
            return this;
        }

        public Builder withDurationFrequency(final int durationFrequency) {
            this.durationFrequency = durationFrequency;
            return this;
        }

        public Builder withEnableKpi(final String enableKpi) {
            this.enableKpi = enableKpi;
            return this;
        }

        public Builder withDescription(final String description) {
            this.description = description;
            return this;
        }

        public KpiDetail build() {
            return new KpiDetail(
                priority,
                number,
                name,
                category,
                fraudType,
                serviceCustType,
                enableSms,
                enableEmail,
                threshold,
                durationFrequency,
                enableKpi,
                description);
        }
    }
}
