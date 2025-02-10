package com.example.Bank.controller;

import com.example.Bank.entites.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.Bank.dbconnection.DbConnection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class MyController {

    private final ObjectMapper oMapper;

    public MyController(ObjectMapper objectMapper) {
        this.oMapper = objectMapper;
    }


    @GetMapping("/main/customer")
    public String myCustomerListener(){
        Customer customer1 = new Customer(1, "Tilegen", 10000);
        String jsonData=null;
        try{
            jsonData=oMapper.writeValueAsString(customer1);
        }catch(JsonProcessingException e){
            System.out.println("Error system");
        }

        return jsonData;
    }

    @PostMapping("/main/customCustomer")
    public String myCustomCustomerListener(@RequestParam int id,
                                           @RequestParam String customerName,
                                           @RequestParam int account) {
        String jsonData = null;
        Customer customer1 = new Customer(id, customerName, account);
        try {
            jsonData = oMapper.writeValueAsString(customer1);
        } catch (JsonProcessingException e) {
            System.out.println("Error system" + e.toString());
        }

        DbConnection con = new DbConnection();
        try {
            con.connect();
        } catch (Exception e) {
            System.out.println("Error with database connection");
            throw new RuntimeException(e);
        }
        return jsonData;
    }

    @GetMapping("/main/allCustomers")
    public String myAllCustomersListener(){
        DbConnection myConnection=new DbConnection();
        Connection con=null;
        ArrayList<Customer> customers=new ArrayList<Customer>();
        try  {
            con = myConnection.connect();
            customers = myConnection.getCustomers(con);
        }catch(Exception e){
            System.out.println("Error getting all customers");
        }


        String jsonData=null;
        try{
            jsonData=oMapper.writeValueAsString(customers);
        }catch (JsonProcessingException e){
            System.out.println("Some error with customer");
        }


        return jsonData;
    }

    @PostMapping("/main/findCustomer")
    public String myFindCustomerListener(@RequestParam int id) {
        DbConnection myConnection=new DbConnection();
        Connection con=null;
        Customer customer1=null;
        try {
            con = myConnection.connect();
            customer1 = myConnection.findCustomerById(con, id);
        }catch(Exception e){
            System.out.println("Error finding customer");
        }

        String jsonData=null;
        try{
            jsonData=oMapper.writeValueAsString(customer1);
        }catch (Exception e) {
            System.out.println("Some error with customer");
        }

        return jsonData;
    }

    @PostMapping("/main/createCustomer")
    public String myCreateCustomerListener(@RequestParam int id,
                                           @RequestParam String customerName,
                                           @RequestParam int account) {
        DbConnection myConnection=new DbConnection();
        Customer customer1 = new Customer(id, customerName, account);
        String jsonData = null;
        try (Connection con = myConnection.connect()) {
            Customer createdCustomer = myConnection.createCustomer(con, customer1);
            jsonData = oMapper.writeValueAsString(createdCustomer);
        } catch (JsonProcessingException e) {
            System.out.println("Error with JSON processing: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error creating customer: " + e.getMessage());
        }
        return jsonData;
    }

    @PostMapping("/main/updateCustomer")
    public String updateCustomer(@RequestParam int id,
                                 @RequestParam String newCustomerName,
                                 @RequestParam int newAccount) {
        DbConnection myConnection = new DbConnection();
        Connection con = null;
        Customer customer = null;
        String jsonData = null;
        try {
            con = myConnection.connect();
            customer = myConnection.findCustomerById(con, id);
            System.out.println(customer);
            if (customer != null) {
                customer.setCustomerName(newCustomerName);
                customer.setAccount(newAccount);
                System.out.println(customer);
                con = myConnection.connect();
                myConnection.updateCustomer(con, customer);
            }
        } catch (Exception e) {
            System.out.println("Customer Update Error: " + e.getMessage());
        }

        try {
            jsonData = oMapper.writeValueAsString(customer);
        } catch (JsonProcessingException e) {
            System.out.println("Some error with customer");
        }

        return jsonData;
    }

    @PostMapping("/main/deleteCustomer")
    public String deleteCustomer(@RequestParam int id) {
        DbConnection myConnection = new DbConnection();
        Connection con = null;
        Customer deletedCustomer = null;
        String jsonData = null;
        try {
            con = myConnection.connect();
            deletedCustomer = myConnection.deleteCustomer(con, id);
        } catch (Exception e) {
            System.out.println("Customer Delete Error: " + e.getMessage());
        }
        try {
            jsonData = oMapper.writeValueAsString(deletedCustomer);
        } catch (JsonProcessingException e) {
            System.out.println("Some error with delete operation ");
        }
        return jsonData;
    }

}