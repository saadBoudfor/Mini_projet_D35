package fr.univtln.dosso_boudfor.mini_projet_d35.injectables;

import fr.univtln.dosso_boudfor.mini_projet_d35.annotations.ApplicationDatabase;
import fr.univtln.dosso_boudfor.mini_projet_d35.annotations.JPADAO;
import fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.daos.dao_classes.UserDAO;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by fdosso335 on 01/12/15.
 **/


@JPADAO
public class InjectableUserDAO extends UserDAO {

    @Inject
    public InjectableUserDAO(@ApplicationDatabase EntityManager entityManager) {
        super(entityManager);
    }

}
