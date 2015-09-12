package be.chaouki.daotests;


import be.chaouki.dao.CustomerDAO;
import be.chaouki.dao.DAOFactory;
import be.chaouki.entities.Customer;
import java.util.List;
import org.junit.After;

import org.junit.Assert;
import org.junit.Before;
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
    
    @Before
    public void init(){
        log.info("init() call");
        customerDAO=DAOFactory.getCustomerDAO(entityManager);
    }
    
    @Test
    public void testAdd(){
        final int INITIAL_SIZE=customerDAO.findAll().size();
        
        Customer customer= new Customer();
        customer.setAddress("Rue Derp");
        customer.setMunicipality("DerpVille");
        customer.setName("Monsieur Derpidou");
        customer.setPostalCode(435);
        customer.setPhoneNumber("0477567778");
        
        customerDAO.add(customer);
        Assert.assertEquals(INITIAL_SIZE+1, customerDAO.findAll().size());
        
        Customer newCustomer=customerDAO.findById(customer.getId());
        Assert.assertNotNull(newCustomer);
        Assert.assertEquals(newCustomer, customer);
    }

    @Test
    public void testFind() {
            Customer customer = customerDAO.findById(500L);
            Assert.assertNotNull(customer);
            Assert.assertEquals("customerTest", customer.getName());
    }

    @Test
    public void testFindAll() {
        Assert.assertEquals(3, customerDAO.findAll().size());
    }
    
    @Ignore
    @Test
    public void testFindByName(){
        List<Customer> resultList=null;
        
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
        
    }
    
    @Test
    public void modifyTest(){
        log.info("modify TEST START");
        Customer customer = customerDAO.findById(500L);
        customer.setAddress(customer.getAddress()+" four");
        
        Customer customerModified = customerDAO.findById(500L);
        Assert.assertEquals("test street four", customerModified.getAddress());
    }
    
    @After
    public void afterStatePrint(){
        log.info("CUSTOMER TABLE START----------------------------------------------------");
        for(Customer c : customerDAO.findAll())
            log.info(c.toString());
        log.info("CUSTOMER TABLE END------------------------------------------------------");
        
        
    }
}
