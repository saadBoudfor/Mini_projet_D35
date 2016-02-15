package fr.univtln.dosso_boudfor.mini_projet_d35.interfaces.remotes;

import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;

/**
 ** Created by sboudfor274 on 17/12/15.
 */


public interface IUserBeanRemote {


    public User createAccount(User currentUser);

    public User updateAccount(User currentUser);

    public void deleteAccount(long accountId);


    public User logIn(String email, String password);





}


