package swe4.Server;

import swe4.Client.interfaces.Repository;
import swe4.entities.Device;
import swe4.entities.Reservation;
import swe4.entities.User;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Server implements Repository {
  private User[] users;
  private Device[] devices;
  private String[] deviceCategories;
  private Reservation[] reservations;
  @Override
  public User[] getAllUsers() {
    return users;
  }
  public void fetchAllUsers(){

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

  public static void main(String[] args) throws RemoteException, MalformedURLException {



    int registryPort      = Registry.REGISTRY_PORT;
    String serverHostName = "localhost";
    if (args.length > 0) {
      String[] hostAndPort = args[0].split(":");
      if (hostAndPort.length > 0) serverHostName = hostAndPort[0];
      if (hostAndPort.length > 1) registryPort = Integer.parseInt(hostAndPort[1]);
    }

    String internalUrl = "rmi://localhost:%d/PingService".formatted(registryPort); //address of registry
    String externalUrl = "rmi://%s:%d/PingService".formatted(serverHostName,registryPort);
    System.setProperty("java.rmi.server.hostname", serverHostName);

    Repository server = new Server();
    Remote serviceStub = UnicastRemoteObject.exportObject(server, registryPort);

    LocateRegistry.createRegistry(registryPort);
    Naming.rebind(internalUrl, serviceStub);

    System.out.println("server availabe at %s".formatted(externalUrl));


  }
}
