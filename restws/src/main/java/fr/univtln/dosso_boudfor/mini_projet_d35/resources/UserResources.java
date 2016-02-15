package fr.univtln.dosso_boudfor.mini_projet_d35.resources;

import fr.univtln.dosso_boudfor.mini_projet_d35.annotations.Builder;
import fr.univtln.dosso_boudfor.mini_projet_d35.builders.UserBuilder;
import fr.univtln.dosso_boudfor.mini_projet_d35.data_generators.FullDataBase;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;
import fr.univtln.dosso_boudfor.mini_projet_d35.interfaces.IUserResources;
import fr.univtln.dosso_boudfor.mini_projet_d35.interfaces.locals.IUserBeanLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * * Created by yssouf on 24/12/15.
 **/

@Path("users")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResources implements IUserResources{

    @Inject
    @Builder
    private UserBuilder userBuilder;


    @Inject
    private IUserBeanLocal userBeanEJB;

    @Inject
    private FullDataBase fullDataBase;


    /********************************************************/

    @GET
    @Path("{id}")
    public User getUserById(@PathParam("id") final long userId) {
        return userBeanEJB.getAccount(userId);
    }


    @GET
    @Path("getAll")
    public List<User> getAllUsers() {
        return userBeanEJB.getAllAccounts();
    }

    /********************************************************/

    @POST
    public User createUser(User user) throws BuilderException {
        User tmp = userBuilder.init().setNom(user.getNom()).setPrenom(user.getPrenom()).setEmail(user.getEmail()).setPassword(user.getPassword()).build();
        userBeanEJB.createAccount(tmp);
        return tmp;
    }


    @GET
    @Path("logIn")
    public User logIn(@QueryParam("email") String email, @QueryParam("password") String password) {
        return userBeanEJB.logIn(email, password);
    }



    @GET
    @Path("test")
    public String toto() throws BuilderException {


        fullDataBase.createDatas(3, 5, 7);

        return "ok";
    }






}















