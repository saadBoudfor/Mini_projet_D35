package fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.daos.dao_classes;

import fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.crud.AbstractCrudService;
import fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.crud.QueryParameter;
import fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.daos.interfaces.IUserDAO;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.AccountNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by fdosso335 on 24/11/15.
 **/

public class UserDAO extends AbstractCrudService<User> implements IUserDAO {


    public UserDAO(EntityManager em) {
        super(em);
    }


    @Override
    public User createAccount(User currentUser) {
        User tmp = this.create(currentUser);
        if(tmp==null)
            throw new PersistenceException();
        return tmp;
    }



    @Override
    public User getAccount(String email, String password) {
        List<User> result = findWithNamedQuery(User.class,"User.connection", QueryParameter.with("uEmail", email).and("uPassword", password));

        if(result==null || result.size()==0)
            throw new AccountNotFoundException("Account not found");

        return result.get(0);
    }

    @Override
    public User getAccount(long accountId) {
        User tmp = this.find(User.class, accountId);
        if(tmp==null)
            throw new AccountNotFoundException("Account not found");
        return tmp;
    }

    @Override
    public List<User> getAllAccounts(){
        return findWithNamedQuery(User.class, "User.findAll");
    }


    @Override
    public User findUserByMail(String targetUserEmail){
        List<User> result = findWithNamedQuery(User.class,"User.findUserByMail", QueryParameter.with("uEmail", targetUserEmail));

        if(result==null || result.size()==0)
           return null;

        return result.get(0);
    }




    @Override
    public User updateAccount(User currentUser) {
        return null;
    }

    @Override
    public void deleteAccount(long accountId) {

    }




}
