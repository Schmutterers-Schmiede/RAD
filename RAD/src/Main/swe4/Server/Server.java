package swe4.Server;

import swe4.Client.interfaces.IRepository;
import swe4.Client.interfaces.IServer;
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

public class Server implements IServer {
  private final User[] users;

  public Server(){
    users = new User[]{ new User("name1", "username1", "pw1", "Student"),
                        new User("name2", "username2", "pw2", "Student"),
                        new User("name3", "username3", "pw3", "Student")};
  }

  @Override
  public User[] getAllUsers() throws RemoteException{
    return users;
  }

  @Override
  public User getUserByUsername(String username) throws RemoteException{
    return null;
  }

  @Override
  public boolean addUser(String name, String username, String password, String type) throws RemoteException {
    return false;
  }

  @Override
  public boolean updateUser(String usernameBeforeUpdate, String name, String username, String password, String type) throws RemoteException {
    return false;
  }

  @Override
  public void deleteUser(String username) throws RemoteException {

  }

  @Override
  public boolean authenticateUser(String username, String password) throws RemoteException {
    return false;
  }

  @Override
  public Device[] getAllDevicesAdmin() throws RemoteException {
    return null;
  }

  @Override
  public Device[] searchDevicesByInventoryId(String invNr) throws RemoteException {
    return null;
  }

  @Override
  public Device[] searchDevicesByName(String name) throws RemoteException {
    return null;
  }

  @Override
  public Device[] searchDevicesByBrand(String brand) throws RemoteException {
    return null;
  }

  @Override
  public Device[] searchDevicesByModel(String model) throws RemoteException {
    return null;
  }

  @Override
  public Device[] searchDevicesByCategory(String category) throws RemoteException {
    return null;
  }

  @Override
  public Device[] getAllDevicesUser() throws RemoteException {
    return null;
  }

  @Override
  public boolean addDevice(String inventoryId, String inventoryCode, String name, String brand, String model, String serialNr, String roomNr, LocalDate buyDate, LocalDate logDate, BigDecimal price, String status, String comments, String category) throws RemoteException {
    return false;
  }

  @Override
  public void deleteDevice(String inventoryId) throws RemoteException {

  }

  @Override
  public String[] getDeviceCategories() throws RemoteException {
    return null;
  }

  @Override
  public boolean updateDevice(String inventoryIdBeforeUpdate, String inventoryCodeBeforeUpdate, String inventoryId, String inventoryCode, String name, String brand, String model, String serialNr, String roomNr, LocalDate buyDate, LocalDate disposalDate, BigDecimal price, String status, String comments, String category) throws RemoteException {
    return false;
  }

  @Override
  public Reservation[] getAllReservations() throws RemoteException {
    return null;
  }

  @Override
  public void deleteReservation(int reservationId) throws RemoteException {

  }

  @Override
  public boolean updateReservation(int reservationId, LocalDate startDate, LocalDate endDate) throws RemoteException {
    return false;
  }

  @Override
  public Reservation[] getReservationConflicts(String invId, LocalDate startDate, LocalDate endDate) throws RemoteException {
    return null;
  }

  @Override
  public boolean reservationsOverlap(String invId, LocalDate startDate, LocalDate endDate) throws RemoteException {
    return false;
  }

  @Override
  public void addReservation(String username, String invId, String invCode, String brand, String model, LocalDate startDate, LocalDate endDate, String status) throws RemoteException {

  }

  @Override
  public Reservation[] searchReservationsByInvId(String invId) throws RemoteException {
    return null;
  }

  @Override
  public Reservation[] searchReservationsByStatus(String status) throws RemoteException {
    return null;
  }

  @Override
  public Reservation[] searchReservationsByName(String name) throws RemoteException {
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

    String internalUrl = "rmi://localhost:%d/Server".formatted(registryPort); //address of registry
    String externalUrl = "rmi://%s:%d/Server".formatted(serverHostName,registryPort);
    System.setProperty("java.rmi.server.hostname", serverHostName);

    IServer server = new Server();
    Remote serviceStub = UnicastRemoteObject.exportObject(server, registryPort);

    LocateRegistry.createRegistry(registryPort);
    Naming.rebind(internalUrl, serviceStub);

    System.out.println("server availabe at %s".formatted(externalUrl));


  }
}
