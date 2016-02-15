package fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.daos.dao_classes;

import fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.crud.AbstractCrudService;
import fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.crud.QueryParameter;
import fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.daos.interfaces.IProjectDAO;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.ProjectNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Set;

/**
 * Created by sboudfor274 on 17/12/15.
 **/

public class ProjectDAO extends AbstractCrudService<Project> implements IProjectDAO {

    public ProjectDAO(EntityManager em) {
        super(em);
    }

    /********************************************************/

    @Override
    public Project createProject(Project project) {
        Project tmp = this.create(project);
        if(tmp==null)
            throw new PersistenceException();
        return tmp;
    }

    @Override
    public Project getProject(long projectId) {
        Project tmp = this.find(Project.class, projectId);
        if(tmp==null)
            throw new ProjectNotFoundException("Project not found");
        return tmp;
    }


    @Override
    public Project getProjectByNameAndOwnerId(String projTitle, long ownerId){
        List<Project> projects = findWithNamedQuery(Project.class, "Project.findProjectByNameAndOwnerId", QueryParameter.with("pTitle", projTitle).and("oId", ownerId));
        if( projects==null || projects.size()==0)
            throw new ProjectNotFoundException("Project not found");
        return projects.get(0);

    }

    /********************************************************/
    //// TODO (méthodes à faire)

    /**
     * Mise a jour du projet
     * @param project
     * @return
     */
    @Override
    public Project updateProjet(Project project) {
        return this.update(project);
    }

    @Override
    public void deleteProject(long projectId) {
         this.delete(Project.class, projectId);
    }

    @Override
    public void shareProject(User targetUser, Project project) {
        targetUser.addNewProjet(project);

    }

    @Override
    //TODO: Quelle difference avec get project ?
    public List<Project> getCreatedProjects(long currentUserId) {
        return null;
    }

    /**
     *  TODO: Cette fonction n'est elle pas censé etre dans UserBeanEJB ??
     * @param currentUser
     * @return
     */
    public Set<Project> getParticipations(User currentUser) {
        return currentUser.getMesProjets();
    }

    /**
     * Cette fonction retourne la liste des participant dans un projet(liste des collaborateurs)
     * @param project
     * @return
     */
    @Override
    public Set<User> getParticipants(Project project){
        return project.getParticipants();
    }



}



