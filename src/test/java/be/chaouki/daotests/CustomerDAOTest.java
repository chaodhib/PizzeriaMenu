package be.chaouki.daotests;


import be.chaouki.dao.CustomerDAO;
import be.chaouki.dao.CustomerDAO_JPA;
import be.chaouki.dao.DAOFactory;
import be.chaouki.entities.Customer;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * based on https://github.com/geowarin/hibernate-examples/tree/master/hibernate-jpa-standalone-dbunit
 *
 */
public class CustomerDAOTest extends AbstractDbUnitJpaTest {
    
    private static Logger log = LoggerFactory.getLogger(CustomerDAOTest.class);
    protected static CustomerDAO customerDAO;
    
    @Before
    public void init(){
        log.info("init() call");
        customerDAO=DAOFactory.getCustomerDAO(entityManager);
    }
    
    @Test
    public void testAdd(){
        Assert.assertEquals(2, customerDAO.findAll().size());
        
        Customer customer1= new Customer();
        customer1.setAdress("Rue Derp");
        customer1.setMunicipality("DerpVille");
        customer1.setName("Monsieur Derpidou");
        customer1.setPostalCode(435);
        customer1.setPhoneNumber("0477567778");
        
        customerDAO.add(customer1);
        
        Assert.assertEquals(3, customerDAO.findAll().size());
    }

    @Test
    public void testFind() {

            Customer customer = entityManager.find(Customer.class, 500L);
            Assert.assertNotNull(customer);
            Assert.assertEquals("customerTest", customer.getName());
    }

    @Test
    public void testInsert() {

            Customer newCustomer = new Customer();
            String customerName="newCustomer";
            newCustomer.setName(customerName);

            System.out.println("ID: "+newCustomer.getId());

            entityManager.getTransaction().begin();
            entityManager.persist(newCustomer);
            Long id = newCustomer.getId();
            entityManager.getTransaction().commit();

            Customer customer = entityManager.find(Customer.class, id);
            Assert.assertNotNull(customer);
            Assert.assertEquals(customerName, customer.getName());
    }

    @Test
    public void testFindAll() {

            List<Customer> customers = entityManager.createQuery("FROM Customer").getResultList();
            Assert.assertEquals(2, customers.size());
    }
    
        
    /*
        Customer customer1= new Customer();
        customer1.setAdress("Rue Derp");
        customer1.setMunicipality("DerpVille");
        customer1.setName("Monsieur Derpidou");
        customer1.setPostalCode(435);
        customer1.setPhoneNumber("0477567778");
        
        Customer customer2= new Customer();
        customer2.setAdress("Rue Derp");
        customer2.setMunicipality("DerpVille");
        customer2.setName("Monsieur Derpidou");
        customer2.setPostalCode(435);
        customer2.setPhoneNumber("0477567779");
    */
}
