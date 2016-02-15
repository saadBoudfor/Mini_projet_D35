package fr.univtln.dosso_boudfor.mini_projet_d35.utils;

import fr.univtln.dosso_boudfor.mini_projet_d35.enums.Priority;
import fr.univtln.dosso_boudfor.mini_projet_d35.enums.Status;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * * Created by yssouf on 22/12/15.
 */


public class TaskFilter implements Serializable{

    private String titre;
    private Priority priority;
    private Status status;
    private Date deadLineDate;

    private long userId;


    public TaskFilter(long userId) {
        this.userId = userId;
    }


    public void init(){
        this.titre = null;
        this.priority = null;
        this.status = null;
        this.deadLineDate = null;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDeadLineDate(Date deadLineDate) {
        this.deadLineDate = deadLineDate;
    }


    public String buildQuery(){

        String query = "SELECT t FROM Task t WHERE t.parent.owner.id = :oId";

        if(titre != null)
            query += " AND t.titre LIKE :tTitre";
        if(priority != null)
            query += " AND t.priority = :tPriority";
        if(status != null)
            query += " AND t.status = :tStatus";
        if(deadLineDate != null)
            query += " AND t.delaisEcheance = :tDelaisEcheance";

        return query;
    }

    public Set<Map.Entry<String, Object>> getQueryParameters(){

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("oId", userId);

        if(titre != null)
            parameters.put("tTitre", titre);
        if(priority != null)
            parameters.put("tPriority", priority);
        if(status != null)
            parameters.put("tStatus", status);
        if(deadLineDate != null)
            parameters.put("tDelaisEcheance", deadLineDate);

        return parameters.entrySet();
    }




}
























