package swe4.Client.interfaces;

import swe4.entities.Device;
import swe4.entities.Reservation;
import swe4.entities.User;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

public interface IServer extends Remote {
  // USERS ==========================================================================
  User[] getAllUsers() throws RemoteException;

  User getUserByUsername(String username) throws RemoteException;

  boolean addUser(String name, String username, String password, String type) throws RemoteException;

  boolean updateUser(String usernameBeforeUpdate, String name, String username, String password, String type) throws RemoteException;

  void deleteUser(String username) throws RemoteException;

  boolean authenticateUser(String username, String password) throws RemoteException;

  // DEVICES =========================================================================
  Device[] getAllDevicesAdmin() throws RemoteException;

  Device[] searchDevicesByInventoryId(String invNr) throws RemoteException;
  Device[] searchDevicesByName(String name) throws RemoteException;
  Device[] searchDevicesByBrand(String brand) throws RemoteException;
  Device[] searchDevicesByModel(String model) throws RemoteException;
  Device[] searchDevicesByCategory(String category) throws RemoteException;


  Device[] getAllDevicesUser() throws RemoteException;

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
                    String category) throws RemoteException;

  void deleteDevice(String inventoryId) throws RemoteException;

  String[] getDeviceCategories() throws RemoteException;

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
                       String category) throws RemoteException;


  // RESERVATIONS ========================================================================
  Reservation[] getAllReservations() throws RemoteException;

  void deleteReservation(int reservationId) throws RemoteException;

  boolean updateReservation(int reservationId, LocalDate startDate, LocalDate endDate) throws RemoteException;

  Reservation[] getReservationConflicts(String invId, LocalDate startDate, LocalDate endDate) throws RemoteException;

  boolean reservationsOverlap(String invId, LocalDate startDate, LocalDate endDate) throws RemoteException;

  void addReservation(String username,
                      String invId,
                      String invCode,
                      String brand,
                      String model,
                      LocalDate startDate,
                      LocalDate endDate,
                      String status) throws RemoteException;

  Reservation[] searchReservationsByInvId(String invId) throws RemoteException;
  Reservation[] searchReservationsByStatus(String status) throws RemoteException;
  Reservation[] searchReservationsByName(String name) throws RemoteException;
}
