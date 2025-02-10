package com.example.Bank.dbconnection;

import com.example.Bank.entites.Customer;

import java.sql.*;
import java.util.ArrayList;

public class DbConnection {
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String username = "postgres";
    private final String password = "tilegen0609";


    public Connection connect() throws Exception{
        Connection con = DriverManager.getConnection(url, username, password);
        System.out.println("Connected to database");
        return con;
    }

    public int disconnect(Connection con) throws SQLException{
        if(con!=null) {
            con.close();
            System.out.println("Disconnected from database");
            return 0;
        }
        System.out.println("No connection to database");
        return 1;
    }

    public ArrayList<Customer> getCustomers(Connection con) throws SQLException{
        String query = "SELECT * FROM public.customers";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Customer> customers = new ArrayList<>();
        while(rs.next()) {
            Customer customer = new Customer();
            customer.setCustomerName(rs.getString("customerName"));
            customer.setId(rs.getInt("id"));
            customer.setAccount(rs.getInt("account"));
            customers.add(customer);
        }
        st.close();
        disconnect(con);
        return customers;
    }

    public Customer findCustomerById(Connection con, int id) throws SQLException{
        String query = "SELECT * FROM public.customers WHERE id = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();

        ArrayList<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        while(rs.next()) {
            customer.setCustomerName(rs.getString("customerName"));
            customer.setId(rs.getInt("id"));
            customer.setAccount(rs.getInt("account"));
            customers.add(customer);
        }
        st.close();
        disconnect(con);
        return customer;
    }

    public Customer createCustomer(Connection con, Customer customer) throws SQLException{
        String query = "INSERT INTO public.customers (id, customername, account) VALUES (?, ?, ?)";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, customer.getId());
        st.setString(2, customer.getCustomerName());
        st.setInt(3, customer.getAccount());

        int success = st.executeUpdate();
        st.close();
        disconnect(con);
        if (success > 0) {
            System.out.println("Customer created successfully");
            return customer;
        }
        return null;
    }

    public Customer updateCustomer(Connection con, Customer customer) throws SQLException {
        String query = "UPDATE public.customers SET customername=?, account=? WHERE id=?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, customer.getCustomerName());
        st.setInt(2, customer.getAccount());
        st.setInt(3, customer.getId());

        int success = st.executeUpdate();
        st.close();
        disconnect(con);
        if (success > 0) {
            System.out.println("Customer is updated");
            return customer;
        }
        return null;
    }

    public Customer deleteCustomer(Connection con, int id) throws SQLException {
        String query = "DELETE FROM public.customers WHERE id=?";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, id);

        int success = st.executeUpdate();
        st.close();
        disconnect(con);
        if (success > 0) {
            System.out.println("Customer is deleted");
            return new Customer(id, null, 0);
        }
        return null;
    }
}