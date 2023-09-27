package swe4.Server.Dal;

import swe4.entities.Device;

import java.util.Collection;

public interface IDeviceDao extends AutoCloseable {
  Collection<String> getCategories();
  Collection<Device> getAllForAdmin();
  Collection<Device> getAllForUser();
  Collection<Device> getByInventoryId(String invId);
  Collection<Device> getByInventoryCode(String invId);
  Collection<Device> getByName(String name);
  Collection<Device> getByBrand(String brand);
  Collection<Device> getByModel(String model);
  Collection<Device> getByCategory(String category);
  void add(Device device);
  void update(String invIdBeforeUpdate, Device device);
  void delete(String invId);

}
