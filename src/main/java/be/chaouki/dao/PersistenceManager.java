/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.chaouki.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author chaouki
 */
//public enum PersistenceManager {
//
//  INSTANCE;
//
//  private EntityManagerFactory emFactory;
//
//  private PersistenceManager() {
//    // persistence-unit element.
//    emFactory = Persistence.createEntityManagerFactory("persistence");
//  }
//
//  public EntityManager getEntityManager() {
//    return emFactory.createEntityManager();
//  }
//
//  public void close() {
//    emFactory.close();
//  }
//}

public class PersistenceManager{
    
    private static final EntityManagerFactory emFactory=Persistence.createEntityManagerFactory("persistence");
    private static final EntityManager em=emFactory.createEntityManager();
    
    // the factory and the entity manager are lazy loaded. This method forces their loading by the class loader when called.
    public static void load(){
        ; // intentionaly empty
    }
    
    public static EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }
    
    public static void close(){
        if(em.isOpen())
            em.close();
        if(emFactory.isOpen())
            emFactory.close();
    }
}