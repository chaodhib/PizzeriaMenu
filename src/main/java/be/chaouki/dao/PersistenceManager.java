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
    
    // the EntityManagerFactory "emFactory" is lazy loaded. This method forces its loading when called.
    public static void loadFactory(){
        ; 
    }
    
    public static EntityManager getInstanceEM() {
        return emFactory.createEntityManager();
    }
    
    public static void close(){
        if(emFactory.isOpen())
            emFactory.close();
    }
}