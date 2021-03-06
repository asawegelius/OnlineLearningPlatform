/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.HibernateUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.LoggerFactory;
import se.wegelius.olpstudenthandler.model.persistance.CoursePersistance;

/**
 *
 * @author asawe
 */
public class CourseDao extends OlpDao<CoursePersistance, Integer> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CourseDao.class);

    public CourseDao(Class<CoursePersistance> type) {
        super(type);
    }

    /**
     *
     */
    public CourseDao() {
        super(CoursePersistance.class);
    }

    /**
     * Find an entity by its primary key
     *
     * @param id the entity's primary key
     * @return the entity
     */
    @SuppressWarnings("unchecked")
    @Override
    public CoursePersistance findByID(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CoursePersistance course = null;
        try {
            session.beginTransaction();
            course = (CoursePersistance) session.get(CoursePersistance.class, id);
            Hibernate.initialize(course.getCourseBranch());
            Hibernate.initialize(course.getContentProvider());
            Hibernate.initialize(course.getCourseType());
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
        return course;
    }

    /**
     * Get all entities
     *
     * @return a Set of all entities
     */
    @SuppressWarnings("unchecked")
    @Override
    public Set<CoursePersistance> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<CoursePersistance> objects = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from CoursePersistance");
            objects = query.list();
            for (CoursePersistance course : objects) {
                Hibernate.initialize(course.getCourseBranch());
                Hibernate.initialize(course.getContentProvider());
                Hibernate.initialize(course.getCourseType());
            }
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
     * @param query the name of the query
     * @return a Set of the entities
     */
    @SuppressWarnings("unchecked")
    public Set<CoursePersistance> getAll(String query) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<CoursePersistance> objects = null;
        try {
            session.beginTransaction();
            objects = session.createQuery(query).list();
            for (CoursePersistance course : objects) {
                Hibernate.initialize(course.getCourseBranch());
                Hibernate.initialize(course.getContentProvider());
                Hibernate.initialize(course.getCourseType());
            }
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
}
