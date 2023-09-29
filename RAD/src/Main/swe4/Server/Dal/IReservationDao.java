package swe4.Server.Dal;

import swe4.entities.Reservation;

import java.time.LocalDate;
import java.util.Collection;

public interface IReservationDao extends AutoCloseable {
  Collection<Reservation> getAll();
  Collection<Reservation> getByName(String name);
  Collection<Reservation> getByInventoryId(String invId);
  Collection<Reservation> getByStatus(String status);
  Collection<Reservation> getConflicts(String invId, LocalDate startDate, LocalDate endDate);
  void add(String username, String invId, LocalDate startDate, LocalDate endDate);
  void update(int reservationId, LocalDate startDate, LocalDate endDate);
  void delete(int reservationId);
  Reservation getById(int id);

}
