package com.example.Bank.entites;


public class Customer {

    private int id;
    private String customerName;
    private int account;


    public Customer(){}

    public Customer(int id, String customerName, int account) {
        this.id = id;
        this.customerName = customerName;
        this.account = account;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}