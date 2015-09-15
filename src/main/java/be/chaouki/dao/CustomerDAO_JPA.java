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
        em=PersistenceManager.getEntityManager();
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
    public void add(Customer customer){
        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();
    }
    
    @Override
    public List<Customer> findAll(){
        TypedQuery<Customer> createQuery = em.createQuery("FROM Customer", Customer.class);
        List<Customer> resultList = createQuery.getResultList();
        return resultList;
    }

    @Override
    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    @Override
    public List<Customer> findSearch(Long id, String name, String address, Integer postalCode, String municipality, String phoneNumber) {
        int criteriaCount=0;
        if(id!=null)
            criteriaCount++;
        if(name!=null)
            criteriaCount++;
        if(address!=null)
            criteriaCount++;
        if(postalCode!=null)
            criteriaCount++;
        if(municipality!=null)
            criteriaCount++;
        if(phoneNumber!=null)
            criteriaCount++;
        
        if(criteriaCount==0)
            return findAll();
        
        String query=" SELECT c FROM Customer c WHERE ";
        
        int criteriaProcessed=0;
        // first criteria
        if(id!=null){
            query+="c.id = :id";
            criteriaProcessed=1;
        }
        else if(name!=null){
            query+="UPPER(c.name) LIKE :name";
            criteriaProcessed=2;
        }
        else if(address!=null){
            query+="UPPER(c.address) LIKE :address";
            criteriaProcessed=3;
        }
        else if(postalCode!=null){
            query+="c.postalCode = :postalCode";
            criteriaProcessed=4;
        }
        else if(municipality!=null){
            query+="UPPER(c.municipality) LIKE :municipality";
            criteriaProcessed=5;
        }
        else if(phoneNumber!=null){
            query+="c.phoneNumber = :phoneNumber";
            criteriaProcessed=6;
        }
        
        // 2 or more criterias
        if(criteriaCount>1){
            switch(criteriaProcessed){
                case 1:
                    if(name!=null)
                        query+=" AND UPPER(c.name) LIKE :name";
                case 2:
                    if(address!=null)
                        query+=" AND UPPER(c.address) LIKE :address";
                case 3:
                    if(postalCode!=null)
                        query+=" AND c.postalCode = :postalCode";
                case 4:
                    if(municipality!=null)
                        query+=" AND UPPER(c.municipality) LIKE :municipality";
                case 5:
                    if(phoneNumber!=null)
                        query+=" AND c.phoneNumber = :phoneNumber";
                    break;
            }
        }
        
        log.info(query);
        
        TypedQuery<Customer> createQuery = em.createQuery(query, Customer.class);
        
        if(id!=null)
            createQuery.setParameter("id", id);
        if(name!=null)
            createQuery.setParameter("name", "%" + name.toUpperCase() + "%");
        if(address!=null)
            createQuery.setParameter("address", "%" + address.toUpperCase() + "%");
        if(postalCode!=null)
            createQuery.setParameter("postalCode", postalCode);
        if(municipality!=null)
            createQuery.setParameter("municipality", "%" + municipality.toUpperCase() + "%");
        if(phoneNumber!=null)
            createQuery.setParameter("phoneNumber", phoneNumber);
        
        List<Customer> resultList = createQuery.getResultList();
        return resultList;
    }

    @Override
    public void modify(Customer modifiedCustomer) {
        em.getTransaction().begin();
        Customer customer=em.find(Customer.class, modifiedCustomer.getId());
        if(customer==null)
            throw new IllegalArgumentException("Attempt at modyfying a customer with an unknown id");
        
        em.merge(modifiedCustomer);
        em.getTransaction().commit();
    }

    @Override
    public void modify(Long id, String name, String address, Integer postalCode, String municipality, String phoneNumber) {
        em.getTransaction().begin();
        Customer customer=em.find(Customer.class, id);
        if(customer==null)
            throw new IllegalArgumentException("Attempt at modyfying a customer with an unknown id");
        
        Customer modifiedCustomer=new Customer(id, name, address, postalCode, municipality, phoneNumber);
        em.merge(modifiedCustomer);
        em.getTransaction().commit();
    }

    @Override
    public void remove(Customer customer) {
        em.getTransaction().begin();
        Customer customerToDelete=em.find(Customer.class, customer.getId());
        if(customerToDelete==null)
            throw new IllegalArgumentException("Attempt at deleting a customer with an unknown id");
        
        em.remove(em.merge(customerToDelete));
        em.getTransaction().commit();
    }

    @Override
    public void remove(Long id) {
        em.getTransaction().begin();
        Customer customerToDelete=em.find(Customer.class, id);
        if(customerToDelete==null)
            throw new IllegalArgumentException("Attempt at deleting a customer with an unknown id");
        
        em.remove(em.merge(customerToDelete));
        em.getTransaction().commit();
    }
}
