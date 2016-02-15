package fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.daos.dao_classes;

import fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.crud.AbstractCrudService;
import fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.crud.QueryParameter;
import fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.daos.interfaces.ITaskDAO;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Task;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.User;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.TaskNotFoundException;
import fr.univtln.dosso_boudfor.mini_projet_d35.utils.TaskFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sboudfor274 on 17/12/15.
 **/


public class TaskDAO extends AbstractCrudService<Task> implements ITaskDAO {

    public TaskDAO(EntityManager em) {
        super(em);
    }


    /********************************************************/

    @Override
    public Task createTask(Task task) {
        Task tmp = this.create(task);
        if(tmp == null)
            throw  new PersistenceException();
        return tmp;
    }

    /********************************************************/

    @Override
    public List<Task> getAllTasks(){
        return findWithNamedQuery(Task.class, "Task.findAll");
    }

    @Override
    public Task getTaskById(long taskId) {
        Task result = this.find(Task.class, taskId);
        if(result == null)
            throw new TaskNotFoundException("Task not found");
        return result;
    }

    @Override
    public List<Task> getAllTasksByProject(long projectId){
        return findWithNamedQuery(Task.class, "Task.findAllByProject", QueryParameter.with("pId", projectId));
    }

    @Override
    public List<Task> getTasksNotExpiredByProject(long projectId){
        return findWithNamedQuery(Task.class, "Task.findNotExpiredByProject", QueryParameter.with("tDate", new Date()).and("pId", projectId));
    }

    @Override
    public List<Task> getTasksNotExpiredByUser(long user_id){
        return findWithNamedQuery(Task.class, "Task.findNotExpiredByUser", QueryParameter.with("tDate", new Date()).and("oId", user_id));
    }


    /********************************************************/

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> getTasksByFilter(TaskFilter taskFilter) {
        String request = taskFilter.buildQuery();
        Set<Map.Entry<String, Object>> parameters = taskFilter.getQueryParameters();

        getEm().joinTransaction();

        Query query = getEm().createQuery(request, Task.class);

        for (Map.Entry entry : parameters) {
            query.setParameter((String) entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }

    /********************************************************/
    ////TODO



    @Override
    public boolean assignTask(String targetUserEmail, Task task, Project associatedProject, User currentUser) {
        return false;
    }


    @Override
    public boolean labelTask(String labelText, Task task, User currentUser) {
        return false;
    }



    @Override
    public Task getTasksByLabel(String labelText, long labelOwnerId) {
        return null;
    }


    @Override
    public void deleteTask(long taskId, Project associatedProject, User currentUser) {

    }


    @Override
    public Task updateTask(Task task, Project associatedProject, User currentUser) {
        return null;
    }




}
