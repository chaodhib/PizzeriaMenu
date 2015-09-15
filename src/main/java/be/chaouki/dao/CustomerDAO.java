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
     * It will ignore upper/lowercase differences for the strings name, address and municipality.
     * 
     * Id and postalCode must be exact matches.
     * 
     * name, adress and municipality must be ONE word contained in the stored string.
     * example: findSearch(null, "Peter", null, null, ...) will find the customers
     * named "Peterpan" and "Panpeterone". However findSearch(null, "Peter Pan", null, null, ...)
     * will not find "Pan Peter" nor "Peter".
     * 
     * Multiple criterias can be used at the same time and the result set will be
     * the intersection of the sets returned by the individuals queries.
     * 
     * XXXXXXXXXX PHONE NUMBER XXXXXXXXXXXXXXXXXXXXXX
     * 
     * ! Accents are not supported !
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
