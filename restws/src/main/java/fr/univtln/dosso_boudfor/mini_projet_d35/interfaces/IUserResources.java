package fr.univtln.dosso_boudfor.mini_projet_d35.interfaces;

import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;

import java.util.List;

/**
 * Created by yssouf on 03/01/16.
 **/



public interface IUserResources {

    User getUserById(final long userId);

    List<User> getAllUsers();

    User createUser(User user) throws BuilderException;

    User logIn(String email, String password);

}
