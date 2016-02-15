package fr.univtln.dosso_boudfor.mini_projet_d35.resources;

import fr.univtln.dosso_boudfor.mini_projet_d35.appli_beans.TaskBeanEJB;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Task;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;
import fr.univtln.dosso_boudfor.mini_projet_d35.interfaces.ITaskResources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * * Created by yssouf on 24/12/15.
 **/

@Path("tasks")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResources implements ITaskResources {

    @Inject
    private TaskBeanEJB taskBeanEJB;


    /********************************************************/

    @POST
    public Task createTask(Task task, @QueryParam("parent_id") final long parent_id) throws BuilderException {

        if (task == null)
            throw new BadRequestException("No task received");

        return taskBeanEJB.createTask(task, parent_id);
    }


    @GET
    @Path("{id}")
    public Task getTaskById(@PathParam("id") final long taskId) {
        return taskBeanEJB.getTaskById(taskId);
    }


    /********************************************************/




}















