package fr.univtln.dosso_boudfor.mini_projet_d35.interfaces.remotes;

import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Task;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BadDateException;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;
import fr.univtln.dosso_boudfor.mini_projet_d35.utils.TaskFilter;

import javax.ejb.Remote;
import java.util.Date;
import java.util.List;

/**
 * Created by sboudfor274 on 17/12/15.
 **/


@Remote
public interface ITaskBeanRemote {

    Task createTask(Task task, long parent_id) throws BuilderException;

    void deleteTask(long taskId, Project associatedProject, User currentUser);

    boolean assignTask(String targetUserEmail, Task task, Project associatedProject, User currentUser);

    boolean labelTask(String labelText, Task task, User currentUser);

    //// Changement de priorité et autres
    Task updateTask(Task task, Project associatedProject, User currentUser);

    void updateTaskDeadline(long task_id, Date deadlineDate) throws BadDateException;

    Task getTaskById(long taskId);

    Task getTasksByLabel(String labelText, long labelOwnerId);

    List<Task> getTasksNotExpiredByProject(long projectId);

    List<Task> getAllTasksByProject(long project_id);

    List<Task> getTasksNotExpiredByUser(long user_id);

    List<Task> getTasksByFilter(TaskFilter taskFilter);

}



