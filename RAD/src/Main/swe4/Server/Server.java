package swe4.Server;

import swe4.Server.Dal.UserDao;
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
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class Server implements IServer {
  private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/RAD_db?autoReconnect=true&useSSL=false";
  private static final String USER_NAME = "root";
  private static final String PASSWORD = null;

  @Override
  public User[] getAllUsers() throws RemoteException{
    Collection<User> users = new ArrayList<>();
    try(UserDao userDao = new UserDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      users = userDao.getAll();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return users.toArray(new User[0]);
  }

  @Override
  public User getUserByUsername(String username) throws RemoteException{
    try(UserDao userDao = new UserDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      return userDao.getByUsername(username); // found
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null; //not found
  }

  @Override
  public void addUser(User user) throws RemoteException {
    
    try(UserDao userDao = new UserDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      userDao.add(user); // found
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public void updateUser(String usernameBeforeUpdate, User user) throws RemoteException {
    try(UserDao userDao = new UserDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      userDao.update(usernameBeforeUpdate, user); // found
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void deleteUser(String username) throws RemoteException {
    try(UserDao userDao = new UserDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      userDao.delete(username); // found
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean authenticateUser(String username, String password) throws RemoteException {
    try(UserDao userDao = new UserDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      return userDao.authenticate(username, password);
    }catch (Exception e) {
      e.printStackTrace();
    }
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
  public void addDevice(Device device) throws RemoteException {

  }

  @Override
  public void deleteDevice(String inventoryId) throws RemoteException {

  }

  @Override
  public String[] getDeviceCategories() throws RemoteException {
    return null;
  }

  @Override
  public void updateDevice(String inventoryIdBeforeUpdate, String inventoryCodeBeforeUpdate, Device device) throws RemoteException {

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
