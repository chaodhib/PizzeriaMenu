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
        if(id==null && name==null && address==null && postalCode==null && 
                municipality==null && phoneNumber==null)
            return findAll();
        
        String query=" SELECT c FROM Customer c WHERE 1=1";
        String[] nameWords=null, addressWords=null, municipalityWords=null;
        
        if(id!=null)
            query+=" AND c.id = :id";
        if(name!=null){
            nameWords = name.split("\\s+"); // "anne frank" -> "anne" and "frank"
            for(int i=0; i<nameWords.length; i++)
                query+=" AND UPPER(c.name) LIKE :name"+i;
        }
        if(address!=null){
            addressWords = address.split("\\s+");
            for(int i=0; i<addressWords.length; i++)
                query+=" AND UPPER(c.address) LIKE :address"+i;
        }
        if(postalCode!=null)
            query+=" AND c.postalCode = :postalCode";
        if(municipality!=null){
            municipalityWords = municipality.split("\\s+");
            for(int i=0; i<municipalityWords.length; i++)
                query+=" AND UPPER(c.municipality) LIKE :municipality"+i;
        }
        if(phoneNumber!=null)
            query+=" AND c.phoneNumber LIKE :phoneNumber";
        
        log.info(query);
        
        TypedQuery<Customer> createQuery = em.createQuery(query, Customer.class);
        
        if(id!=null)
            createQuery.setParameter("id", id);
        if(name!=null)
            for(int i=0; i<nameWords.length; i++)
                createQuery.setParameter("name"+i, "%" + nameWords[i].toUpperCase() + "%");
        if(address!=null)
            for(int i=0; i<addressWords.length; i++)
                createQuery.setParameter("address"+i, "%" + addressWords[i].toUpperCase() + "%");
        if(postalCode!=null)
            createQuery.setParameter("postalCode", postalCode);
        if(municipality!=null)
            for(int i=0; i<municipalityWords.length; i++)
                createQuery.setParameter("municipality"+i, "%" + municipalityWords[i].toUpperCase() + "%");
        if(phoneNumber!=null)
            createQuery.setParameter("phoneNumber", "%" + phoneNumber);
        
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
