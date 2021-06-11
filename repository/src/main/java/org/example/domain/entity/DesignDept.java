package org.example.domain.entity;

import java.util.Date;

public class DesignDept extends BaseEntity {

    int orderId;
    Date dateDesignCompleted;
    String designName;

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setDateDesignCompleted(Date dateDesignCompleted) {
        this.dateDesignCompleted = dateDesignCompleted;
    }

    public void setDesignName(String designName) { this.designName = designName; }

    public int getOrderId() { return this.orderId; }

    public Date getDateDesignCompleted() {
        return this.dateDesignCompleted;
    }

    public String getDesignName() { return this.designName; }
}














