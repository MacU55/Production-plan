package org.example.dto;

import java.util.Date;

public class DesignDeptDTO extends BaseDTO {

    private int designId;
    private int orderId;
    private Date dateDesignCompleted;
    private String designName;


    public void setDesignId(int designId) {
        this.designId = designId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public void setDateDesignCompleted(Date dateDesignCompleted) {
        this.dateDesignCompleted = dateDesignCompleted;
    }
    public void setDesignName(String designName){this.designName = designName; }

    public int getDesignId() {
        return this.designId;
    }
    public int getOrderId() { return this.orderId;}
    public Date getDateDesignCompleted() {
        return this.dateDesignCompleted;
    }
    public String getDesignName() {
        return this.designName;
    }

}

