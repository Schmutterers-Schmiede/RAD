import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import swe4.Server.Dal.*;
import swe4.entities.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;


public class DALUnitTester {
  private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/RAD_db?autoReconnect=true&useSSL=false";
  private static final String USER_NAME = "root";
  private static final String PASSWORD = null;
  private final UserDao userDao = new UserDao(CONNECTION_STRING, USER_NAME, PASSWORD);
  private final DeviceDao deviceDao = new DeviceDao(CONNECTION_STRING, USER_NAME, PASSWORD);
  private final ReservationDao reservationDao = new ReservationDao(CONNECTION_STRING, USER_NAME, PASSWORD);

  /*requirements:
    - running server
    - running docker container with craete and insert script executed
  */

  // USERS ===================================================================
  @Test
  public void getUserByUsername(){
    assert(userDao.getByUsername("wpudsey0") != null);
  }

  @Test
  public void getUserCount(){
    assert(userDao.getCount() == 50);
  }

  @Test
  public void getAllUsers(){
    Collection<User> users = new ArrayList<>(userDao.getAll());
    assert (users != null && users.size() == 50);
  }

  @Test
  public void insertUser(){
    userDao.add(new User("Max Mustermann", "mamuman3", "aoitnrs", "Student"));
    assert(userDao.getByUsername("mamuman3") != null);
    userDao.delete("mamuman3");
  }

  @Test
  public void deleteUser(){
    userDao.add(new User("Max Mustermann", "mamuman3", "aoitnrs", "Student"));
    userDao.delete("mamuman3");
    assert(userDao.getCount() == 50);
  }

  @Test
  public void authenticateExistingUser(){
    assert(userDao.authenticate("wpudsey0", "xG6(.c58a"));
  }

  @Test
  public void authenticateNonExistentUser(){
    assert(!userDao.authenticate("doesNotExist111", "password"));
  }

  @Test
  public void updateUser(){
    //only change name
    userDao.update("wpudsey0", new User("New Name", "wpudsey0", "xG6(.c58a", "Student"));
    User user = userDao.getByUsername("wpudsey0");
    assert(user.getName().equals("New Name"));
    userDao.update("wpudsey0", new User("Waiter Pudsey", "wpudsey0", "xG6(.c58a", "Student"));
  }

  // DEVICES ===================================================================
  @Test
  public void getDeviceCount(){
    assert(deviceDao.getCount() == 18);
  }

  @Test
  public void getCategories(){
    assert (deviceDao.getCategories().size() == 5);
  }

  @Test
  public void getAllDevicesForAdmin(){
    Collection<Device> devices = new ArrayList<>(deviceDao.getAll(false));
    assert (devices.size() == 18);
  }
  @Test
  public void getAllDevicesForUser(){
    Collection<Device> devices = new ArrayList<>(deviceDao.getAll(true));
    assert (devices.size() == 16);
  }
  @Test
  public void getDeviceByInventoryId(){
    assert (deviceDao.getByInventoryId("INV00001", false) != null);
  }
  @Test
  public void getDeviceByInventoryCode(){
    assert (deviceDao.getByInventoryCode("FHID00001", false) != null);
  }
  @Test
  public void getDevicesByName(){
    assert (deviceDao.getByName("Tablet", false).size() == 2);
  }
  @Test
  public void getDevicesByBrand(){
    assert (deviceDao.getByBrand("BrandA", false).size() == 3);
  }
  @Test
  public void getDevicesByModel(){
    assert (deviceDao.getByModel("ModelX", false).size() == 3);
  }
  @Test
  public void getDevicesByCategory(){
    assert(deviceDao.getByCategory("Messgeräte", false).size() == 5);
  }
  @Test
  public void AddDevice(){
    deviceDao.add(new Device(
            "INV00099",
            "FHID00099",
            "name",
            "brand",
            "model",
            "9999999999",
            "FH9.999",
            LocalDate.now(),
            LocalDate.now(),
            new BigDecimal("3.33"),
            "test comment",
            "Messgeräte"));
    assert (deviceDao.getCount() == 19);
    deviceDao.delete("INV00099");
  }
  @Test
  public void deleteDevice(){
    deviceDao.add(new Device(
            "INV00099",
            "FHID00099",
            "name",
            "brand",
            "model",
            "9999999999",
            "FH9.999",
            LocalDate.now(),
            LocalDate.now(),
            new BigDecimal("3.33"),
            "test comment",
            "Messgeräte"));
    deviceDao.delete("INV00099");
    assert (deviceDao.getCount() == 18);
  }

  @Test
  public void updateDevice(){
    deviceDao.update("INV00001", new Device(
            "INV00001",
            "FHID00001",
            "Oscilloscope",
            "BrandC",
            "ModelXYZ",
            "K1L2M3N4O5",
            "103",
            LocalDate.of(2020,1,2),
            LocalDate.of(2020,1,2),
            new BigDecimal("345.67"),
            "verfügbar",
            "",
            "Messgeräte"
    ));
    Collection<Device> devices = deviceDao.getByInventoryId("INV00001", false);
    Device device = null;
    for(var dev : devices){
      device = dev;
      break;
    }
    assert (device != null && device.getModel().equals("ModelXYZ"));
    deviceDao.update("INV00001", new Device(
            "INV00001",
            "FHID00001",
            "Oscilloscope",
            "BrandC",
            "ModelZ",
            "K1L2M3N4O5",
            "103",
            LocalDate.of(2020,1,2),
            LocalDate.of(2020,1,2),
            new BigDecimal("345.67"),
            "verfügbar",
            "",
            "Messgeräte"
    ));
  }

  //RESERVATIONS ======================================================================
  @Test
  public void getAllReservations(){
    Collection<Reservation> reservations = reservationDao.getAll();
    assert(reservations.size() == 16);
  }
  @Test
  public void getReservationsByInventoryId(){
    assert (reservationDao.getByInventoryId("INV00001").size() == 1);
  }
  @Test
  public void getReservationsByName(){
    assert (reservationDao.getByName("Lil Laughtisse").size() == 2);
  }
  @Test
  public void getReservationsByStatus(){
    assert (reservationDao.getByStatus("laufend").size() == 6);
  }
  @Test
  public void getReservationConflicts(){
    assert(reservationDao.getConflicts("INV00003", LocalDate.of(2024,3,12), LocalDate.of(2024,3,15)).size() == 1);
  }
  @Test
  public void addAndDeleteReservation(){
    reservationDao.add("ahellikes1d", "INV00018", LocalDate.of(2025,1,1), LocalDate.of(2025,1,10));
    assert (reservationDao.getByName("Aristotle Hellikes").size() == 1);
    reservationDao.delete(17);
    assert (reservationDao.getAll().size() == 16);
  }
  @Test
  public void getReservationById(){
    Reservation res = reservationDao.getById(1);
    assert (
      res.getUsername().equals("llaughtisse17") &&
      res.getInvId().equals("INV00004") &&
      res.getStartDate().equals(LocalDate.of(2023,1,13)) &&
      res.getEndDate().equals(LocalDate.of(2023,1,19))
    );
  }
  @Test
  public void updateReservation(){
    LocalDate newStartDate = LocalDate.of(2025,1,2);
    LocalDate newEndDate = LocalDate.of(2025,1,11);
    reservationDao.update(1, newStartDate, newEndDate);
    Reservation res = reservationDao.getById(1);
    assert (res.getStartDate().equals(newStartDate) && res.getEndDate().equals(newEndDate));
    reservationDao.update(1, LocalDate.of(2023,1,13),LocalDate.of(2023,1,19));
  }
}
