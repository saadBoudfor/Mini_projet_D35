package fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.daos.interfaces;

import fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.crud.ICrudService;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Task;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.utils.TaskFilter;

import java.util.List;

/**
 * Created by sboudfor274 on 17/12/15.
 **/


public interface ITaskDAO extends ICrudService<Task> {


    Task createTask(Task task);

    void deleteTask(long taskId, Project associatedProject, User currentUser);


    boolean assignTask(String targetUserEmail, Task task, Project associatedProject, User currentUser);

    boolean labelTask(String labelText, Task task, User currentUser);


    //// Changement de priorité et autres
    Task updateTask(Task task, Project associatedProject, User currentUser);


    Task getTaskById(long taskId);


    List<Task> getAllTasksByProject(long projectId);


    List<Task> getAllTasks();


    List<Task> getTasksNotExpiredByProject(long projectId);

    List<Task> getTasksNotExpiredByUser(long user_id);

    Task getTasksByLabel(String labelText, long labelOwnerId);


    List getTasksByFilter(TaskFilter taskFilter);




}
