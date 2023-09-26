package swe4.Server.Dal;

import swe4.Server.Dal.DataAccessException;

import swe4.Server.Dal.IUserDao;
import swe4.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class UserDao implements IUserDao {
  private Connection connection;
  private String
          connectionString,
          username,
          password;
  public UserDao(String connectionString) {
    this(connectionString, null, null);
  }

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

    try (PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM users")) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          c.add(new User(
                  resultSet.getString("username"),
                  resultSet.getString("name"),
                  resultSet.getString("password"),
                  resultSet.getString("role")
          ));
        }
      } // includes finally resultSet.close();
    } catch (SQLException ex) {
      throw new DataAccessException("SQLException: " + ex.getMessage());
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
                  resultSet.getString("role")
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
                 getConnection().prepareStatement("insert into users "
                                 + "(username, name, password, role) values (?, ?, ?, ?)")) {
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
                 getConnection().prepareStatement("delete FROM users where username = ?")) {
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
    "update users SET username= ?, name= ?, password= ?, role= ? where username = ?")) {
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

  }
}
