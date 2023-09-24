package swe4.Client;

import swe4.Client.interfaces.Repository;
import swe4.entities.Device;
import swe4.entities.Reservation;
import swe4.entities.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ServerRepository implements Repository {
  private static ServerRepository instance;
  public static ServerRepository getInstance() {
    if (instance == null) instance = new ServerRepository();
    return instance;
  }
  @Override
  public User[] getAllUsers() {
    return null;
  }

  @Override
  public User getUserByUsername(String username) {
    return null;
  }

  @Override
  public boolean addUser(String name, String username, String password, String type) {
    return false;
  }

  @Override
  public boolean updateUser(String usernameBeforeUpdate, String name, String username, String password, String type) {
    return false;
  }

  @Override
  public void deleteUser(String username) {

  }

  @Override
  public boolean authenticateUser(String username, String password) {
    return false;
  }

  @Override
  public Device[] getAllDevicesAdmin() {
    return null;
  }

  @Override
  public Device[] searchDevicesByInventoryId(String invNr) {
    return null;
  }

  @Override
  public Device[] searchDevicesByName(String name) {
    return null;
  }

  @Override
  public Device[] searchDevicesByBrand(String brand) {
    return null;
  }

  @Override
  public Device[] searchDevicesByModel(String model) {
    return null;
  }

  @Override
  public Device[] searchDevicesByCategory(String category) {
    return null;
  }

  @Override
  public Device[] getAllDevicesUser() {
    return null;
  }

  @Override
  public boolean addDevice(String inventoryId, String inventoryCode, String name, String brand, String model, String serialNr, String roomNr, LocalDate buyDate, LocalDate logDate, BigDecimal price, String status, String comments, String category) {
    return false;
  }

  @Override
  public void deleteDevice(String inventoryId) {

  }

  @Override
  public String[] getDeviceCategories() {
    return null;
  }

  @Override
  public boolean updateDevice(String inventoryIdBeforeUpdate, String inventoryCodeBeforeUpdate, String inventoryId, String inventoryCode, String name, String brand, String model, String serialNr, String roomNr, LocalDate buyDate, LocalDate disposalDate, BigDecimal price, String status, String comments, String category) {
    return false;
  }

  @Override
  public Reservation[] getAllReservations() {
    return null;
  }

  @Override
  public void deleteReservation(int reservationId) {

  }

  @Override
  public boolean updateReservation(int reservationId, LocalDate startDate, LocalDate endDate) {
    return false;
  }

  @Override
  public Reservation[] getReservationConflicts(String invId, LocalDate startDate, LocalDate endDate) {
    return null;
  }

  @Override
  public boolean reservationsOverlap(String invId, LocalDate startDate, LocalDate endDate) {
    return false;
  }

  @Override
  public void addReservation(String username, String invId, String invCode, String brand, String model, LocalDate startDate, LocalDate endDate, String status) {

  }

  @Override
  public Reservation[] searchReservationsByInvId(String invId) {
    return null;
  }

  @Override
  public Reservation[] searchReservationsByStatus(String status) {
    return null;
  }

  @Override
  public Reservation[] searchReservationsByName(String name) {
    return null;
  }
}
