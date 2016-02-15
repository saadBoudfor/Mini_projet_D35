package fr.univtln.dosso_boudfor.mini_projet_d35;

import fr.univtln.dosso_boudfor.mini_projet_d35.appli_beans.TaskBeanEJB;
import fr.univtln.dosso_boudfor.mini_projet_d35.builders.TaskBuilder;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Task;
import fr.univtln.dosso_boudfor.mini_projet_d35.enums.Priority;
import fr.univtln.dosso_boudfor.mini_projet_d35.enums.Status;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sboudfor on 21/01/2016.
 */
@Named
@RequestScoped
public class TaskController implements Serializable {
    private static final Logger log = Logger.getLogger(ScheduleView.class.getName());

    private  String titre;
    private String contenue;
    private String date;
    private String priorite;
    private String statu;
    private Project project;

    @Inject
    private transient TaskBeanEJB taskBeanEJB;
    private Task task = new Task();

    public Date dateFromString(String dateInString){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

        Date date = null;
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    public TaskController() {
    }

    public TaskController(String titre, String contenue, String date, String priorite, String statu, Project project) {
        this.titre = titre;
        this.contenue = contenue;
        this.date = date;
        this.priorite = priorite;
        this.statu = statu;
        this.project = project;
    }

    public Task newTask() throws BuilderException {
        TaskBuilder tk = new TaskBuilder();
        return tk.init().setTitre(titre).setContenue(contenue).setParent(project).setDelaisEcheance(dateFromString(date)).setPriority(Priority.FAIBLE).setStatus(Status.EN_COURS).build();
    }



    // Tache selectionné par l'utilisateur
    private Task selectedTask;

    public Task getSelectedTask() {
        //log.log( Level.SEVERE, "***** ****** ****** Task ID, getSelected : " + selectedTask.getTitre() );
         return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }

    public String getTaskInfo(long id){
        //log.log( Level.SEVERE, "***** ****** ****** Task ID, getInfo: " + selectedTask.getTitre() );
        task = taskBeanEJB.getTaskById(id);
        return "testCalendar.faces";
    }

}
