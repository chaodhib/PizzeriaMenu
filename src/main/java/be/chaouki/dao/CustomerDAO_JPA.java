/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.chaouki.dao;

import be.chaouki.entities.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.SynchronizationType;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author chaouki
 */
public class CustomerDAO_JPA implements CustomerDAO {
    
    private static Logger log = LoggerFactory.getLogger(CustomerDAO_JPA.class);
    
    private EntityManager em;
    
    public CustomerDAO_JPA(){
        em=PersistenceManager.getInstanceEM();
    }

    /**
     * Used for tests with dbunit
     * @param em 
     */
    public CustomerDAO_JPA(EntityManager em){
        this.em=em;
        log.info("CustomerDAO_JPA construct with factory"+em.toString());
    }

    @Override
    protected void finalize() throws Throwable {
        em.close();
        super.finalize();
    }
    
    
    
    @Override
    public void add(Customer customer){
        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();
    }
    
//    private void displayAll(EntityManager em){
//        TypedQuery<Customer> createQuery = em.createQuery("FROM Customer", Customer.class);
//        List<Customer> resultList = createQuery.getResultList();
//        
//        log.info("CUSTOMER TABLE START----------------------------------------------------");
//        for(Customer c : resultList)
//            log.info(c.toString());
//        log.info("CUSTOMER TABLE END------------------------------------------------------");
//    }
    
    @Override
    public List<Customer> findAll(){
        TypedQuery<Customer> createQuery = em.createQuery("FROM Customer", Customer.class);
        List<Customer> resultList = createQuery.getResultList();
        log.info("findAll call in DAO_JPA. Size= "+resultList.size());
        return resultList;
    }
    
    @Override
    public List<Customer> find(){
        return null;
    }
    
}
