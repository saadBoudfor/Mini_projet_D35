package fr.univtln.dosso_boudfor.mini_projet_d35.appli_beans;

import fr.univtln.dosso_boudfor.mini_projet_d35.annotations.JPADAO;
import fr.univtln.dosso_boudfor.mini_projet_d35.emails_senders.MailSender;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.enums.EmailServers;
import fr.univtln.dosso_boudfor.mini_projet_d35.injectables.InjectableUserDAO;
import fr.univtln.dosso_boudfor.mini_projet_d35.interfaces.locals.IUserBeanLocal;
import fr.univtln.dosso_boudfor.mini_projet_d35.qualifiers.EmailSender;

import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by sboudfor274 on 17/12/15.
 **/


@Stateless
@Named
@LocalBean
@Local(IUserBeanLocal.class)
//@Remote(IUserBeanRemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserBeanEJB implements IUserBeanLocal, Serializable{
//public class UserBeanEJB implements IUserBeanLocal, IUserBeanRemote, Serializable{

    @Inject
    @JPADAO
    private InjectableUserDAO userDAO;

    @Inject
    @EmailSender(EmailServers.Gmail)
    MailSender mailSender;


    /********************************************************/

    @Override
    public InjectableUserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public MailSender getMailSender() {
        return mailSender;
    }

    /********************************************************/

    @Override
    public User createAccount(User currentUser) {
        User newUser = userDAO.createAccount(currentUser);

        String mssgText = "Welcome " + newUser.getPrenom() + ",\n\n";
        mssgText += "We are happy to see you amongst the users of our application, ";
        mssgText += "we hope you will enjoy it.\n\n";
        mssgText += "Best regards.";

        mailSender.sendMessage(newUser.getEmail(), "Welcome", mssgText);

        return newUser;
    }


    /********************************************************/


    @Override
    public User getAccount(long accountId) {
        return userDAO.getAccount(accountId);
    }

    @Override
    public List<User> getAllAccounts() {
        return userDAO.getAllAccounts();
    }


    //// TODO (à terminer): correspond à la connection, donc: authent, sécu et autres...
    @Override
    public User logIn(String email, String password) {
        return userDAO.getAccount(email, password);
    }


    /********************************************************/
    //// TODO : ces methodes sont à terminer

    @Override
    public User updateAccount(User currentUser) {
        return userDAO.updateAccount(currentUser);
    }

    @Override
    public void deleteAccount(long accountId) {
        userDAO.deleteAccount(accountId);
    }




}




