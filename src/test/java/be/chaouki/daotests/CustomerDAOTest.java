package be.chaouki.daotests;


import be.chaouki.dao.CustomerDAO;
import be.chaouki.dao.DAOFactory;
import be.chaouki.entities.Customer;
import java.util.List;
import org.junit.After;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * based on https://github.com/geowarin/hibernate-examples/tree/master/hibernate-jpa-standalone-dbunit
 *
 */
public class CustomerDAOTest extends AbstractDbUnitJpaTest {
    
    private static Logger log = LoggerFactory.getLogger(CustomerDAOTest.class);
    private static CustomerDAO customerDAO;
    
    @BeforeClass
    public static void init(){
        customerDAO=DAOFactory.getCustomerDAO(entityManager);
    }
    
    @After
    public void afterStatePrint(){
        log.info("CUSTOMER TABLE START----------------------------------------------------");
        for(Customer c : customerDAO.findAll())
            log.info(c.toString());
        log.info("CUSTOMER TABLE END------------------------------------------------------");
    }
    
    @Test
    public void testAdd(){
        final int INITIAL_SIZE=customerDAO.findAll().size();
        
        Customer customer= new Customer(null, "Monsieur Derpidou", "Rue Derp", 435, "DerpVille", "0477567778");
        customerDAO.add(customer);
        
        Assert.assertEquals(INITIAL_SIZE+1, customerDAO.findAll().size());
        
        Customer newCustomer=customerDAO.findById(customer.getId());
        Assert.assertNotNull(newCustomer);
        Assert.assertEquals(newCustomer, customer);
    }

    @Test
    public void testFindById() {
            Customer customer = customerDAO.findById(500L);
            Assert.assertNotNull(customer);
            Assert.assertEquals("customerTest", customer.getName());
    }

    @Test
    public void testFindAll() {
        Assert.assertEquals(3, customerDAO.findAll().size());
    }
    
    @Test
    public void testFindByCriteria(){
        List<Customer> resultList=null;
        
        // find by nothing (find all)
        resultList=customerDAO.findSearch(null, null, null, null, null, null);
        Assert.assertEquals(3, resultList.size());
        
        // find by name
        resultList=customerDAO.findSearch(null, "customer", null, null, null, null);
        Assert.assertEquals(2, resultList.size());
        
        resultList=customerDAO.findSearch(null, "Test", null, null, null, null);
        Assert.assertEquals(2, resultList.size());
        
        resultList=customerDAO.findSearch(null, "test", null, null, null, null);
        Assert.assertEquals(2, resultList.size());
        
        resultList=customerDAO.findSearch(null, "2", null, null, null, null);
        Assert.assertEquals(1, resultList.size());
        Assert.assertEquals(501L, resultList.get(0).getId().longValue());
        
        resultList=customerDAO.findSearch(null, "omerTes", null, null, null, null);
        Assert.assertEquals(2, resultList.size());
        
        resultList=customerDAO.findSearch(null, "omertes", null, null, null, null);
        Assert.assertEquals(2, resultList.size());
        
        resultList=customerDAO.findSearch(null, "merTest2", null, null, null, null);
        Assert.assertEquals(1, resultList.size());
        Assert.assertEquals(501L, resultList.get(0).getId().longValue());
        
        resultList=customerDAO.findSearch(null, "two", null, null, null, null);
        Assert.assertEquals(1, resultList.size());
        Assert.assertEquals(502L, resultList.get(0).getId().longValue());
        
        resultList=customerDAO.findSearch(null, "Two", null, null, null, null);
        Assert.assertEquals(1, resultList.size());
        Assert.assertEquals(502L, resultList.get(0).getId().longValue());
        
        resultList=customerDAO.findSearch(null, "street", null, null, null, null);
        Assert.assertEquals(0, resultList.size());
        
        // find by id
        resultList=customerDAO.findSearch(500L, null, null, null, null, null);
        Assert.assertEquals(1, resultList.size());
        Assert.assertEquals(500L, resultList.get(0).getId().longValue());
        Assert.assertEquals("customerTest", resultList.get(0).getName());
        Assert.assertEquals("test street", resultList.get(0).getAddress());
        
        // find by id and name
        resultList=customerDAO.findSearch(500L, "customerTest", null, null, null, null);
        Assert.assertEquals(1, resultList.size());
        Assert.assertEquals(500L, resultList.get(0).getId().longValue());
        Assert.assertEquals("customerTest", resultList.get(0).getName());
        
        resultList=customerDAO.findSearch(500L, "customerTestA", null, null, null, null);
        Assert.assertEquals(0, resultList.size());
    }
    
    @Test
    public void modifyByEntityTest(){
        log.info("Test 5");
        Customer customer = customerDAO.findById(500L);
        Assert.assertNotNull(customer);
        Assert.assertEquals("test street", customer.getAddress());
        
        customer.setAddress(customer.getAddress()+" four");
        customerDAO.modify(customer);
        
        Customer customerModified = customerDAO.findById(500L);
        Assert.assertNotNull(customerModified);
        Assert.assertEquals("test street four", customerModified.getAddress());
    }
    
    @Test
    public void modifyByFieldsTest(){
        log.info("Test 6");
        Customer customer = customerDAO.findById(500L);
        Assert.assertNotNull(customer);
        Assert.assertEquals("test street", customer.getAddress());
        
        customerDAO.modify(customer.getId(), customer.getName(), customer.getAddress()+" four", customer.getPostalCode(), customer.getMunicipality(), customer.getPhoneNumber());
        
        Customer customerModified = customerDAO.findById(500L);
        Assert.assertNotNull(customerModified);
        Assert.assertEquals("test street four", customerModified.getAddress());
    }
    
    @Test
    public void removeByEntityTest(){
        log.info("Test 7");
        final int INITIAL_SIZE=3;
        
        Customer customer = customerDAO.findById(501L);
        Assert.assertNotNull(customer);
        
        Assert.assertEquals(INITIAL_SIZE, customerDAO.findAll().size());
        
        customerDAO.remove(customer);
        Customer customerDeleted = customerDAO.findById(501L);
        Assert.assertNull(customerDeleted);
        Assert.assertEquals(INITIAL_SIZE-1, customerDAO.findAll().size());
    }
    
    @Test
    public void removeByIdTest(){
        log.info("Test 8");
        final int INITIAL_SIZE=3;
        
        Customer customer = customerDAO.findById(501L);
        Assert.assertNotNull(customer);
        
        Assert.assertEquals(INITIAL_SIZE, customerDAO.findAll().size());
        
        customerDAO.remove(501L);
        Customer customerDeleted = customerDAO.findById(501L);
        Assert.assertNull(customerDeleted);
        Assert.assertEquals(INITIAL_SIZE-1, customerDAO.findAll().size());
    }
}
