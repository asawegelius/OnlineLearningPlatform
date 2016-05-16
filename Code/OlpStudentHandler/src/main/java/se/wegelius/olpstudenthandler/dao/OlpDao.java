/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.HibernateUtil;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Query;
import org.slf4j.LoggerFactory;

/**
 * Generic repository providing basic CRUD operations
 *
 * @author Asa Wegelius
 *
 * @param <T> the entity type
 * @param <ID> the primary key type
 */
public class OlpDao<T, ID extends Serializable> implements IOlpDao<T, ID> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OlpDao.class);
    private final Class<T> type;

    public OlpDao(Class<T> type) {
        super();
        this.type = type;
    }

    /**
     * Save an entity
     *
     * @param entity the entity to save
     */
    @Override
    public void save(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error("tried to save " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * Updates an entity
     *
     * @param entity the entity to update
     */
    @Override
    public void update(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * Saves or updates an entity
     *
     * @param entity the entity to update or save
     */
    @Override
    public void saveOrUpdate(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * Merges an entity
     *
     * @param entity the entity to merge
     */
    @Override
    public void merge(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * Find an entity by its primary key
     *
     * @param id the entity's primary key
     * @return the entity
     */
    @SuppressWarnings("unchecked")
    @Override
    public T findByID(ID id) {
        logger.info("trying to find by id " + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        T obj = null;
        try {
            session.beginTransaction();
            obj = (T) session.get(type, id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                logger.error(e.getMessage());
            }
        }
        return obj;
    }

    /**
     * Get all entities
     *
     * @return a Set of all entities
     */
    @SuppressWarnings("unchecked")
    @Override
    public Set<T> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<T> objects = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from " + type.getName());
            objects = query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                logger.error(e.getMessage());
            }
        }
        if (objects != null) {
            return new HashSet<>(objects);
        }
        return null;
    }

    /**
     * Find entities based on a query
     *
     * @param hsql the query
     * @param params the query parameters
     * @return a Set of the entities
     */
    @SuppressWarnings("unchecked")
    @Override
    public Set<T> query(String hsql, Map<String, Object> params) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<T> objects = null;
        logger.info("hsql: " + hsql + " params size: " + params.size());
        try {
            session.beginTransaction();
            Query query = session.createQuery(hsql);
            if (params != null) {
                for (String i : params.keySet()) {
                    query.setParameter(i, params.get(i));
                }
            }
            logger.info("query parameters: " + Arrays.toString(query.getNamedParameters()));
            if ((!hsql.toUpperCase().contains("DELETE"))
                    && (!hsql.toUpperCase().contains("UPDATE"))
                    && (!hsql.toUpperCase().contains("INSERT"))) {
                objects = query.list();
                logger.info("FINISHED - query. Result size=" + objects.size());
            } else {
                logger.info("FINISHED - query. ");
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                logger.error(e.getMessage());
            }
        }
        if (objects != null) {
            logger.info("no of objects: " + objects.size());
            return new HashSet<>(objects);
        }
        return null;
    }

    /**
     * Deletes an entity
     *
     * @param entity the entity to delete
     */
    @Override
    public void delete(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * Get the class of the entity
     *
     * @return the entity class
     */
    @Override
    public Class<T> getEntityClass() {
        return type;
    }

    /**
     * Count all entities
     *
     * @return how many entities there are
     */
    @Override
    public int count() {
        Set<T> all = getAll();
        return all.size();
    }
}
