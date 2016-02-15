package fr.univtln.dosso_boudfor.mini_projet_d35.interfaces;

import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Task;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;

/**
 * Created by yssouf on 03/01/16.
 **/



public interface ITaskResources {

    Task getTaskById(final long taskId);

    Task createTask(Task task, long parent_id) throws BuilderException;

}
