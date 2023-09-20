package swe4.entities;

import java.time.LocalDate;

public class Reservation {
  private int reservationId;
  private String username;
  private String rentedByName;
  private String invId;
  private String invCode;
  private String brand;
  private String model;
  private LocalDate startDate;
  private LocalDate endDate;
  private String status; //reserviert, laufend, überfällig

  public Reservation(
          int reservationId,
          String username,
          String rentedByName,
          String invId,
          String invCode,
          String brand,
          String model,
          LocalDate startDate,
          LocalDate endDate,
          String status) {
    this.reservationId = reservationId;
    this.username = username;
    this.rentedByName = rentedByName;
    this.invId = invId;
    this.invCode = invCode;
    this.brand = brand;
    this.model = model;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
  }

  public int getReservationId() {
    return reservationId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRentedByName() {
    return rentedByName;
  }

  public void setRentedByName(String rentedByName) {
    this.rentedByName = rentedByName;
  }

  public String getInvId() {
    return invId;
  }

  public void setInvId(String invId) {
    this.invId = invId;
  }

  public String getInvCode() {
    return invCode;
  }

  public void setInvCode(String invCode) {
    this.invCode = invCode;
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

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
