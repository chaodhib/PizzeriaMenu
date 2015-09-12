package be.chaouki.daotests;

import be.chaouki.dao.CustomerDAO;
import be.chaouki.dao.DAOFactory;
import java.io.InputStream;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.HibernateException;
import org.hibernate.internal.SessionImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * based on https://github.com/geowarin/hibernate-examples/tree/master/hibernate-jpa-standalone-dbunit
 *
 */
public abstract class AbstractDbUnitJpaTest {
    
    private static Logger log = LoggerFactory.getLogger(AbstractDbUnitJpaTest.class);

	private static EntityManagerFactory entityManagerFactory;
	protected static EntityManager entityManager;
        
	private static IDatabaseConnection connection;
	private static IDataSet dataset;

	/**
	 * Will load test-dataset.xml before each test case
	 * @throws DatabaseUnitException 
	 * @throws HibernateException 
	 */
	@BeforeClass
	public static void initEntityManager() throws HibernateException, DatabaseUnitException {
		entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
//		entityManagerFactory = Persistence.createEntityManagerFactory("persistence-test");
		entityManager = entityManagerFactory.createEntityManager();
		connection = new DatabaseConnection(((SessionImpl) (entityManager.getDelegate())).connection());
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());

		FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
		flatXmlDataSetBuilder.setColumnSensing(true);
		InputStream dataSet = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-data.xml");
		dataset = flatXmlDataSetBuilder.build(dataSet);
	}

	@AfterClass
	public static void closeEntityManager() {
		entityManager.close();
		entityManagerFactory.close();
	}
	
	/**
	 * Will clean the database and restore initial DB state before each test
	 * 
	 * @throws SQLException 
	 * @throws DatabaseUnitException 
	 */
	@Before
	public void cleanDB() throws DatabaseUnitException, SQLException {
            log.info("cleanDB() call");
            entityManager.getTransaction().begin();
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);
//            DatabaseOperation.DELETE_ALL.execute(connection, dataset);
            entityManager.getTransaction().commit();
            
	}
}
