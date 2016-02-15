package fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.crud;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yssouf on 12/03/15.
 */
public class QueryParameter {

    private Map parameters = null;

    private QueryParameter(String name, Object value){
        this.parameters = new HashMap();
        this.parameters.put(name, value);
    }

    public static QueryParameter with(String name, Object value){
        return new QueryParameter(name, value);
    }

    public QueryParameter and(String name, Object value){
        this.parameters.put(name, value);
        return this;
    }

    public Map parameters(){
        return this.parameters;
    }

}
