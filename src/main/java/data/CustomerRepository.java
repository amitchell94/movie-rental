package data;

import application.Customer;

import java.util.List;

public interface CustomerRepository {
    void save(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomer(String name);
}
