package swe4.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Device {
  private String inventoryId,
          inventoryCode,
          name,
          brand,
          model,
          serialNr,
          roomNr,
          status,
          comments,
          category;


  private LocalDate buyDate,
          logDate,
          disposalDate;

  private BigDecimal price;

  public Device(
          String inventoryId,
          String inventoryCode,
          String name,
          String brand,
          String model,
          String serialNr,
          String roomNr,
          LocalDate buyDate,
          LocalDate logDate,
          BigDecimal price,
          String status,
          String comments,
          String category
  ) {
    this.inventoryId = inventoryId;
    this.inventoryCode = inventoryCode;
    this.name = name;
    this.brand = brand;
    this.model = model;
    this.serialNr = serialNr;
    this.roomNr = roomNr;
    this.comments = comments;
    this.buyDate = buyDate;
    this.logDate = logDate;
    this.disposalDate = null;
    this.price = price;
    this.status = status;
    this.category = category;
  }


  public String getInventoryId() {
    return inventoryId;
  }

  public void setInventoryId(String inventoryId) {
    this.inventoryId = inventoryId;
  }

  public String getInventoryCode() {
    return inventoryCode;
  }

  public void setInventoryCode(String inventoryCode) {
    this.inventoryCode = inventoryCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getSerialNr() {
    return serialNr;
  }

  public void setSerialNr(String serialNr) {
    this.serialNr = serialNr;
  }

  public String getRoomNr() {
    return roomNr;
  }

  public void setRoomNr(String roomNr) {
    this.roomNr = roomNr;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public LocalDate getBuyDate() {
    return buyDate;
  }

  public void setBuyDate(LocalDate buyDate) {
    this.buyDate = buyDate;
  }

  public LocalDate getLogDate() {
    return logDate;
  }

  public void setLogDate(LocalDate logDate) {
    this.logDate = logDate;
  }

  public LocalDate getDisposalDate() {
    return disposalDate;
  }

  public void setDisposalDate(LocalDate disposalDate) {
    this.disposalDate = disposalDate;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
}
