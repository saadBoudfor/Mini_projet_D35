package fr.univtln.dosso_boudfor.mini_projet_d35.producers;

import fr.univtln.dosso_boudfor.mini_projet_d35.annotations.ApplicationDatabase;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by fdosso335 on 01/12/15.
 **/


public class DatabaseProducer {

    @Produces
    @PersistenceContext(unitName = "appli_db")
    @ApplicationDatabase
    private EntityManager em;


//    public void close(@Disposes @ApplicationDatabase EntityManager em) {
//        em.close();
//    }

}
