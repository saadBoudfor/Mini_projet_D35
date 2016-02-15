package fr.univtln.dosso_boudfor.mini_projet_d35.crud_dao.crud;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yssouf on 12/03/15.
 **/

public abstract class AbstractCrudService<T> implements ICrudService<T> {

    private EntityManager em = null;

    public AbstractCrudService(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEm() {
        return em;
    }

    public T create(T t) {
        em.joinTransaction();
        this.em.persist(t);
        this.em.flush();
        this.em.refresh(t);
        return t;
    }


    @SuppressWarnings("unchecked")
    public T find(Class type, Object id) {
        em.joinTransaction();
        return (T) this.em.find(type, id);
    }

    public T update(T t) {
        em.joinTransaction();
        return this.em.merge(t);
    }

    public void delete(Class type, Object id) {
        em.joinTransaction();
        Object ref = this.em.getReference(type, id);
        this.em.remove(ref);
    }


    @SuppressWarnings("unchecked")
    public List<T> findWithNamedQuery(Class type, String namedQueryName) {
        em.joinTransaction();
        return this.em.createNamedQuery(namedQueryName, type).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> findWithNamedQuery(Class type, String queryName, int resultLimit) {
        em.joinTransaction();
        return this.em.createNamedQuery(queryName, type).setMaxResults(resultLimit).getResultList();
    }

    public List<T> findWithNamedQuery(Class type, String namedQueryName, QueryParameter queryParameter) {
        em.joinTransaction();
        return findWithNamedQuery(type, namedQueryName, queryParameter, 0);
    }

    @SuppressWarnings("unchecked")
    public List<T> findWithNamedQuery(Class type, String namedQueryName, QueryParameter queryParameter, int resultLimit) {
        em.joinTransaction();
        Set<Map.Entry<String, Object>> rawParameters = queryParameter.parameters().entrySet();
        Query query = this.em.createNamedQuery(namedQueryName, type);
        if (resultLimit > 0)
            query.setMaxResults(resultLimit);
        for (Map.Entry entry : rawParameters) {
            query.setParameter((String) entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

}
