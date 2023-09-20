package swe4.Client;


import swe4.entities.Device;
import swe4.entities.Reservation;
import swe4.entities.User;
import swe4.Client.interfaces.Repository;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakeRepository implements Repository {
  private final List<User> users;
  private final List<Device> devices;
  private final List<String> deviceCategories;
  private final List<Reservation> reservations;
  private static FakeRepository instance;

  public static FakeRepository getInstance() {
    if (instance == null) instance = new FakeRepository();
    return instance;
  }

  //======== DUMMY DATA ========
  private FakeRepository() {
    users = new ArrayList<>();
    devices = new ArrayList<>();
    reservations = new ArrayList<>();
    deviceCategories = new ArrayList<>();

    users.add(new User("name1", "username1", "pw1", "Student"));
    users.add(new User("name2", "username2", "pw2", "Student"));
    users.add(new User("name3", "username3", "pw3", "Student"));

    devices.add(new Device("INV12345",
            "FHID12345",
            "Laptop",
            "hp",
            "hp zbook",
            "4895725245",
            "FH2.224",
            LocalDate.of(2020, 5, 14),
            LocalDate.of(2020, 5, 21),
            new BigDecimal("1599.99"),
            "verfügbar",
            "good stuff",
            "Computer"
    ));
    Device dev = new Device("INV98765",
            "FHID98765",
            "Osziloskop",
            "Gucci",
            "Gucci wave 9000",
            "9586739684",
            "FH2.333",
            LocalDate.of(2020, 5, 14),
            LocalDate.of(2020, 5, 21),
            new BigDecimal("9999.99"),
            "ausgegliedert",
            "ride the wave",
            "Messgeräte");
    dev.setDisposalDate(LocalDate.of(2020, 5, 22));
    devices.add(dev);

    devices.add(new Device("INV78456",
            "FHID78456",
            "Spülmittelanalysator",
            "Bernhard Trug GmbH",
            "Spülisator 9001",
            "9876549654",
            "FH4.000",
            LocalDate.of(2020, 5, 14),
            LocalDate.of(2020, 5, 21),
            new BigDecimal("2200"),
            "verfügbar",
            "hat uns ein Verrückter auf einem Nilpferd verkauft",
            "Messgeräte"
    ));

    devices.add(new Device("INV99999",
            "FHID99999",
            "Alkomat",
            "Air & Spirit",
            "Blow2Go",
            "987654321",
            "FH3.420",
            LocalDate.of(2020, 5, 14),
            LocalDate.of(2020, 5, 21),
            new BigDecimal("50"),
            "überfällig",
            "Die beliebteste Attraktion im FH Pub",
            "Messgeräte"
    ));

    deviceCategories.add("Laptops");
    deviceCategories.add("Messgeräte");

    reservations.add(new Reservation(
            1,
            "username1",
            "name1",
            "INV78456",
            "FHID78456",
            "Bernhard Trug GmbH",
            "Spülisator 9001",
            LocalDate.of(2023, 10, 31),
            LocalDate.of(2023, 11, 10),
            "reserviert"
    ));

    reservations.add(new Reservation(
            2,
            "username2",
            "name2",
            "INV78456",
            "FHID78456",
            "Bernhard Trug GmbH",
            "Spülisator 9001",
            LocalDate.of(2023, 12, 1),
            LocalDate.of(2023, 12, 10),
            "reserviert"
    ));

    reservations.add(new Reservation(
            3,
            "username3",
            "name3",
            "INV12345",
            "FHID12345",
            "hp",
            "hp zbook",
            LocalDate.of(2023, 10, 20),
            LocalDate.of(2023, 11, 10),
            "reserviert"
    ));

    reservations.add(new Reservation(
            4,
            "username3",
            "name3",
            "INV99999",
            "FHID99999",
            "Blow2Go",
            "Air & Spirit",
            LocalDate.of(2023, 9, 1),
            LocalDate.of(2023, 9, 2),
            "überfällig"
    ));

  }

  private int maxReservationId = 4;

  private int nextReservationId() {
    maxReservationId++;
    return maxReservationId;
  }

  //======== USERS ==========

  public List<User> getAllUsers() {
    return users;
  }

  @Override
  public User getUserByUsername(String username) {
    for(User user : users){
      if(user.getUsername().equals(username))
        return user;
    }
    return null;
  }

  @Override
  public boolean addUser(String name, String username, String password, String type) {
    User newUser = new User(name, username, password, type);
    if (users.stream().anyMatch(user -> user.getUsername().equals(newUser.getUsername())))
      return false;

    users.add(newUser);
    return true;
  }

  @Override
  public boolean updateUser(String usernameBeforeUpdate, String name, String username, String password, String type) {
    if (!username.equals(usernameBeforeUpdate) &&
            users.stream().anyMatch(user -> user.getUsername().equals(username)))
      return false;

    User userToUpdate = users.stream()
            .filter(user -> user.getUsername().equals(usernameBeforeUpdate))
            .findFirst()
            .orElse(null);
    if (userToUpdate != null) {
      userToUpdate.setName(name);
      userToUpdate.setUsername(username);
      userToUpdate.setPassword(password);
      userToUpdate.setType(type);
    }
    return true;
  }

  @Override
  public void deleteUser(String username) {
    User userToDelete = users.stream()
            .filter(user -> user.getUsername().equals(username))
            .findFirst().orElse(null);
    if (userToDelete != null)
      users.remove(userToDelete);
  }

  @Override
  public boolean authenticateUser(String username, String password) {
    for(User user : users){
      if(user.getUsername().equals(username) && user.getPassword().equals(password))
        return true;
    }
    return false;
  }

  //=========== DEVICES =============

  @Override
  public List<Device> getAllDevicesAdmin() {
    return devices;
  }

  @Override
  public List<Device> searchDevicesByInventoryId(String invId) {
    List<Device> results = new ArrayList<>();
    for(Device device : devices){
      if(device.getInventoryId().equals(invId))
        results.add(device);
    }
    return results;
  }

  @Override
  public List<Device> searchDevicesByName(String name) {
    List<Device> results = new ArrayList<>();
    for(Device device : devices){
      if(device.getName().equals(name))
        results.add(device);
    }
    return results;
  }

  @Override
  public List<Device> searchDevicesByBrand(String brand) {
    List<Device> results = new ArrayList<>();
    for(Device device : devices){
      if(device.getBrand().equals(brand))
        results.add(device);
    }
    return results;
  }

  @Override
  public List<Device> searchDevicesByModel(String model) {
    List<Device> results = new ArrayList<>();
    for(Device device : devices){
      if(device.getModel().equals(model))
        results.add(device);
    }
    return results;
  }

  @Override
  public List<Device> searchDevicesByCategory(String category) {
    List<Device> results = new ArrayList<>();
    for(Device device : devices){
      if(device.getCategory().equals(category))
        results.add(device);
    }
    return results;
  }


  @Override
  public List<Device> getAllDevicesUser() {
    List<Device> filteredDevices = new ArrayList<>();
    for (Device item : devices) {
      if (!item.getStatus().equals("ausgegliedert"))
        filteredDevices.add(item);
    }
    return filteredDevices;
  }

  @Override
  public boolean addDevice(String inventoryId,
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
                           String category) {
    if (devices.stream().anyMatch(device -> device.getInventoryId().equals(inventoryId))) {
      return false;
    } else {
      devices.add(new Device(
              inventoryId,
              inventoryCode,
              name,
              brand,
              model,
              serialNr,
              roomNr,
              buyDate,
              logDate,
              price,
              status,
              comments,
              category));
    }
    return true;
  }

  @Override
  public void deleteDevice(String inventoryId) {
    Device deviceToDelete = devices.stream()
            .filter(device -> device.getInventoryId().equals(inventoryId))
            .findFirst()
            .orElse(null);
    if (deviceToDelete != null)
      devices.remove(deviceToDelete);
  }


  @Override
  public List<String> getDeviceCategories() {
    return deviceCategories;
  }

  @Override
  public boolean updateDevice(String inventoryIdBeforeUpdate,
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
                              String category) {

    if (!inventoryId.equals(inventoryIdBeforeUpdate) &&
            devices.stream().anyMatch(device -> device.getInventoryId().equals(inventoryId)))
      return false;

    if (!inventoryCode.equals(inventoryCodeBeforeUpdate) &&
            devices.stream().anyMatch(device -> device.getInventoryId().equals(inventoryId)))
      return false;

    Device deviceToUpdate = devices
            .stream()
            .filter(device -> device.getInventoryId().equals(inventoryId))
            .findFirst()
            .orElse(null);

    if (deviceToUpdate != null) {
      deviceToUpdate.setInventoryId(inventoryId);
      deviceToUpdate.setInventoryCode(inventoryCode);
      deviceToUpdate.setName(name);
      deviceToUpdate.setBrand(brand);
      deviceToUpdate.setModel(model);
      deviceToUpdate.setSerialNr(serialNr);
      deviceToUpdate.setRoomNr(roomNr);
      deviceToUpdate.setBuyDate(buyDate);
      deviceToUpdate.setDisposalDate(disposalDate);
      deviceToUpdate.setPrice(price);
      deviceToUpdate.setStatus(status);
      deviceToUpdate.setComments(comments);
      deviceToUpdate.setCategory(category);
    }
    return true;
  }

  @Override
  public List<Reservation> getAllReservations() {
    return reservations;
  }

  @Override
  public void deleteReservation(int reservationId) {
    Reservation reservationToDelete = reservations
            .stream()
            .filter(reservation -> reservation.getReservationId() == reservationId)
            .findFirst()
            .orElse(null);
    if (reservationToDelete != null)
      reservations.remove(reservationToDelete);
  }

  @Override
  public boolean updateReservation(int reservationId, LocalDate startDate, LocalDate endDate) {
    if (reservations.stream().anyMatch(reservation ->
            DateChecker.timeSpanOverlap(reservation.getStartDate(), reservation.getEndDate(), startDate, endDate)))
      return false; //overlap with other reservation

    Reservation reservationToUpdate = reservations
            .stream()
            .filter(reservation -> reservation.getReservationId() == reservationId)
            .findFirst()
            .orElse(null);
    reservationToUpdate.setStartDate(startDate);
    reservationToUpdate.setEndDate(endDate);
    return true;
  }

  @Override
  public List<Reservation> getReservationConflicts(String invId, LocalDate startDate, LocalDate endDate) {
    List<Reservation> conflicts = new ArrayList<>();
    for (Reservation item : reservations) {
      if (DateChecker.timeSpanOverlap(
              item.getStartDate(),
              item.getEndDate(),
              startDate,
              endDate) &&
              item.getInvId().equals(invId))
        conflicts.add(item);
    }
    return conflicts;
  }

  @Override
  public boolean reservationsOverlap(String invId, LocalDate startDate, LocalDate endDate) {
    for (Reservation item : reservations) {
      if (DateChecker.timeSpanOverlap(item.getStartDate(), item.getEndDate(), startDate, endDate) && item.getInvId().equals(invId))
        return true;
    }
    return false;
  }

  @Override
  public void addReservation(
          String username,
          String invId,
          String invCode,
          String brand,
          String model,
          LocalDate startDate,
          LocalDate endDate,
          String status) {
    User user = getUserByUsername(username);
    reservations.add(new Reservation(
            nextReservationId(),
            username,
            user.getName(),
            invId,
            invCode,
            brand,
            model,
            startDate,
            endDate,
            status));
  }

  @Override
  public List<Reservation> searchReservationsByInvId(String invId) {
    List<Reservation> result = new ArrayList<>();
    for(Reservation reservation : reservations){
      if(reservation.getInvId().equals(invId))
        result.add(reservation);
    }
    return result;
  }

  @Override
  public List<Reservation> searchReservationsByStatus(String status) {
    List<Reservation> result = new ArrayList<>();
    for(Reservation reservation : reservations){
      if(reservation.getStatus().equals(status))
        result.add(reservation);
    }
    return result;
  }

  @Override
  public List<Reservation> searchReservationsByName(String name) {
    List<Reservation> result = new ArrayList<>();
    for(Reservation reservation : reservations){
      if(reservation.getRentedByName().equals(name))
        result.add(reservation);
    }
    return result;
  }


}
