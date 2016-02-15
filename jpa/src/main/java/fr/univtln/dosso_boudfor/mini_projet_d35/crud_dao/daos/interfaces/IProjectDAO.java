package fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.daos.interfaces;

import fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.crud.ICrudService;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;

import java.util.List;
import java.util.Set;

/**
 * Created by sboudfor274 on 17/12/15.
 **/


public interface IProjectDAO extends ICrudService<Project> {

    Project createProject(Project project);

    Project getProjectByNameAndOwnerId(String projName, long ownerId);

    Project updateProjet(Project project);

    void deleteProject(long projectId);



    void shareProject(User targetUser, Project project);

    // Renvoie la liste des projets crées par l'utilisateur
    List<Project> getCreatedProjects(long currentUserId);

    // Renvoie la liste des projets aux quels participe l'utilisateur
    Set<Project> getParticipations(User currentUser);

    Project getProject(long projectId);
    public Set<User> getParticipants(Project project);



    }
