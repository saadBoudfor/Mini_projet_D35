package fr.univtln.dosso_boudfor.mini_projet_d35.resources;

import fr.univtln.dosso_boudfor.mini_projet_d35.appli_beans.ProjectBeanEJB;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;
import fr.univtln.dosso_boudfor.mini_projet_d35.interfaces.IProjectResources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * * Created by yssouf on 24/12/15.
 **/

@Path("projects")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectResources implements IProjectResources {

    @Inject
    private ProjectBeanEJB projectBeanEJB;


    /********************************************************/

    @POST
    public Project createProject(@QueryParam("projectTitle") String projectTitle, @QueryParam("ownerId") long ownerId) throws BuilderException {

        if (projectTitle == null)
            throw new BadRequestException("The project has no title");

        return projectBeanEJB.createProject(projectTitle, ownerId);
    }


    @GET
    @Path("{id}")
    public Project getProjectById(@PathParam("id") final long projectId) {
        return projectBeanEJB.getProject(projectId);
    }


    /********************************************************/




}















