package fr.univtln.dosso_boudfor.mini_projet_d35.builders;

import fr.univtln.dosso_boudfor.mini_projet_d35.annotations.Builder;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;

import java.util.Date;


/**
 * * Created by yssouf on 24/12/15.
 */

@Builder
public class ProjectBuilder implements IBuilder<ProjectBuilder, Project>{

    private String titre;
    private User owner;

    public ProjectBuilder() {
    }

    @Override
    public ProjectBuilder init() {
        this.titre = null;
        this.owner = null;
        return this;
    }

    @Override
    public Project build() throws BuilderException {

        if(titre==null ||  owner==null)
            throw new BuilderException("Au moins un champ obligatoire est null");

        Date date = new Date();

        return new Project(titre, date, owner);

    }


    public ProjectBuilder setTitre(String titre) {
        this.titre = titre;
        return this;
    }


    public ProjectBuilder setOwner(User owner) {
        this.owner = owner;
        return this;
    }


}
