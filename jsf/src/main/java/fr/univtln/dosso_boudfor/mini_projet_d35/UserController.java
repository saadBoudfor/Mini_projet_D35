package fr.univtln.dosso_boudfor.mini_projet_d35;

import fr.univtln.dosso_boudfor.mini_projet_d35.appli_beans.ProjectBeanEJB;
import fr.univtln.dosso_boudfor.mini_projet_d35.appli_beans.TaskBeanEJB;
import fr.univtln.dosso_boudfor.mini_projet_d35.appli_beans.UserBeanEJB;
import fr.univtln.dosso_boudfor.mini_projet_d35.data_generators.FullDataBase;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Task;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.enums.Priority;
import fr.univtln.dosso_boudfor.mini_projet_d35.enums.Status;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.AccountNotFoundException;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;
import fr.univtln.dosso_boudfor.mini_projet_d35.utils.TaskFilter;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * ** Cette classe permet d'interagir avec la couche EJB et d'éffectuer des validations.
 **/

@Named
//@RequestScoped   //c'est de toute façon la portée par défaut d'un objet géré par FaceDervlet
@SessionScoped
public class UserController implements Serializable {
    private static final Logger log = Logger.getLogger(ScheduleView.class.getName());


    @Inject
    private transient UserBeanEJB utilisateurBean;

    @Inject
    private transient FullDataBase fullDataBase;

    public String getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(String selectedProject) {
        this.selectedProject = selectedProject;
    }

    @Inject
    private transient TaskBeanEJB taskBeanEJB;

    @Inject
    private transient ProjectBeanEJB projectBeanEJB;

    public Long getUserId() {
        return utilisateur.getId();
    }


    public List<Task> getTaskByPriority(int priority) {
        List<Task> tasks = new ArrayList<>();
        TaskFilter taskFilter = new TaskFilter(utilisateur.getId());
        switch (priority) {
            case 0:
                taskFilter.setPriority(Priority.AUCUNNE);
                break;

            case 1:
                taskFilter.setPriority(Priority.FAIBLE);
                break;

            case 2:
                taskFilter.setPriority(Priority.MOYENNE);
                break;

            case 3:
                taskFilter.setPriority(Priority.FORTE);
                break;
        }

        return taskBeanEJB.getTasksByFilter(taskFilter);
    }

    String selectedProject;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    String Date;

    public TaskBeanEJB getTaskBeanEJB() {
        return taskBeanEJB;
    }

    public void setTaskBeanEJB(TaskBeanEJB taskBeanEJB) {
        this.taskBeanEJB = taskBeanEJB;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    User utilisateur = new User();
    Project project = new Project();
    Task task = new Task();

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    private List<Task> allTask;

    public UserController() {

    }

    public User getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(User utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String createAccount() throws BuilderException {
        fullDataBase.createDatas(utilisateur, 3, 2);
        //utilisateur = utilisateurBean.createAccount(utilisateur);
        return "main.faces";
    }

    public String connect(String email, String password) {
        try {
            utilisateur = utilisateurBean.logIn(email, password);
            return "main.faces";
        } catch (AccountNotFoundException e) {
            addMessage("Le mot de passe est incorrect !");
            return "main.faces";
        }
    }


    public String createTask() throws BuilderException, ParseException {

        project = projectBeanEJB.getProjectByNameAndOwnerId(selectedProject, utilisateur.getId());
        utilisateur.addNewProjet(project);

        project.addTask(task);
        taskBeanEJB.createTask(task, project.getId());

        return "main.faces";
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<Task> getTasksNotExpiredByUser() {
        return taskBeanEJB.getTasksNotExpiredByUser(utilisateur.getId());
    }

    public Status[] getStatuses() {
        return Status.values();
    }

    public Priority[] getPriorities() {
        return Priority.values();
    }

    public List<String> getProjectsTitles() {
        List<String> stringList = new ArrayList<>();

        for (Project project : utilisateur.getMesProjets()) {
            stringList.add(project.getTitre());
        }
        return stringList;
    }


    public int getNotProgressPourcentage(long projectId) {
        return 100 - getProgressPourcentage(projectId);
    }

    public int getTaskDone(long projectId) {

        List<Task> allTasks = taskBeanEJB.getAllTasksByProject(projectId);
        int size = 0;
        for (Task task : allTasks) {
            if (task.getStatus() == Status.EFFECTUEE) {
                size++;
            }
        }
        return size;
    }

    public int getTaskNotDone(long projectId) {
        int tmp = projectBeanEJB.getProject(projectId).getTaches().size();
        return tmp - getTaskDone(projectId);
    }

    public int getProgressPourcentage(long projectId) {

        List<Task> allTasks = taskBeanEJB.getAllTasksByProject(projectId);
        int size = getTaskDone(projectId);
        return (size * 100 / allTasks.size());
    }


    public String getBarProgresseStyle(int prc) {
        String styleClass = "progress-bar progress-bar-danger";
        if ((30 < prc) && (prc < 65)) {
            styleClass = "progress-bar progress-bar-warning";
        }
        if (prc > 65) {
            styleClass = "progress-bar progress-bar-success";
        }

        return styleClass;
    }

    public String getStyleClasseForPriority(Long id) {
        String styleClass = "badge bg-primary";
        Task task = taskBeanEJB.getTaskById(id);
        if (task.getPriority() == Priority.FAIBLE) {
            styleClass = "badge bg-success";
        }
        if (task.getPriority() == Priority.MOYENNE) {
            styleClass = "badge bg-warning";
        }
        if (task.getPriority() == Priority.FORTE) {
            styleClass = "badge bg-important";
        }

        return styleClass;
    }

    public String getStyleClasseForStatu(long id) {
        String styleClass = "badge bg-primary";
        Task task = taskBeanEJB.getTaskById(id);
        if (task.getStatus() == Status.EFFECTUEE) {
            styleClass = "badge bg-success";
        }
        if (task.getStatus() == Status.EN_ATTENTE) {
            styleClass = "badge bg-warning";
        }

        return styleClass;
    }

    public int getcountProject(int test) {
        return utilisateur.getMesProjets().size();
    }

    public int getNbrParticipant(long id) {
        return projectBeanEJB.getProject(id).getParticipants().size();
    }

    /**
     * Cette fonction permet de créer un projet saisi par l'utilisateurs.
     * @return
     * @throws BuilderException
     */
    public String createProject() throws BuilderException {
        project = projectBeanEJB.createProject(project.getTitre(), utilisateur.getId());
        return "main.faces";
    }


    //********************************Cas d'usage: modifier - supprimer - plus d'infos taches *********************************//

    /**
     * Cette fonction renvoie l'id de la tache selectionné par l'utilisateur.
     * @param taskId
     * @return
     */
    public long getSelectedTaskId(long taskId){

        return taskId;
    }

    // Tache selectionné par l'utilisateur
    private Task selectedTask;

    public Task getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {

        this.selectedTask = selectedTask;
    }

}

