package swe4.Server.Dal;

import swe4.entities.Device;

import java.util.Collection;

public interface IDeviceDao extends AutoCloseable {
  Collection<String> getCategories();
  Collection<Device> getAll(boolean isForUser);
  Collection<Device> getByInventoryId(String invId, boolean isForUser);
  Collection<Device> getByInventoryCode(String invId, boolean isForUser);
  Collection<Device> getByName(String name, boolean isForUser);
  Collection<Device> getByBrand(String brand, boolean isForUser);
  Collection<Device> getByModel(String model, boolean isForUser);
  Collection<Device> getByCategory(String category, boolean isForUser);
  void add(Device device);
  void update(String invIdBeforeUpdate, Device device);
  void delete(String invId);

}
