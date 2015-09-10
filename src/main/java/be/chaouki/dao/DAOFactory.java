/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.chaouki.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author chaouki
 */ 
public class DAOFactory {
    
//    private static CustomerDAO customerDAO;
//    
    
      // non threat safe
//    public static CustomerDAO getCustomerDAO(){
//        if(customerDAO==null)
//            customerDAO=new CustomerDAO_JPA();
//        return customerDAO;
//    }
    
    public static CustomerDAO getCustomerDAO(){
        return new CustomerDAO_JPA();
    }
    
    /**
     * Used for tests with dbunit
     * @param em 
     */
    public static CustomerDAO getCustomerDAO(EntityManager em){
        return new CustomerDAO_JPA(em);
    }
    
//    public static CustomerDAO getCustomerDAO(){
//        return new CustomerDAO_JPA();
//    }
}
