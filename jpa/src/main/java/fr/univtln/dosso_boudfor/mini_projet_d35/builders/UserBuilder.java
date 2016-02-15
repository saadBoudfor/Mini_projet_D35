package fr.univtln.dosso_boudfor.mini_projet_d35.builders;

import fr.univtln.dosso_boudfor.mini_projet_d35.annotations.Builder;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;

/**
 * * Created by yssouf on 24/12/15.
 */

@Builder
public class UserBuilder implements IBuilder<UserBuilder, User>{

    private String nom;
    private String prenom;
    private String email;
    private String password;

    public UserBuilder() {
    }

    @Override
    public UserBuilder init() {
        this.nom = null;
        this.prenom = null;
        this.email = null;
        this.password = null;
        return this;
    }

    @Override
    public User build() throws BuilderException {

        if(nom==null || prenom==null || email==null || password==null)
            throw new BuilderException("Au moins un champ obligatoire est null");

        return new User(nom, prenom, email, password);

    }


    public UserBuilder setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public UserBuilder setPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

}
