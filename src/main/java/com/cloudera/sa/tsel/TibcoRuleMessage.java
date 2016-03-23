package com.cloudera.sa.tsel;

import com.google.common.base.MoreObjects;

public class TibcoRuleMessage {
    public int field_x = 1;
    public int field_y = 2;

    public TibcoRuleMessage() {
    }

    public TibcoRuleMessage(int field_x, int field_y) {
        this.field_x = field_x;
        this.field_y = field_y;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("field_x", this.field_x)
            .add("field_y", this.field_y)
            .toString();
    }
}
