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
import java.util.List;

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
        serverProxy.updateUser(usernameBeforeUpdate, new User(username, name, password, role));
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
  public List<Reservation> getReservationConflicts(String invId, LocalDate startDate, LocalDate endDate) {
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
}
