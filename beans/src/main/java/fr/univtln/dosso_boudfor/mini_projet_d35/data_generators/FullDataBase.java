package fr.univtln.dosso_boudfor.mini_projet_d35.data_generators;

import fr.univtln.dosso_boudfor.mini_projet_d35.appli_beans.UserBeanEJB;
import fr.univtln.dosso_boudfor.mini_projet_d35.builders.ProjectBuilder;
import fr.univtln.dosso_boudfor.mini_projet_d35.builders.TaskBuilder;
import fr.univtln.dosso_boudfor.mini_projet_d35.builders.UserBuilder;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Task;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.enums.Priority;
import fr.univtln.dosso_boudfor.mini_projet_d35.enums.Status;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Util on 16/01/2016.
 **/


/*
  *  This class creates 5 users with 7 projects respectively. Each project also contains 9 tasks.
 */
public class FullDataBase implements Serializable{

    private List<User> users = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();


    @Inject
    private UserBeanEJB userBeanEJB;


    /********************************************************/

    public FullDataBase() {
    }

    /********************************************************/

    public List<User> getUsers() {
        return users;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    private Calendar cal = Calendar.getInstance(); // creates calendar

    /********************************************************/



    public void createDatas(User user, int nbProjects, int nbTasks) throws BuilderException {
        ProjectBuilder projectBuilder = new ProjectBuilder();
        TaskBuilder taskBuilder = new TaskBuilder();

        Project tmpProject;
        Task tmpTask, tmpTask2, tmpTask3;

        for(int j=0; j<nbProjects; j++){
            tmpProject = projectBuilder.init().setTitre("projet"+(j+1)).setOwner(user).build();
            projects.add(tmpProject);
            user.addNewProjet(tmpProject);

            for(int k=0; k<nbTasks; k++){
                tmpTask = taskBuilder.init().setTitre("task"+(k+1)+"_p"+(j+1)).setContenue("to edit").setPriority(Priority.AUCUNNE).setStatus(Status.EN_ATTENTE).setParent(tmpProject).setDelaisEcheance(addHour(cal,1)).build();
                tmpTask2 = taskBuilder.init().setTitre("task"+(k+2)+"_p"+(j+2)).setContenue("to edit").setPriority(Priority.FAIBLE).setStatus(Status.EN_COURS).setParent(tmpProject).setDelaisEcheance(addHour(cal,2)).build();
                tmpTask3 = taskBuilder.init().setTitre("task"+(k+3)+"_p"+(j+3)).setContenue("to edit").setPriority(Priority.FORTE).setStatus(Status.EFFECTUEE).setParent(tmpProject).setDelaisEcheance(addHour(cal,3)).build();

                tasks.add(tmpTask);
                tasks.add(tmpTask2);
                tasks.add(tmpTask3);

                tmpProject.addTask(tmpTask);
                tmpProject.addTask(tmpTask2);
                tmpProject.addTask(tmpTask3);

            }
        }

        userBeanEJB.createAccount(user);

    }





    public Date addHour(Calendar cal, int nbr){
        cal.setTime(new Date()); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, nbr); // adds one hour
        return cal.getTime(); // returns new date object, one hour in the future
    }
}