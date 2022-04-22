package dao;

import model.Address;

public interface AddressDao {
    void save(Address address);

    long getId(Address address);

    Address getById(long id);
}
