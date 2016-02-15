package fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.daos.interfaces;

import fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.crud.ICrudService;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;

import java.util.List;

/**
 * Created by fdosso335 on 24/11/15.
 **/

public interface IUserDAO extends ICrudService<User> {


    public User createAccount(User currentUser);

    User findUserByMail(String targetUserEmail);

    public User updateAccount(User currentUser);

    public void deleteAccount(long accountId);


    public User getAccount(String email, String password);

    public User getAccount(long accountId);

    public List<User> getAllAccounts();

}
