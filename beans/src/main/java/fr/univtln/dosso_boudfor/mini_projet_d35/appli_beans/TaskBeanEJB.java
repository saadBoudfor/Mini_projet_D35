package fr.univtln.dosso_boudfor.mini_projet_d35.appli_beans;

import fr.univtln.dosso_boudfor.mini_projet_d35.annotations.Builder;
import fr.univtln.dosso_boudfor.mini_projet_d35.annotations.JPADAO;
import fr.univtln.dosso_boudfor.mini_projet_d35.builders.TaskBuilder;
import fr.univtln.dosso_boudfor.mini_projet_d35.emails_senders.MailSender;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Task;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.enums.EmailServers;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BadDateException;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;
import fr.univtln.dosso_boudfor.mini_projet_d35.injectables.InjectableProjectDAO;
import fr.univtln.dosso_boudfor.mini_projet_d35.injectables.InjectableTaskDAO;
import fr.univtln.dosso_boudfor.mini_projet_d35.interfaces.remotes.ITaskBeanRemote;
import fr.univtln.dosso_boudfor.mini_projet_d35.qualifiers.EmailSender;
import fr.univtln.dosso_boudfor.mini_projet_d35.utils.TaskFilter;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.ejb.Timer;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * Created by sboudfor274 on 17/12/15.
 **/


@Stateless
@Named
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TaskBeanEJB implements ITaskBeanRemote, Serializable {

    @Inject
    @JPADAO
    private InjectableTaskDAO tacheDAO;

    @Inject
    @JPADAO
    private InjectableProjectDAO projectDAO;

    @Inject
    @Builder
    private TaskBuilder taskBuilder;


    @Resource
    TimerService timerService;

    @Inject
    @EmailSender(EmailServers.Gmail)
    MailSender mailSender;


    /********************************************************/

    @Timeout
    public void sendReminder(Timer timer){
        Task task = (Task) timer.getInfo();
        Project taskParent = task.getParent();
        User taskOwner = taskParent.getOwner();

        String mssgText = "Hello ";
        User manager = task.getManager();

        if(manager != null){
            mssgText += manager.getPrenom() +  ",\n\n";
            mssgText += "This message is to remind you that the task: " + task.getTitre();
            mssgText += " from the project: " + taskParent.getTitre() + ", which was assigned to you by ";
            mssgText += taskOwner.getPrenom() +  ", expires in 1 day.\n\n";
            mssgText += "Best regards.";
            mailSender.sendMessage(manager.getEmail(), "Reminder", mssgText);
        }else {
            mssgText += taskOwner.getPrenom() +  ",\n\n";
            mssgText += "This message is to remind you that the task: " + task.getTitre();
            mssgText += " from your project: " + taskParent.getTitre() + " expires in 1 day.\n\n";
            mssgText += "Best regards.";
            mailSender.sendMessage(taskOwner.getEmail(), "Reminder", mssgText);
        }
    }

    //On envoie un rappel un jour avant la date d'échéance, si celle-ci existe.
    public void createTimer(Task task){

        if(task.getDelaisEcheance() != null){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(task.getDelaisEcheance());

            ScheduleExpression reminderSchedule = new ScheduleExpression();

            // Correnpond au 1er janvier de l'année
            if(calendar.get(Calendar.MONTH) == Calendar.JANUARY && calendar.get(Calendar.DAY_OF_MONTH) == 1){
                reminderSchedule.year(calendar.get(Calendar.YEAR) -1);
                reminderSchedule.month("Dec");
                reminderSchedule.dayOfMonth("Last");
            }
            else {
                reminderSchedule.year(calendar.get(Calendar.YEAR));
                if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {  // Correspond au premier d'un mois (autre que Janvier)
                    // On enrégistre le dernier jour du mois précedent.
                    reminderSchedule.month(calendar.get(Calendar.MONTH)).dayOfMonth("Last");
                } else
                    reminderSchedule.month(calendar.get(Calendar.MONTH) + 1).dayOfMonth(calendar.get(Calendar.DAY_OF_MONTH) - 1);
            }

            timerService.createCalendarTimer(reminderSchedule, new TimerConfig(task, true));
        }

    }

    /********************************************************/

    @Override
    public Task createTask(Task task, long parent_id) throws BuilderException {

        Project parent = projectDAO.getProject(parent_id);

        Task result = taskBuilder.init()
                .setTitre(task.getTitre())
                .setContenue(task.getContenue())
                .setPriority(task.getPriority())
                .setStatus(task.getStatus())
                .setDelaisEcheance(task.getDelaisEcheance())
                .setParent(parent)
                .build();


        result = tacheDAO.create(result);

        //à voir: devait être fait automtiquement par jpa
        parent.addTask(result);

        this.createTimer(result);

        return result;
    }


    /********************************************************/

    @Override
    public Task getTaskById(long taskId) {
        return tacheDAO.getTaskById(taskId);
    }



    @Override
    public List<Task> getTasksNotExpiredByProject(long projectId){
        return tacheDAO.getTasksNotExpiredByProject(projectId);
    }

    @Override
    public List<Task> getAllTasksByProject(long project_id) {
        return tacheDAO.getAllTasksByProject(project_id);
    }

    @Override
    public List<Task> getTasksNotExpiredByUser(long user_id){
        return tacheDAO.getTasksNotExpiredByUser(user_id);
    }


    /********************************************************/

    // Supprime l'ancien timer, s'il existe, avant de  mettre le nouveau.
    @Override
    public void updateTaskDeadline(long taskId, Date deadlineDate) throws BadDateException {
        if(deadlineDate == null)
            throw new BadDateException("No date received");
        Task tmp = tacheDAO.getTaskById(taskId);
        tmp.setDelaisEcheance(deadlineDate);

        List<Timer> timers = new ArrayList<>(timerService.getTimers());

        for(Timer timer: timers){
            if((((Task) timer.getInfo()).getId()) == tmp.getId()){
                timer.cancel();
                break;
            }
        }


        this.createTimer(tmp);
    }


    @Override
    public List<Task> getTasksByFilter(TaskFilter taskFilter) {
        return tacheDAO.getTasksByFilter(taskFilter);
    }

    //// TODO (méthodes à faire)

    @Override
    public boolean assignTask(String targetUserEmail, Task task, Project associatedProject, User currentUser) {
        // Etape I: recuperer le projet auquel appartient assignerTache

        // Etape II: verifier que le projet et bien partagé avec l'utilisateur

        // Etape III: Si oui ajouter la task dans la liste des taches assignées à l'utilisateur.
        return tacheDAO.assignTask(targetUserEmail, task, associatedProject, currentUser);
    }


    @Override
    public boolean labelTask(String labelText, Task task, User currentUser) {
        return tacheDAO.labelTask(labelText, task, currentUser);
    }



    @Override
    public Task getTasksByLabel(String labelText, long labelOwnerId) {
        return tacheDAO.getTasksByLabel(labelText, labelOwnerId);
    }


    @Override
    public Task updateTask(Task task, Project associatedProject, User currentUser) {
        return tacheDAO.updateTask(task, associatedProject, currentUser);
    }

    @Override
    public void deleteTask(long taskId, Project associatedProject, User currentUser) {
        tacheDAO.deleteTask(taskId, associatedProject, currentUser);
    }



}
