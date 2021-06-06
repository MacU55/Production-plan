package org.example.dto;

import java.util.Date;

public class OrderDTO extends BaseDTO {

    private int id;
    private String name;
    private int quantity;
    private Date dateShipmentContract;
    private Date dateDesign;
    private Date dateTechnology;
    private Date dateSustain;
    private Date dateShipment;

    public void setId(int orderId) {
         this.id = orderId;
    }
    public void setName(String orderName) {
        this.name = orderName;
    }
    public void setQuantity(int orderQuantity) {
        this.quantity = orderQuantity;
    }
    public void setDateShipmentContract(Date dateShipmentContract) {
        this.dateShipmentContract = dateShipmentContract;
    }
    public void setDateDesign(Date dateDesign) {
        this.dateDesign = dateDesign;
    }
    public void setDateTechnology(Date dateTechnology) {
        this.dateTechnology = dateTechnology;
    }
    public void setDateSustain(Date dateSustain) {
        this.dateSustain = dateSustain;
    }
    public void setDateShipment(Date dateShipment) {
        this.dateShipment = dateShipment;
    }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public Date getDateShipmentContract() {
        return this.dateShipmentContract;
    }
    public Date getDateDesign() {
        return this.dateDesign;
    }
    public Date getDateTechnology() {
        return this.dateTechnology;
    }
    public Date getDateSustain() {
        return this.dateSustain;
    }
    public Date getDateShipment() {
        return this.dateShipment;
    }
}
