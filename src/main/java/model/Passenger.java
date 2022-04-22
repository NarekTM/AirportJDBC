package model;

public class Passenger {
    private String name;
    private String phone;
    private long addressId;
    private Address address;

    public Passenger(String name, String phone, long address_id) {
        this.name = name;
        this.phone = phone;
        this.addressId = address_id;
    }

    public Passenger(String name, String phone, Address address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        String addressInfo;
        if (addressId != 0) {
            addressInfo = "address ID = " + addressId;
        } else {
            addressInfo = address.toString();
        }
        return "name = " + name +
                ", phone = " + phone +
                ", " + addressInfo;
    }
}
