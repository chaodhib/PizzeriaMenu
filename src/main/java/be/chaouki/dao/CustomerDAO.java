/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.chaouki.dao;

import be.chaouki.entities.Customer;
import java.util.List;

/**
 *
 * @author chaouki
 */
public interface CustomerDAO {

    // CREATE
    void add(Customer customer);

    // READ
    
    /**
     * This method finds all Customers that match the criterias given by parameters.
     * 
     * <p>name, adress and municipality can be multiple words with a white space
     * acting as a seperator. Example: FindSearch(null, "charles fred", null,...)
     * will return the customers named "fred charles", "charles frederick"
     * and even "frederickcharles". Upper/lowercase differences for 
     * the name, address and municipality are ignored.
     * 
     * <p>!!! Accents are not supported !!!
     * 
     * <p>Id and postalCode must be exact matches.
     * 
     * <p>The phoneNumber given should either be an exact match or it should 
     * at least match the tail of the one saved. Example: if "555" is given 
     * as parameter, customers with "0477777555" and "+3277777555" will 
     * be returned but not "555486443" or "04775556534".
     * 
     * <p>Multiple criterias (a name and a phone number for example) can be used 
     * at the same time and the result set will be 
     * the intersection of the sets returned by the individuals queries.
     * 
     * @param id
     * @param name
     * @param address
     * @param postalCode
     * @param municipality
     * @param phoneNumber
     * @return 
     */
    List<Customer> findSearch(Long id, String name, String address, Integer postalCode, String municipality, String phoneNumber);
    
    Customer findById(Long id);

    List<Customer> findAll();
    
    // UPDATE
    void modify(Customer modifiedCustomer);
    void modify(Long id, String name, String address, Integer postalCode, String municipality, String phoneNumber);
    
    // DELETE
    void remove(Customer customer);
    void remove(Long id);
}
