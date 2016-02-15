package fr.univtln.dosso_boudfor.mini_projet_d35.interfaces.remotes;

import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.SharingException;

import javax.ejb.Remote;
import java.util.List;
import java.util.Set;

/**
 * Created by sboudfor274 on 17/12/15.
 **/


@Remote
public interface IProjectBeanRemote {


    public Project createProject(String projectTitle, long owner_id) throws BuilderException;

    Project getProjectByNameAndOwnerId(String projName, long ownerId);

    public Project updateProjet(Project project);

    public void deleteProject(long projectId);



    public boolean shareProject(String targetUserEmail, Project project, String currentUserLastName, String currentUserFirstName) throws SharingException;


    // Renvoie la liste des projets crées par l'utilisateur
    public List<Project> getCreatedProjects(long currentUserId);

    // Renvoie la liste des projets aux quels participe l'utilisateur
    public Set<Project> getParticipations(User currentUser);

    public Project getProject(long projectId);






}




