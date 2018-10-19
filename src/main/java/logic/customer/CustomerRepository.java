package logic.customer;

import java.util.List;

public interface CustomerRepository {
    void save(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomer(String name);
}
