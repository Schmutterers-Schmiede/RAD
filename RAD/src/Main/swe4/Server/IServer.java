package swe4.Server;

import swe4.entities.Device;
import swe4.entities.Reservation;
import swe4.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;


public interface IServer extends Remote {
  // USERS ==========================================================================
  User[] getAllUsers() throws RemoteException;

  User getUserByUsername(String username) throws RemoteException;

  void addUser(User user) throws RemoteException;

  void updateUser(String usernameBeforeUpdate, User user) throws RemoteException;

  void deleteUser(String username) throws RemoteException;

  boolean authenticateUser(String username, String password) throws RemoteException;

  // DEVICES =========================================================================
  Device[] getAllDevices(boolean isForUser) throws RemoteException;

  Device[] searchDevicesByInventoryId(String invNr, boolean isForUser) throws RemoteException;
  Device[] searchDevicesByName(String name, boolean isForUser) throws RemoteException;
  Device[] searchDevicesByBrand(String brand, boolean isForUser) throws RemoteException;
  Device[] searchDevicesByModel(String model, boolean isForUser) throws RemoteException;
  Device[] searchDevicesByCategory(String category, boolean isForUser) throws RemoteException;
  Device[] searchDevicesByInventoryCode(String invCode, boolean isForUser) throws RemoteException;

  void addDevice(Device device) throws RemoteException;

  void deleteDevice(String inventoryId) throws RemoteException;

  String[] getDeviceCategories() throws RemoteException;

  void updateDevice(String inventoryIdBeforeUpdate, Device device) throws RemoteException;


  // RESERVATIONS ========================================================================
  Reservation[] getAllReservations() throws RemoteException;

  void deleteReservation(int reservationId) throws RemoteException;

  void updateReservation(int reservationId, LocalDate startDate, LocalDate endDate) throws RemoteException;

  Reservation[] getReservationConflicts(String invId, LocalDate startDate, LocalDate endDate) throws RemoteException;

  void addReservation(String username, String invId, LocalDate startDate, LocalDate endDate) throws RemoteException;

  Reservation[] searchReservationsByInvId(String invId) throws RemoteException;
  Reservation[] searchReservationsByStatus(String status) throws RemoteException;
  Reservation[] searchReservationsByName(String name) throws RemoteException;
}
