package swe4.Client;


import swe4.entities.Device;
import swe4.entities.Reservation;
import swe4.entities.User;
import swe4.Client.interfaces.IRepository;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakeRepository implements IRepository {

  private static FakeRepository instance;

  public static FakeRepository getInstance() {
    if (instance == null) instance = new FakeRepository();
    return instance;
  }


  @Override
  public User[] getAllUsers() {
    return new User[0];
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
    return new Device[0];
  }

  @Override
  public Device[] searchDevicesByInventoryId(String invNr) {
    return new Device[0];
  }

  @Override
  public Device[] searchDevicesByName(String name) {
    return new Device[0];
  }

  @Override
  public Device[] searchDevicesByBrand(String brand) {
    return new Device[0];
  }

  @Override
  public Device[] searchDevicesByModel(String model) {
    return new Device[0];
  }

  @Override
  public Device[] searchDevicesByCategory(String category) {
    return new Device[0];
  }

  @Override
  public Device[] getAllDevicesUser() {
    return new Device[0];
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
    return new String[0];
  }

  @Override
  public boolean updateDevice(int deviceId, String inventoryIdBeforeUpdate, String inventoryCodeBeforeUpdate, String inventoryId, String inventoryCode, String name, String brand, String model, String serialNr, String roomNr, LocalDate buyDate, LocalDate disposalDate, BigDecimal price, String status, String comments, String category) {
    return false;
  }

  @Override
  public Reservation[] getAllReservations() {
    return new Reservation[0];
  }

  @Override
  public void deleteReservation(int reservationId) {

  }

  @Override
  public boolean updateReservation(String invId, int reservationId, LocalDate startDate, LocalDate endDate) {
    return false;
  }

  @Override
  public Reservation[] getReservationConflicts(String invId, LocalDate startDate, LocalDate endDate) {
    return new Reservation[0];
  }

  @Override
  public void addReservation(String username, String invId, String invCode, String brand, String model, LocalDate startDate, LocalDate endDate, String status) {

  }

  @Override
  public Reservation[] searchReservationsByInvId(String invId) {
    return new Reservation[0];
  }

  @Override
  public Reservation[] searchReservationsByStatus(String status) {
    return new Reservation[0];
  }

  @Override
  public Reservation[] searchReservationsByName(String name) {
    return new Reservation[0];
  }
}
