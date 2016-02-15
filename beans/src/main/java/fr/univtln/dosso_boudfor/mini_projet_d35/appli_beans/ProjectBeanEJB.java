package fr.univtln.dosso_boudfor.mini_projet_d35.appli_beans;

import fr.univtln.dosso_boudfor.mini_projet_d35.annotations.Builder;
import fr.univtln.dosso_boudfor.mini_projet_d35.annotations.JPADAO;
import fr.univtln.dosso_boudfor.mini_projet_d35.builders.ProjectBuilder;
import fr.univtln.dosso_boudfor.mini_projet_d35.emails_senders.MailSender;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.enums.EmailServers;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.SharingException;
import fr.univtln.dosso_boudfor.mini_projet_d35.injectables.InjectableProjectDAO;
import fr.univtln.dosso_boudfor.mini_projet_d35.injectables.InjectableUserDAO;
import fr.univtln.dosso_boudfor.mini_projet_d35.interfaces.remotes.IProjectBeanRemote;
import fr.univtln.dosso_boudfor.mini_projet_d35.qualifiers.EmailSender;

import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/***
 ** Created by sboudfor274 on 17/12/15.
***/

@Stateless
@Named
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProjectBeanEJB implements IProjectBeanRemote, Serializable {

    @Inject
    @JPADAO
    private InjectableProjectDAO projetDAO;

    @Inject
    @JPADAO
    private InjectableUserDAO userDAO;

    @Inject
    @EmailSender(EmailServers.Gmail)
    MailSender mailSender;

    @Inject
    @Builder
    private ProjectBuilder projectBuilder;

    /********************************************************/

    @Override
    public Project createProject(String projectTitle, long owner_id) throws BuilderException {
        User owner = userDAO.getAccount(owner_id);
        Project tmp = projectBuilder.init().setTitre(projectTitle).setOwner(owner).build();

        tmp = projetDAO.createProject(tmp);

        //à voir: devait être fait automtiquement par jpa
        owner.addNewProjet(tmp);

        return tmp;
    }

    @Override
    public Project getProject(long projectId) {
        return projetDAO.getProject(projectId);
    }

    @Override
    public Project getProjectByNameAndOwnerId(String projTitle, long ownerId){
        return projetDAO.getProjectByNameAndOwnerId(projTitle, ownerId);
    }

    @Override
    public boolean shareProject(String targetUserEmail, Project project, String currentUserLastName, String currentUserFirstName) throws SharingException {

        User targetUser = userDAO.findUserByMail(targetUserEmail);

        if(targetUser == null)
            throw new SharingException("These is no account associated to this email");

        projetDAO.shareProject(targetUser, project);


        String mssgText = "Hello " + targetUser.getPrenom() + ",\n\n";
        mssgText += currentUserFirstName + " " + currentUserLastName + "had add you to his project: " + project.getTitre() + ".\n\n" ;
        mssgText += "Best regards.";

        mailSender.sendMessage(targetUserEmail, "Sharing notif.", mssgText);

        return true;
    }

    @Override
    public List<Project> getCreatedProjects(long currentUserId) {
        return projetDAO.getCreatedProjects(currentUserId);
    }



    /********************************************************/
    //// TODO (méthodes à faire)


    @Override
    public Set<Project> getParticipations(User currentUser) {
        return projetDAO.getParticipations(currentUser);
    }

    @Override
    public Project updateProjet(Project project) {
        return projetDAO.updateProjet(project);
    }

    @Override
    public void deleteProject(long projectId) {
        projetDAO.deleteProject(projectId);
    }


    public Set<User> getParticipants(Project project) {
        return project.getParticipants();
    }



    }














