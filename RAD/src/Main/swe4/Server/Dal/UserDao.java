package swe4.Server.Dal;
import swe4.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class UserDao implements IUserDao {
  private Connection connection;
  private final String
          connectionString,
          username,
          password;

  public UserDao(String connectionString, String username, String password) {
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
  public Collection<User> getAll() {
    Collection<User> c = new ArrayList<>();

    try (PreparedStatement statement = getConnection().prepareStatement(
            "SELECT * FROM users " +
                "JOIN roles " +
                "ON users.role_id = roles.role_id " +
                "ORDER BY name;")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          c.add(new User(
                  resultSet.getString("name"),
                  resultSet.getString("username"),
                  resultSet.getString("password"),
                  resultSet.getString("role_name")
          ));
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException( "SQLException: " + ex.getMessage());
    } // includes finally statement.close()

    return c;
  }

  @Override
  public User getByUsername(String username) {
    try (PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM users WHERE username = ?")) {
      statement.setString(1, username);

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return new User(
                  resultSet.getString("username"),
                  resultSet.getString("name"),
                  resultSet.getString("password"),
                  resultSet.getString("role_id")
          );
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    } // includes finally statement.close()

    return null;//no user found
  }

  @Override
  public void add(User user) {
    try (PreparedStatement statement =
                 getConnection().prepareStatement(
                         "INSERT INTO users "
                           + "(username, name, password, role_id)" +
                             "values (?, ?, ?, (SELECT role_id FROM roles WHERE role_name = ? " + "));"
                 )) {
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getName());
      statement.setString(3, user.getPassword());
      statement.setString(4, user.getRole());
      // 1. insert the new entry
      statement.executeUpdate();
    }
    catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
  }

  @Override
  public void delete(String username) {
    try (PreparedStatement statement =
                 getConnection().prepareStatement("DELETE FROM users WHERE username = ?")) {
      statement.setString(1, username);
      statement.executeUpdate();
    }
    catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }
  }

  @Override
  public boolean authenticate(String username, String password) {
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(
                 "SELECT EXISTS (" +
                         "SELECT 1 " +
                         "FROM users " +
                         "WHERE username = ? " +
                         "AND password = ?) " +
                         "AS cred_match")) {
      statement.setString(1, username);
      statement.setString(2, password);

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          int result = resultSet.getInt("cred_match");
          return result == 1;
        }
      }
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
    }

    return false;
  }

  @Override
  public void update(String usernameBeforeUpdate, User user ) {
    try (PreparedStatement statement = getConnection().prepareStatement(
    "UPDATE users SET username= ?, name= ?, password= ?, " +
            "role_id= (SELECT role_id FROM roles WHERE role_name = ?)" +
            "WHERE username = ?")) {
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getName());
      statement.setString(3, user.getPassword());
      statement.setString(4, user.getRole());
      statement.setString(5, usernameBeforeUpdate);
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
