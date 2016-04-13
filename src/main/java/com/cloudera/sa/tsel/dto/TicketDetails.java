package com.cloudera.sa.tsel.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.List;

@JacksonXmlRootElement(localName = "Ticket_Details")
public class TicketDetails {
    @JacksonXmlProperty(localName = "Ticket_Detail")
    @JacksonXmlElementWrapper(useWrapping = false)
    public TicketDetail[] ticketDetails = new TicketDetail[0];

    private TicketDetails() {}

    public TicketDetails(final TicketDetail[] ticketDetails) {
        this.ticketDetails = ticketDetails;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("Ticket_Details", this.ticketDetails)
            .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final TicketDetails other = (TicketDetails) obj;
        return java.util.Arrays.deepEquals(this.ticketDetails, other.ticketDetails);
    }

    public static class Builder {
        private TicketDetail[] ticketDetails;

        public Builder() {
        }

        public Builder withTicketDetails(List<TicketDetail> ticketDetails) {
            this.ticketDetails = new ImmutableList.Builder<TicketDetail>()
                .addAll(ticketDetails)
                .build()
                .toArray(new TicketDetail[0]);
            return this;
        }

        public TicketDetails build() {
            return new TicketDetails(this.ticketDetails);
        }
    }
}
