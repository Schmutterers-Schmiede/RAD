package swe4.Client.interfaces;

import swe4.entities.Device;
import swe4.entities.Reservation;
import swe4.entities.User;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.time.LocalDate;
import java.util.List;

public interface Repository extends Remote {

  // USERS ==========================================================================
  User[] getAllUsers();

  User getUserByUsername(String username);

  boolean addUser(String name, String username, String password, String type);

  boolean updateUser(String usernameBeforeUpdate, String name, String username, String password, String type);

  void deleteUser(String username);

  boolean authenticateUser(String username, String password);

  // DEVICES =========================================================================
  Device[] getAllDevicesAdmin();

  Device[] searchDevicesByInventoryId(String invNr);
  Device[] searchDevicesByName(String name);
  Device[] searchDevicesByBrand(String brand);
  Device[] searchDevicesByModel(String model);
  Device[] searchDevicesByCategory(String category);


  Device[] getAllDevicesUser();

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
                    String status,
                    String comments,
                    String category);

  void deleteDevice(String inventoryId);

  String[] getDeviceCategories();

  boolean updateDevice(String inventoryIdBeforeUpdate,
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

  boolean updateReservation(int reservationId, LocalDate startDate, LocalDate endDate);

  Reservation[] getReservationConflicts(String invId, LocalDate startDate, LocalDate endDate);

  boolean reservationsOverlap(String invId, LocalDate startDate, LocalDate endDate);

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

