package com.cloudera.sa.tsel.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import com.google.common.base.Objects;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.List;

@JacksonXmlRootElement(localName = "Ticket_Detail")
public class TicketDetail {

    private TicketDetail() { }

    public TicketDetail(
            final String alertId,
            final String ticketId,
            final String msisdn) {
        this.alertId = alertId;
        this.ticketId = ticketId;
        this.msisdn = msisdn;
    }

    @JacksonXmlProperty(localName = "Alert_ID")
    public String alertId;

    @JacksonXmlProperty(localName = "Ticket_ID")
    public String ticketId;

    @JacksonXmlProperty(localName = "MSISDN")
    public String msisdn;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("Alert_ID", this.alertId)
            .add("Ticket_ID", this.ticketId)
            .add("MSISDN", this.msisdn)
            .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final TicketDetail other = (TicketDetail) obj;
        return Objects.equal(this.alertId, other.alertId)
            && Objects.equal(this.ticketId, other.ticketId)
            && Objects.equal(this.msisdn, other.msisdn);
    }

    public static class Builder {
        private String alertId;
        private String ticketId;
        private String msisdn;

        public Builder() {
        }

        public Builder withAlertId(final String alertId) {
            this.alertId = alertId;
            return this;
        }

        public Builder withTicketId(final String ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public Builder withMsisdn(final String msisdn) {
            this.msisdn = msisdn;
            return this;
        }

        public TicketDetail build() {
            return new TicketDetail(this.alertId, this.ticketId, this.msisdn);
        }
    }
}
