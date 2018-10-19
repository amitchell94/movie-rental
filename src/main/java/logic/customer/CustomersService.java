package logic.customer;

import java.util.List;

public class CustomersService {
    private CustomerRepository customerRepository;

    public CustomersService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public List<Customer> getAllcustomers () {
        return customerRepository.getAllCustomers();
    }

    public Customer getCustomerFromName(String customerName) {
        return customerRepository.getCustomer(customerName);
    }
}
