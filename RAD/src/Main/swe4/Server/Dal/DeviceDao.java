package swe4.Server.Dal;

import swe4.entities.Device;
import swe4.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

public class DeviceDao implements IDeviceDao{
  private Connection connection;
  private String     connectionString;
  private String     userName;
  private String     password;

  public DeviceDao(String connectionString) {
    this(connectionString, null, null);
  }

  public DeviceDao(String connectionString, String userName, String password){
    this.connectionString = connectionString;
    this.userName = userName;
    this.password = password;
  }

  public Connection getConnection() throws DataAccessException {
    try {
      if (connection == null)
        connection = DriverManager.getConnection(connectionString, userName, password);
      return connection;
    }
    catch (SQLException ex) {
      throw new DataAccessException("Can't establish connection to database. SQLException: "
              + ex.getMessage());
    }// try/catch
  } // getConnection

  @Override
  public Collection<String> getCategories() {
    Collection<String> c = new ArrayList<>();
    try (PreparedStatement statement = getConnection().prepareStatement("SELECT category_name FROM categories")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          c.add(resultSet.getString("category_name"));
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close()
    return c;
  }

  @Override
  public Collection<Device> getAllForAdmin() {
    Collection<Device> c = new ArrayList<>();

    try (PreparedStatement statement = getConnection().prepareStatement(
            "SELECT  device_id,          inventory_id,   inventory_code," +
                        "name,               brand,          model," +
                        "serial_nr,          room_nr,        buy_date," +
                        "log_date,           disposal_date,  price," +
                        "device_status_name, comments,       category_name  " +
                        "FROM devices" +
                "JOIN device_status on devices.device_status_id = device_status.device_status_id" +
                "JOIN categories on devices.category_id = categories.category_id" +
                "ORDER BY device_id;")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          Device d = new Device(
                  resultSet.getInt("device_id"),
                  resultSet.getString("inventory_id"),
                  resultSet.getString("inventory_code"),
                  resultSet.getString("name"),
                  resultSet.getString("brand"),
                  resultSet.getString("model"),
                  resultSet.getString("serial_nr"),
                  resultSet.getString("room_nr"),
                  resultSet.getDate("buy_date").toLocalDate(),
                  resultSet.getDate("log_date").toLocalDate(),
                  resultSet.getBigDecimal("price"),
                  resultSet.getString("device_status_name"),
                  resultSet.getString("comments"),
                  resultSet.getString("category_name")
          );
          Date disposalDate = resultSet.getDate("disposal_date");
          if(disposalDate != null)
            d.setDisposalDate(disposalDate.toLocalDate());
          c.add(d);
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close()

    return c;
  }

  @Override
  public Collection<Device> getAllForUser() {
    Collection<Device> c = new ArrayList<>();

    try (PreparedStatement statement = getConnection().prepareStatement(
            "SELECT  device_id,          inventory_id,   inventory_code," +
                        "name,               brand,          model," +
                        "serial_nr,          room_nr,        buy_date," +
                        "log_date,           disposal_date,  price," +
                        "device_status_name, comments,       category_name  " +
                "FROM devices" +
                "JOIN device_status on devices.device_status_id = device_status.device_status_id" +
                "JOIN categories on devices.category_id = categories.category_id" +
                "WHERE devices.device_status_id != 4" +
                "ORDER BY device_id;")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          Device d = new Device(
                  resultSet.getInt("device_id"),
                  resultSet.getString("inventory_id"),
                  resultSet.getString("inventory_code"),
                  resultSet.getString("name"),
                  resultSet.getString("brand"),
                  resultSet.getString("model"),
                  resultSet.getString("serial_nr"),
                  resultSet.getString("room_nr"),
                  resultSet.getDate("buy_date").toLocalDate(),
                  resultSet.getDate("log_date").toLocalDate(),
                  resultSet.getBigDecimal("price"),
                  resultSet.getString("device_status_name"),
                  resultSet.getString("comments"),
                  resultSet.getString("category_name")
          );
          Date disposalDate = resultSet.getDate("disposal_date");
          if(disposalDate != null)
            d.setDisposalDate(disposalDate.toLocalDate());
          c.add(d);
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
    return c;
  }

  @Override
  public Collection<Device> getByInventoryId(String invId) {
    Collection<Device> c = new ArrayList<>();
    try (PreparedStatement statement = getConnection().prepareStatement(
            "SELECT  device_id,          inventory_id,   inventory_code," +
                    "name,               brand,          model," +
                    "serial_nr,          room_nr,        buy_date," +
                    "log_date,           disposal_date,  price," +
                    "device_status_name, comments,       category_name  " +
                    "FROM devices" +
                    "JOIN device_status on devices.device_status_id = device_status.device_status_id" +
                    "JOIN categories on devices.category_id = categories.category_id" +
                    "WHERE devices.device_status_id != 4" +
                    "AND inventory_id = '" + invId + "' " +
                    "ORDER BY device_id;")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          Device d = new Device(
                  resultSet.getInt("device_id"),
                  resultSet.getString("inventory_id"),
                  resultSet.getString("inventory_code"),
                  resultSet.getString("name"),
                  resultSet.getString("brand"),
                  resultSet.getString("model"),
                  resultSet.getString("serial_nr"),
                  resultSet.getString("room_nr"),
                  resultSet.getDate("buy_date").toLocalDate(),
                  resultSet.getDate("log_date").toLocalDate(),
                  resultSet.getBigDecimal("price"),
                  resultSet.getString("device_status_name"),
                  resultSet.getString("comments"),
                  resultSet.getString("category_name")
          );
          Date disposalDate = resultSet.getDate("disposal_date");
          if(disposalDate != null)
            d.setDisposalDate(disposalDate.toLocalDate());
          c.add(d);
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
    return c;
  }

  @Override
  public Collection<Device> getByInventoryCode(String invId) {
    Collection<Device> c = new ArrayList<>();
    try (PreparedStatement statement = getConnection().prepareStatement(
            "SELECT  device_id,          inventory_id,   inventory_code," +
                    "name,               brand,          model," +
                    "serial_nr,          room_nr,        buy_date," +
                    "log_date,           disposal_date,  price," +
                    "device_status_name, comments,       category_name  " +
                    "FROM devices" +
                    "JOIN device_status on devices.device_status_id = device_status.device_status_id" +
                    "JOIN categories on devices.category_id = categories.category_id" +
                    "WHERE devices.device_status_id != 4" +
                    "AND inventory_code = '" + invId + "' " +
                    "ORDER BY device_id;")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          Device d = new Device(
                  resultSet.getInt("device_id"),
                  resultSet.getString("inventory_id"),
                  resultSet.getString("inventory_code"),
                  resultSet.getString("name"),
                  resultSet.getString("brand"),
                  resultSet.getString("model"),
                  resultSet.getString("serial_nr"),
                  resultSet.getString("room_nr"),
                  resultSet.getDate("buy_date").toLocalDate(),
                  resultSet.getDate("log_date").toLocalDate(),
                  resultSet.getBigDecimal("price"),
                  resultSet.getString("device_status_name"),
                  resultSet.getString("comments"),
                  resultSet.getString("category_name")
          );
          Date disposalDate = resultSet.getDate("disposal_date");
          if(disposalDate != null)
            d.setDisposalDate(disposalDate.toLocalDate());
          c.add(d);
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
    return c;
  }

  @Override
  public Collection<Device> getByName(String name) {
    Collection<Device> c = new ArrayList<>();
    try (PreparedStatement statement = getConnection().prepareStatement(
            "SELECT  device_id,          inventory_id,   inventory_code," +
                    "name,               brand,          model," +
                    "serial_nr,          room_nr,        buy_date," +
                    "log_date,           disposal_date,  price," +
                    "device_status_name, comments,       category_name  " +
                    "FROM devices" +
                    "JOIN device_status on devices.device_status_id = device_status.device_status_id" +
                    "JOIN categories on devices.category_id = categories.category_id" +
                    "WHERE devices.device_status_id != 4" +
                    "AND name LIKE '" + name + "' " +
                    "ORDER BY device_id;")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          Device d = new Device(
                  resultSet.getInt("device_id"),
                  resultSet.getString("inventory_id"),
                  resultSet.getString("inventory_code"),
                  resultSet.getString("name"),
                  resultSet.getString("brand"),
                  resultSet.getString("model"),
                  resultSet.getString("serial_nr"),
                  resultSet.getString("room_nr"),
                  resultSet.getDate("buy_date").toLocalDate(),
                  resultSet.getDate("log_date").toLocalDate(),
                  resultSet.getBigDecimal("price"),
                  resultSet.getString("device_status_name"),
                  resultSet.getString("comments"),
                  resultSet.getString("category_name")
          );
          Date disposalDate = resultSet.getDate("disposal_date");
          if(disposalDate != null)
            d.setDisposalDate(disposalDate.toLocalDate());
          c.add(d);
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
    return c;
  }

  @Override
  public Collection<Device> getByBrand(String brand) {
    Collection<Device> c = new ArrayList<>();
    try (PreparedStatement statement = getConnection().prepareStatement(
            "SELECT  device_id,          inventory_id,   inventory_code," +
                    "name,               brand,          model," +
                    "serial_nr,          room_nr,        buy_date," +
                    "log_date,           disposal_date,  price," +
                    "device_status_name, comments,       category_name  " +
                    "FROM devices" +
                    "JOIN device_status on devices.device_status_id = device_status.device_status_id" +
                    "JOIN categories on devices.category_id = categories.category_id" +
                    "WHERE devices.device_status_id != 4" +
                    "AND brand LIKE '" + brand + "' " +
                    "ORDER BY device_id;")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          Device d = new Device(
                  resultSet.getInt("device_id"),
                  resultSet.getString("inventory_id"),
                  resultSet.getString("inventory_code"),
                  resultSet.getString("name"),
                  resultSet.getString("brand"),
                  resultSet.getString("model"),
                  resultSet.getString("serial_nr"),
                  resultSet.getString("room_nr"),
                  resultSet.getDate("buy_date").toLocalDate(),
                  resultSet.getDate("log_date").toLocalDate(),
                  resultSet.getBigDecimal("price"),
                  resultSet.getString("device_status_name"),
                  resultSet.getString("comments"),
                  resultSet.getString("category_name")
          );
          Date disposalDate = resultSet.getDate("disposal_date");
          if(disposalDate != null)
            d.setDisposalDate(disposalDate.toLocalDate());
          c.add(d);
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
    return c;
  }

  @Override
  public Collection<Device> getByModel(String model) {
    Collection<Device> c = new ArrayList<>();
    try (PreparedStatement statement = getConnection().prepareStatement(
            "SELECT  device_id,          inventory_id,   inventory_code," +
                    "name,               brand,          model," +
                    "serial_nr,          room_nr,        buy_date," +
                    "log_date,           disposal_date,  price," +
                    "device_status_name, comments,       category_name  " +
                    "FROM devices" +
                    "JOIN device_status on devices.device_status_id = device_status.device_status_id" +
                    "JOIN categories on devices.category_id = categories.category_id" +
                    "WHERE devices.device_status_id != 4" +
                    "AND name LIKE '" + model + "' " +
                    "ORDER BY device_id;")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          Device d = new Device(
                  resultSet.getInt("device_id"),
                  resultSet.getString("inventory_id"),
                  resultSet.getString("inventory_code"),
                  resultSet.getString("name"),
                  resultSet.getString("brand"),
                  resultSet.getString("model"),
                  resultSet.getString("serial_nr"),
                  resultSet.getString("room_nr"),
                  resultSet.getDate("buy_date").toLocalDate(),
                  resultSet.getDate("log_date").toLocalDate(),
                  resultSet.getBigDecimal("price"),
                  resultSet.getString("device_status_name"),
                  resultSet.getString("comments"),
                  resultSet.getString("category_name")
          );
          Date disposalDate = resultSet.getDate("disposal_date");
          if(disposalDate != null)
            d.setDisposalDate(disposalDate.toLocalDate());
          c.add(d);
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
    return c;
  }

  @Override
  public Collection<Device> getByCategory(String category) {
    Collection<Device> c = new ArrayList<>();
    try (PreparedStatement statement = getConnection().prepareStatement(
            "SELECT  device_id,          inventory_id,   inventory_code," +
                    "name,               brand,          model," +
                    "serial_nr,          room_nr,        buy_date," +
                    "log_date,           disposal_date,  price," +
                    "device_status_name, comments,       category_name  " +
                    "FROM devices" +
                    "JOIN device_status on devices.device_status_id = device_status.device_status_id" +
                    "JOIN categories on devices.category_id = categories.category_id" +
                    "WHERE devices.device_status_id != 4" +
                    "AND category_name LIKE '" + category + "' " +
                    "ORDER BY device_id;")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          Device d = new Device(
                  resultSet.getInt("device_id"),
                  resultSet.getString("inventory_id"),
                  resultSet.getString("inventory_code"),
                  resultSet.getString("name"),
                  resultSet.getString("brand"),
                  resultSet.getString("model"),
                  resultSet.getString("serial_nr"),
                  resultSet.getString("room_nr"),
                  resultSet.getDate("buy_date").toLocalDate(),
                  resultSet.getDate("log_date").toLocalDate(),
                  resultSet.getBigDecimal("price"),
                  resultSet.getString("device_status_name"),
                  resultSet.getString("comments"),
                  resultSet.getString("category_name")
          );
          Date disposalDate = resultSet.getDate("disposal_date");
          if(disposalDate != null)
            d.setDisposalDate(disposalDate.toLocalDate());
          c.add(d);
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
    return c;
  }



  @Override
  public void add(Device device) {
    int categoryId = getCategoryId(device.getCategory());
    try (PreparedStatement statement =
                 getConnection().prepareStatement("INSERT INTO devices (" +
                         "inventory_id,       inventory_code,       name, " +
                         "brand,              serial_nr,            room_nr,              " +
                         "buy_date,           log_date,             price,    " +
                         "device_status_id,     comments,           category_id)" +
                         "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
      statement.setString(1, device.getInventoryId());
      statement.setString(2, device.getInventoryCode());
      statement.setString(3, device.getName());
      statement.setString(4, device.getBrand());
      statement.setString(5, device.getModel());
      statement.setString(6, device.getSerialNr());
      statement.setString(7, device.getRoomNr());
      statement.setDate(8, Date.valueOf(device.getBuyDate()));
      statement.setDate(9, Date.valueOf(device.getLogDate()));
      statement.setBigDecimal(10, device.getPrice());
      statement.setInt(11, 1);
      statement.setString(12, device.getComments());
      statement.setInt(13, categoryId);
      statement.executeUpdate();
    }
    catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
  }

  @Override
  public void update(String invIdBeforeUpdate, Device device) {

    StringBuilder sqlString = new StringBuilder();
    sqlString.append( "UPDATE devices" +
            "SET " +
            "inventory_id = ? " +
            "inventory_code = ? " +
            "name = ? " +
            "brand = ? " +
            "model = ? " +
            "serial_nr = ? " +
            "room_nr = ? " +
            "buy_date = ? " +
            "log_date = ? " +
            "price = ?" +
            "device_status_id = ? " +
            "comments = ? " +
            "category_id = ?" +
            "WHERE inventory_id = " + invIdBeforeUpdate + ";");
    boolean setDisposalDate = device.getDisposalDate() != null;
    if(setDisposalDate){
      sqlString.append(" disposal_date = ? ");
    }

    try (PreparedStatement statement = getConnection().prepareStatement(sqlString.toString())) {
      statement.setString(1, device.getInventoryId());
      statement.setString(2, device.getInventoryCode());
      statement.setString(3, device.getName());
      statement.setString(4, device.getBrand());
      statement.setString(5, device.getModel());
      statement.setString(6, device.getSerialNr());
      statement.setString(7, device.getRoomNr());
      statement.setDate(8, Date.valueOf(device.getBuyDate()));
      statement.setDate(9, Date.valueOf(device.getLogDate()));
      statement.setBigDecimal(10, device.getPrice());
      statement.setInt(11, getDeviceStatusId(device.getStatus()));
      statement.setString(12, device.getComments());
      statement.setInt(13, getCategoryId(device.getCategory()));
      if(setDisposalDate) {
        statement.setString(14, device.getModel());
      }
      statement.executeUpdate();
    }
    catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
  }

  @Override
  public void delete(String invId) {
    try (PreparedStatement statement =
                 getConnection().prepareStatement("DELETE FROM devices WHERE inventory_id = ?")) {
      statement.setString(1, invId);
      statement.executeUpdate();
    }
    catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
  }

  private int getCategoryId(String category) {
    try (PreparedStatement statement = getConnection().prepareStatement("SELECT category_id FROM categories WHERE category_name = ?")) {
      statement.setString(1, category);

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getInt("category_id");
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close()
    return 0;
  }

  private int getDeviceStatusId(String status) {
    try (PreparedStatement statement = getConnection().prepareStatement("SELECT device_status_id FROM device_status WHERE device_status_name = ?")) {
      statement.setString(1, status);

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getInt("category_id");
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close()
    return 0;
  }

  @Override
  public void close() throws Exception {
    try {
      if (connection != null) connection.close();
      connection = null;
    }
    catch (SQLException ex) {
      throw new DataAccessException("Problems closing database connection: SQLException: " + ex.getMessage());
    } // catch
  }
}
