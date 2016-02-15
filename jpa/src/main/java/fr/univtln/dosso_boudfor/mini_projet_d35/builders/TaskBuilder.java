package fr.univtln.dosso_boudfor.mini_projet_d35.builders;

import fr.univtln.dosso_boudfor.mini_projet_d35.annotations.Builder;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Task;
import fr.univtln.dosso_boudfor.mini_projet_d35.enums.Priority;
import fr.univtln.dosso_boudfor.mini_projet_d35.enums.Status;
import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;

import java.util.Date;


/**
 * * Created by yssouf on 24/12/15.
 */

@Builder
public class TaskBuilder implements IBuilder<TaskBuilder, Task>{

    private String titre;
    private String contenue;
    private Status status;
    private Date delaisEcheance;
    private Priority priority;
    private Project parent;

    public TaskBuilder() {
    }

    @Override
    public TaskBuilder init() {
        this.titre = null;
        this.contenue = null;
        this.status = null;
        this.delaisEcheance = null;
        this.priority = null;
        this.parent = null;
        return this;
    }

    @Override
    public Task build() throws BuilderException {
        if(titre==null || parent==null)
            throw new BuilderException("Au moins un champ obligatoire est null");

        if(contenue == null)
            contenue = "";
        if(status == null)
            status = Status.EN_ATTENTE;
        if(priority == null)
            priority = Priority.AUCUNNE;

        return new Task(titre, contenue, status, delaisEcheance, priority, parent);
    }

    public TaskBuilder setTitre(String titre) {
        this.titre = titre;
        return this;
    }

    public TaskBuilder setContenue(String contenue) {
        this.contenue = contenue;
        return this;
    }

    public TaskBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public TaskBuilder setDelaisEcheance(Date delaisEcheance) {
        this.delaisEcheance = delaisEcheance;
        return this;
    }

    public TaskBuilder setPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public TaskBuilder setParent(Project parent) {
        this.parent = parent;
        return this;
    }

}
