package org.example.domain.entity;

import java.util.Date;

public class Order extends BaseEntity {

    String name;
    int quantity;
    Date dateShipmentContract;
    Date dateDesign;
    Date dateTechnology;
    Date dateSustain;
    Date dateShipment;

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
    
    public void setName(String name) {
        this.name = name;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}