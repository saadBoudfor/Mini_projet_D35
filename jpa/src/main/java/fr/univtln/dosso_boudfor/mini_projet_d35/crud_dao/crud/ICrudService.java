package fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.crud;

import java.util.List;

/**
 * Created by yssouf on 12/03/15.
 */
public interface ICrudService<T> {

    public  T create(T t);

    public  T update(T t);

    public  T find(Class type, Object id);

    public void delete(Class type, Object id);

    public List<T> findWithNamedQuery(Class type, String queryName);

    public List<T> findWithNamedQuery(Class type, String queryName, int resultLimit);

    public List<T> findWithNamedQuery(Class type, String namedQueryName, QueryParameter queryParameter);

    public List<T> findWithNamedQuery(Class type, String namedQueryName, QueryParameter queryParameter, int resultLimit);

}
