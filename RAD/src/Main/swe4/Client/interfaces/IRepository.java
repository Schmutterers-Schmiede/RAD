package swe4.Client.interfaces;

import swe4.entities.Device;
import swe4.entities.Reservation;
import swe4.entities.User;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.time.LocalDate;

public interface IRepository extends Remote {

  // USERS ==========================================================================
  User[] getAllUsers();

  User getUserByUsername(String username);

  boolean addUser(String name, String username, String password, String type);

  boolean updateUser(String usernameBeforeUpdate, String name, String username, String password, String type);

  void deleteUser(String username);

  boolean authenticateUser(String username, String password);

  // DEVICES =========================================================================
  Device[] getAllDevices(boolean isForUser);

  Device[] searchDevicesByInventoryId(String invNr, boolean isForUser);
  Device[] searchDevicesByName(String name, boolean isForUser);
  Device[] searchDevicesByBrand(String brand, boolean isForUser);
  Device[] searchDevicesByModel(String model, boolean isForUser);
  Device[] searchDevicesByCategory(String category, boolean isForUser);

  boolean addDevice(String inventoryId,
                    String inventoryCode,
                    String name,
                    String brand,
                    String model,
                    String serialNr,
                    String roomNr,
                    LocalDate buyDate,
                    LocalDate logDate,
                    BigDecimal price,
                    String comments,
                    String category);

  void deleteDevice(String inventoryId);

  String[] getDeviceCategories();

  boolean updateDevice(int deviceId,
                       String inventoryIdBeforeUpdate,
                       String inventoryCodeBeforeUpdate,
                       String inventoryId,
                       String inventoryCode,
                       String name,
                       String brand,
                       String model,
                       String serialNr,
                       String roomNr,
                       LocalDate buyDate,
                       LocalDate disposalDate,
                       BigDecimal price,
                       String status,
                       String comments,
                       String category);


  // RESERVATIONS ========================================================================
  Reservation[] getAllReservations();

  void deleteReservation(int reservationId);

  boolean updateReservation(String invId, int reservationId, LocalDate startDate, LocalDate endDate);

  Reservation[] getReservationConflicts(String invId, LocalDate startDate, LocalDate endDate);

  void addReservation(String username,
                      String invId,
                      String invCode,
                      String brand,
                      String model,
                      LocalDate startDate,
                      LocalDate endDate,
                      String status);

  Reservation[] searchReservationsByInvId(String invId);
  Reservation[] searchReservationsByStatus(String status);
  Reservation[] searchReservationsByName(String name);
}

