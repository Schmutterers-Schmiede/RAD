package swe4.Server;

import swe4.Server.Dal.DeviceDao;
import swe4.Server.Dal.ReservationDao;
import swe4.Server.Dal.UserDao;
import swe4.entities.Device;
import swe4.entities.Reservation;
import swe4.entities.User;

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
    User[] result = new User[users.size()];
    int i = 0;
    for(var user : users){
      result[i] = user;
      i++;
    }
    return result;
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
  public Device[] getAllDevices(boolean isForUser) throws RemoteException {
    Collection<Device> devices = new ArrayList<>();
    try(DeviceDao deviceDao = new DeviceDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      devices = deviceDao.getAll(isForUser);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return devices.toArray(new Device[0]);
  }

  @Override
  public Device[] searchDevicesByInventoryId(String invId, boolean isForUser) throws RemoteException {
    Collection<Device> devices = new ArrayList<>();
    try(DeviceDao deviceDao = new DeviceDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      devices = deviceDao.getByInventoryId(invId, isForUser);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return devices.toArray(new Device[0]);
  }

  @Override
  public Device[] searchDevicesByName(String name, boolean isForUser) throws RemoteException {
    Collection<Device> devices = new ArrayList<>();
    try(DeviceDao deviceDao = new DeviceDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      devices = deviceDao.getByName(name, isForUser);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return devices.toArray(new Device[0]);
  }

  @Override
  public Device[] searchDevicesByBrand(String brand, boolean isForUser) throws RemoteException {
    Collection<Device> devices = new ArrayList<>();
    try(DeviceDao deviceDao = new DeviceDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      devices = deviceDao.getByBrand(brand, isForUser);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return devices.toArray(new Device[0]);
  }

  @Override
  public Device[] searchDevicesByModel(String model, boolean isForUser) throws RemoteException {
    Collection<Device> devices = new ArrayList<>();
    try(DeviceDao deviceDao = new DeviceDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      devices = deviceDao.getByModel(model, isForUser);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return devices.toArray(new Device[0]);
  }

  @Override
  public Device[] searchDevicesByCategory(String category, boolean isForUser) throws RemoteException {
    Collection<Device> devices = new ArrayList<>();
    try(DeviceDao deviceDao = new DeviceDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      devices = deviceDao.getByCategory(category, isForUser);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return devices.toArray(new Device[0]);
  }

  @Override
  public Device[] searchDevicesByInventoryCode(String invCode, boolean isForUser) throws RemoteException {
    Collection<Device> devices = new ArrayList<>();
    try(DeviceDao deviceDao = new DeviceDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      devices = deviceDao.getByInventoryCode(invCode, isForUser);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return devices.toArray(new Device[0]);
  }

  @Override
  public void addDevice(Device device) throws RemoteException {
    try(DeviceDao deviceDao = new DeviceDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      deviceDao.add(device);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void deleteDevice(String inventoryId) throws RemoteException {
    try(DeviceDao deviceDao = new DeviceDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      deviceDao.delete(inventoryId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public String[] getDeviceCategories() throws RemoteException {
    Collection<String> categories = new ArrayList<>();
    try(DeviceDao deviceDao = new DeviceDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      categories = deviceDao.getCategories();
    } catch (Exception e) {
      e.printStackTrace();
    }
    String[] result = new String[categories.size()];
    int i = 0;
    for(var cat : categories){
      result[i] = cat;
      i++;
    }
    return result;
  }

  @Override
  public void updateDevice(String inventoryIdBeforeUpdate, Device device) throws RemoteException {
    try(DeviceDao deviceDao = new DeviceDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      deviceDao.update(inventoryIdBeforeUpdate, device);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public Reservation[] getAllReservations() throws RemoteException {
    Collection<Reservation> reservations = new ArrayList<>();
    try(ReservationDao reservationDao = new ReservationDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      reservations = reservationDao.getAll();
    } catch (Exception e) {
      e.printStackTrace();
    }
    Reservation[] result = new Reservation[reservations.size()];
    int i = 0;
    for(var res : reservations){
      result[i] = res;
      i++;
    }
    return result;
  }

  @Override
  public void deleteReservation(int reservationId) throws RemoteException {
    try(ReservationDao reservationDao = new ReservationDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      reservationDao.delete(reservationId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateReservation(int reservationId, LocalDate startDate, LocalDate endDate) throws RemoteException {
    try(ReservationDao reservationDao = new ReservationDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      reservationDao.update(reservationId, startDate, endDate);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public Reservation[] getReservationConflicts(String invId, LocalDate startDate, LocalDate endDate) throws RemoteException {
    Collection<Reservation> reservations = new ArrayList<>();
    try(ReservationDao reservationDao = new ReservationDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      reservations = reservationDao.getConflicts(invId, startDate, endDate);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Reservation[] result = new Reservation[reservations.size()];
    int i = 0;
    for(var res : reservations){
      result[i] = res;
      i++;
    }
    return result;
  }

  @Override
  public void addReservation(String username, String invId, LocalDate startDate, LocalDate endDate) throws RemoteException{
    try(ReservationDao reservationDao = new ReservationDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      reservationDao.add(username, invId, startDate, endDate);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public Reservation[] searchReservationsByInvId(String invId) throws RemoteException {
    Collection<Reservation> reservations = new ArrayList<>();
    try(ReservationDao reservationDao = new ReservationDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      reservations = reservationDao.getByInventoryId(invId);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Reservation[] result = new Reservation[reservations.size()];
    int i = 0;
    for(var res : reservations){
      result[i] = res;
      i++;
    }
    return result;
  }

  @Override
  public Reservation[] searchReservationsByStatus(String status) throws RemoteException {
    Collection<Reservation> reservations = new ArrayList<>();
    try(ReservationDao reservationDao = new ReservationDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      reservations = reservationDao.getByStatus(status);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Reservation[] result = new Reservation[reservations.size()];
    int i = 0;
    for(var res : reservations){
      result[i] = res;
      i++;
    }
    return result;
  }

  @Override
  public Reservation[] searchReservationsByName(String name) throws RemoteException {
    Collection<Reservation> reservations = new ArrayList<>();
    try(ReservationDao reservationDao = new ReservationDao(CONNECTION_STRING, USER_NAME, PASSWORD)){
      reservations = reservationDao.getByName(name);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Reservation[] result = new Reservation[reservations.size()];
    int i = 0;
    for(var res : reservations){
      result[i] = res;
      i++;
    }
    return result;
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
