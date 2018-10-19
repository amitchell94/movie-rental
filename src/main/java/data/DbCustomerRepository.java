package data;

import logic.customer.Customer;
import logic.customer.CustomerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbCustomerRepository implements CustomerRepository {
    @Override
    public Customer getCustomer(String name) {
        Customer customer = new Customer();
        Integer id = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(ConnectionCreator.createConnectionUrl());
            preparedStatement = connection.prepareStatement("SELECT * from blockbuster.customers where c_name=?");
            preparedStatement.setString(1, name);
            resultSet =preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer.setId(resultSet.getInt("c_id"));
                customer.setName(resultSet.getString("c_name"));
                customer.setAddress(resultSet.getString("c_address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return customer;
    }

    @Override
    public void save(Customer customer) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(ConnectionCreator.createConnectionUrl());

            preparedStatement = connection.prepareStatement("insert into  blockbuster.customers (c_name, c_address) " +
                    "values (?, ?)");
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();

        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(ConnectionCreator.createConnectionUrl());

            resultSet = connection.createStatement().executeQuery("select * from blockbuster.customers");

            customerList = transformToCustomerList(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return customerList;
    }

    private List<Customer> transformToCustomerList(ResultSet resultSet) throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        while (resultSet.next()) {
            Customer customer = new Customer();
            customer.setId(resultSet.getInt("c_id"));
            customer.setName(resultSet.getString("c_name"));
            customer.setAddress(resultSet.getString("c_address"));
            customerList.add(customer);
        }
        return customerList;
    }
}
