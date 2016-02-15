package fr.univtln.dosso_boudfor.mini_projet_d35.interfaces;

import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;

/**
 * Created by yssouf on 03/01/16.
 **/



public interface IProjectResources {

    Project getProjectById(final long projectId);

    Project createProject(String projectTitle, long owner_id) throws BuilderException;

}
