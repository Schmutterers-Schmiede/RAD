package swe4.Client;

import swe4.Client.interfaces.IRepository;
import swe4.Server.IServer;
import swe4.entities.Device;
import swe4.entities.Reservation;
import swe4.entities.User;

import java.math.BigDecimal;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.time.LocalDate;


public class ServerRepository implements IRepository {
  private static ServerRepository instance;
  private IServer serverProxy;
  public static ServerRepository getInstance() {
    if (instance == null) instance = new ServerRepository();
    return instance;
  }

  private ServerRepository(){
    String hostAndPort = "localhost"; //default port is 1099
    String serviceUrl = "rmi://%s/Server".formatted(hostAndPort);
    System.out.println("looking up %s".formatted(hostAndPort));

    try{
      serverProxy = (IServer) Naming.lookup(serviceUrl);

    }catch (Exception e){
      System.out.println("exception in serverRepository constructor");
    }
  }

  @Override
  public User[] getAllUsers() {
    try{
      return serverProxy.getAllUsers();
    } catch (NullPointerException e){
      System.out.println("exception in getallUsers");
    } catch (RemoteException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public User getUserByUsername(String username) {
    try{
      return serverProxy.getUserByUsername(username);//found
    }catch (RemoteException e){e.printStackTrace();}
    return null; //not found
  }

  @Override
  public boolean addUser(String name, String username, String password, String role) {
    try{
      if(serverProxy.getUserByUsername(username) == null){
        serverProxy.addUser(new User(name, username, password, role));
        return true;
      }
      else return false;
    }catch (RemoteException e){e.printStackTrace();}
    return false;
  }

  @Override
  public boolean updateUser(String usernameBeforeUpdate, String name, String username, String password, String role) {
    try{
      if(!username.equals(usernameBeforeUpdate) && serverProxy.getUserByUsername(username) != null){
        return false;
      }else {
        serverProxy.updateUser(usernameBeforeUpdate, new User(name, username, password, role));
        return true;
      }
    }catch (RemoteException e){e.printStackTrace();}
    return false;
  }

  @Override
  public void deleteUser(String username) {
    try{
      serverProxy.deleteUser(username);
    }catch (RemoteException e){e.printStackTrace();}
  }

  @Override
  public boolean authenticateUser(String username, String password) {
    try{
      return serverProxy.authenticateUser(username, password);
    }catch (RemoteException e){e.printStackTrace();}
    return false;
  }

  @Override
  public Device[] getAllDevices(boolean isForUser) {
    try{
      return serverProxy.getAllDevices(isForUser);
    }catch (RemoteException e){e.printStackTrace();}
    return new Device[0];
  }

  @Override
  public Device[] searchDevicesByInventoryId(String invId, boolean isForUser) {
    try{
      return serverProxy.searchDevicesByInventoryId(invId, isForUser);
    }catch (RemoteException e){e.printStackTrace();}
    return new Device[0];
  }

  @Override
  public Device[] searchDevicesByName(String name, boolean isForUser) {
    try{
      return serverProxy.searchDevicesByName(name, isForUser);
    }catch (RemoteException e){e.printStackTrace();}
    return new Device[0];
  }

  @Override
  public Device[] searchDevicesByBrand(String brand, boolean isForUser) {
    try{
      return serverProxy.searchDevicesByBrand(brand, isForUser);
    }catch (RemoteException e){e.printStackTrace();}
    return new Device[0];
  }

  @Override
  public Device[] searchDevicesByModel(String model, boolean isForUser) {
    try{
      return serverProxy.searchDevicesByModel(model, isForUser);
    }catch (RemoteException e){e.printStackTrace();}
    return new Device[0];
  }

  @Override
  public Device[] searchDevicesByCategory(String category, boolean isForUser) {
    try{
      return serverProxy.searchDevicesByCategory(category, isForUser);
    }catch (RemoteException e){e.printStackTrace();}
    return new Device[0];
  }

  @Override
  public boolean addDevice(String inventoryId, String inventoryCode, String name, String brand, String model, String serialNr, String roomNr, LocalDate buyDate, LocalDate logDate, BigDecimal price, String comments, String category) {
    try{
      if(serverProxy.searchDevicesByInventoryId(inventoryId, false).length == 0 &&
         serverProxy.searchDevicesByInventoryCode(inventoryCode, false).length == 0) {
        serverProxy.addDevice(new Device(inventoryId, inventoryCode, name, brand, model, serialNr, roomNr, buyDate, logDate, price, comments, category));
        return true;
      }
    }catch (RemoteException e){e.printStackTrace();}
    return false;
  }

  @Override
  public void deleteDevice(String inventoryId) {
    try{
      serverProxy.deleteDevice(inventoryId);
    }catch (RemoteException e){e.printStackTrace();}
  }

  @Override
  public String[] getDeviceCategories() {
    try{
      return serverProxy.getDeviceCategories();
    }catch (RemoteException e){e.printStackTrace();}
    return new String[0];
  }

  @Override
  public boolean updateDevice(int deviceId, String inventoryIdBeforeUpdate, String inventoryCodeBeforeUpdate, String inventoryId, String inventoryCode, String name, String brand, String model, String serialNr, String roomNr, LocalDate buyDate, LocalDate disposalDate, BigDecimal price, String status, String comments, String category) {
    try{
      if(!inventoryCodeBeforeUpdate.equals(inventoryCode) &&
          serverProxy.searchDevicesByInventoryId(inventoryIdBeforeUpdate, false).length != 0 ||
         !inventoryIdBeforeUpdate.equals(inventoryId) &&
          serverProxy.searchDevicesByInventoryCode(inventoryCodeBeforeUpdate, false).length != 0){
        return false;
      }

      serverProxy.updateDevice(inventoryIdBeforeUpdate, new Device(inventoryId, inventoryCode, name, brand, model, serialNr, roomNr, buyDate, LocalDate.now(), price, status, comments, category));
      return true;
    } catch (RemoteException e){e.printStackTrace();}
    return false;
  }

  @Override
  public Reservation[] getAllReservations() {
    try{
      return serverProxy.getAllReservations();
    }catch (RemoteException e){e.printStackTrace();}
    return new Reservation[0];
  }

  @Override
  public void deleteReservation(int reservationId) {
    try{
      serverProxy.deleteReservation(reservationId);
    }catch (RemoteException e){e.printStackTrace();}
  }

  @Override
  public boolean updateReservation(String invId, int reservationId, LocalDate startDate, LocalDate endDate) {
    try{
      if(serverProxy.getReservationConflicts(invId, startDate, endDate).length != 0){
        return false;
      }
      serverProxy.updateReservation(reservationId, startDate, endDate);
      return true;
    }catch (RemoteException e){e.printStackTrace();}
    return false;
  }

  @Override
  public Reservation[] getReservationConflicts(String invId, LocalDate startDate, LocalDate endDate) {
    try{
      return serverProxy.getReservationConflicts(invId, startDate, endDate);
    }catch (RemoteException e){e.printStackTrace();}
    return new Reservation[0];
  }

  @Override
  public void addReservation(String username, String invId, String invCode, String brand, String model, LocalDate startDate, LocalDate endDate, String status) {
    try{
      serverProxy.addReservation(username, invId, startDate, endDate);
    }catch (RemoteException e){e.printStackTrace();}
  }

  @Override
  public Reservation[] searchReservationsByInvId(String invId) {
    try{
      return serverProxy.searchReservationsByInvId(invId);
    }catch (RemoteException e){e.printStackTrace();}
    return new Reservation[0];
  }

  @Override
  public Reservation[] searchReservationsByStatus(String status) {
    try{
      return serverProxy.searchReservationsByStatus(status);
    }catch (RemoteException e){e.printStackTrace();}
    return new Reservation[0];
  }

  @Override
  public Reservation[] searchReservationsByName(String name) {
    try{
      return serverProxy.searchReservationsByName(name);
    }catch (RemoteException e){e.printStackTrace();}
    return new Reservation[0];
  }
}
