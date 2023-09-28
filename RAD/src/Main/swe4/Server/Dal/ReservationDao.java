package swe4.Server.Dal;

import swe4.entities.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class ReservationDao implements IReservationDao{
  private final String
          connectionString,
          username,
          password;
  private Connection connection;
  public ReservationDao(String connectionString, String username, String password){
    this.connectionString = connectionString;
    this.username = username;
    this.password = password;
  }

  public Connection getConnection() throws DataAccessException {
    try {
      if (connection == null)
        connection = DriverManager.getConnection(connectionString, username, password);
      return connection;
    }
    catch (SQLException ex) {
      throw new DataAccessException("Can't establish connection to database. SQLException: "
              + ex.getMessage());
    }// try/catch
  } // getConnection

  @Override
  public Collection<Reservation> getAll() {
    Collection<Reservation> c = new ArrayList<>();

    try (PreparedStatement statement = getConnection().prepareStatement(
            "SELECT   reservation_id,         users.username, users.name, " +
                    "     inventory_id,           inventory_code,  brand, " +
                    "     model,                  start_date,     end_date," +
                    "     reservation_status_name " +
                "FROM reservations " +
                "JOIN users ON reservations.username = users.username " +
                "JOIN devices ON reservations.device_id = devices.device_id " +
                "JOIN reservation_status ON reservations.reservation_status_id = reservation_status.reservation_status_id " +
                "ORDER BY start_date DESC")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          c.add(new Reservation(
                  resultSet.getInt("reservation_id"),
                  resultSet.getString("username"),
                  resultSet.getString("name"),
                  resultSet.getString("inventory_id"),
                  resultSet.getString("inventory_code"),
                  resultSet.getString("brand"),
                  resultSet.getString("model"),
                  resultSet.getDate("start_date").toLocalDate(),
                  resultSet.getDate("end_date").toLocalDate(),
                  resultSet.getString("reservation_status_name")
          ));
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close()

    return c;
  }

  @Override
  public Collection<Reservation> getByName(String name) {
    Collection<Reservation> c = new ArrayList<>();

    try (PreparedStatement statement = getConnection().prepareStatement(
            "SELECT   reservation_id,         users.username, users.name, " +
                    "     inventory_id,           inventory_code,  brand, " +
                    "     model,                  start_date,     end_date," +
                    "     reservation_status_name " +
                    "FROM reservations " +
                    "JOIN users ON reservations.username = users.username " +
                    "JOIN devices ON reservations.device_id = devices.device_id " +
                    "JOIN reservation_status ON reservations.reservation_status_id = reservation_status.reservation_status_id " +
                    "WHERE users.name = '" + name + "' " +
                    "ORDER BY start_date DESC")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          c.add(new Reservation(
                  resultSet.getInt("reservation_id"),
                  resultSet.getString("username"),
                  resultSet.getString("name"),
                  resultSet.getString("inventory_id"),
                  resultSet.getString("inventory_code"),
                  resultSet.getString("brand"),
                  resultSet.getString("model"),
                  resultSet.getDate("start_date").toLocalDate(),
                  resultSet.getDate("end_date").toLocalDate(),
                  resultSet.getString("reservation_status_name")
          ));
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close()

    return c;
  }

  @Override
  public Collection<Reservation> getByInventoryId(String invId) {
    Collection<Reservation> c = new ArrayList<>();

    try (PreparedStatement statement = getConnection().prepareStatement(
            "SELECT   reservation_id,         users.username, users.name, " +
                    "     inventory_id,           inventory_code,  brand, " +
                    "     model,                  start_date,     end_date," +
                    "     reservation_status_name " +
                    "FROM reservations " +
                    "JOIN users ON reservations.username = users.username " +
                    "JOIN devices ON reservations.device_id = devices.device_id " +
                    "JOIN reservation_status ON reservations.reservation_status_id = reservation_status.reservation_status_id " +
                    "WHERE inventory_id = '" + invId + "' " +
                    "ORDER BY start_date DESC ")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          c.add(new Reservation(
                  resultSet.getInt("reservation_id"),
                  resultSet.getString("username"),
                  resultSet.getString("name"),
                  resultSet.getString("inventory_id"),
                  resultSet.getString("inventory_code"),
                  resultSet.getString("brand"),
                  resultSet.getString("model"),
                  resultSet.getDate("start_date").toLocalDate(),
                  resultSet.getDate("end_date").toLocalDate(),
                  resultSet.getString("reservation_status_name")
          ));
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close()

    return c;
  }

  @Override
  public Collection<Reservation> getByStatus(String status) {
    Collection<Reservation> c = new ArrayList<>();

    try (PreparedStatement statement = getConnection().prepareStatement(
            "SELECT   reservation_id,         users.username, users.name, " +
            "inventory_id,           inventory_code,  brand,"+
            "model,                  start_date,     end_date, "+
            "reservation_status_name "+
            "FROM reservations "+
            "JOIN users ON reservations.username = users.username "+
            "JOIN devices ON reservations.device_id = devices.device_id "+
            "JOIN reservation_status ON reservations.reservation_status_id = reservation_status.reservation_status_id "+
            "WHERE reservation_status.reservation_status_id = (select reservation_status_id from reservation_status where reservation_status_name = '" + status +"') "+
            "ORDER BY start_date DESC;")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          c.add(new Reservation(
                  resultSet.getInt("reservation_id"),
                  resultSet.getString("username"),
                  resultSet.getString("name"),
                  resultSet.getString("inventory_id"),
                  resultSet.getString("inventory_code"),
                  resultSet.getString("brand"),
                  resultSet.getString("model"),
                  resultSet.getDate("start_date").toLocalDate(),
                  resultSet.getDate("end_date").toLocalDate(),
                  resultSet.getString("reservation_status_name")
          ));
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close()

    return c;
  }

  @Override
  public Collection<Reservation> getConflicts(String invId, LocalDate startDate, LocalDate endDate) {
    Collection<Reservation> c = new ArrayList<>();

    try (PreparedStatement statement = getConnection().prepareStatement(
            "SELECT   reservation_id,         users.username, users.name, " +
                    "     inventory_id,           inventory_code,  brand, " +
                    "     model,                  start_date,     end_date, " +
                    "     reservation_status_name " +
                    "FROM reservations " +
                    "JOIN users ON reservations.username = users.username " +
                    "JOIN devices ON reservations.device_id = devices.device_id " +
                    "JOIN reservation_status ON reservations.reservation_status_id = reservation_status.reservation_status_id " +
                    "WHERE inventory_id = '" + invId + "' " +
                    "AND (start_date between '" + startDate.toString() + "' AND '" + endDate.toString() + "' " +
                      "OR end_date between '" + startDate.toString() + "' AND '" + endDate.toString() + "' )" +
                    "ORDER BY start_date DESC;")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          c.add(new Reservation(
                  resultSet.getInt("reservation_id"),
                  resultSet.getString("username"),
                  resultSet.getString("name"),
                  resultSet.getString("inventory_id"),
                  resultSet.getString("inventory_code"),
                  resultSet.getString("brand"),
                  resultSet.getString("model"),
                  resultSet.getDate("start_date").toLocalDate(),
                  resultSet.getDate("end_date").toLocalDate(),
                  resultSet.getString("reservation_status_name")
          ));
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close()

    return c;
  }

  @Override
  public void add(String username, String invId, LocalDate startDate, LocalDate endDate) {
    try (PreparedStatement statement =
                 getConnection().prepareStatement("insert into reservations  "
                         + "(username, device_id, start_date, end_date, reservation_status_id) " +
                         "values (?, " +
                         "(select device_id from devices where inventory_id = '" + invId + "')," +
                         " ?, " +
                         " ?," +
                         "1)")) {
      statement.setString(1, username);
      statement.setDate(2, Date.valueOf(startDate));
      statement.setDate(3, Date.valueOf(endDate));
      // 1. insert the new entry
      statement.executeUpdate();
    }
    catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
  }

  @Override
  public void update(int reservationId, LocalDate startDate, LocalDate endDate) {
    try (PreparedStatement statement = getConnection().prepareStatement(
            "UPDATE reservations SET start_date= ?, end_date= ? " +
                "WHERE reservation_id = ?")) {
      statement.setDate(1, Date.valueOf(startDate));
      statement.setDate(2, Date.valueOf(endDate));
      statement.setInt(3, reservationId);
      statement.executeUpdate();
    }
    catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
  }

  @Override
  public void delete(int reservationId) {
    try (PreparedStatement statement = getConnection().prepareStatement(
            "DELETE FROM reservations WHERE reservation_id = ?")) {
      statement.setInt(1, reservationId);
      statement.executeUpdate();
    }
    catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
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
