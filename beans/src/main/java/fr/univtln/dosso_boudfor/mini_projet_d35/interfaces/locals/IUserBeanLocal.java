package fr.univtln.dosso_boudfor.mini_projet_d35.interfaces.locals;

import fr.univtln.dosso_boudfor.mini_projet_d35.emails_senders.MailSender;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.injectables.InjectableUserDAO;

import java.util.List;

/**
 ** Created by sboudfor274 on 17/12/15.
 */


public interface IUserBeanLocal {


    InjectableUserDAO getUserDAO();

    MailSender getMailSender();

    User createAccount(User currentUser);

    User updateAccount(User currentUser);

    void deleteAccount(long accountId);

    User logIn(String email, String password);

    User getAccount(long accountId);

    List<User> getAllAccounts();



}


