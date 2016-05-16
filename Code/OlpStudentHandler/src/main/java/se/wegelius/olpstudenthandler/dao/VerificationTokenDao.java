/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.LoggerFactory;
import se.wegelius.olpstudenthandler.model.persistance.VerificationtokenPersistance;

/**
 *
 * @author asawe
 */
public class VerificationTokenDao extends OlpDao<VerificationtokenPersistance, Integer> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(VerificationTokenDao.class);

    public VerificationTokenDao(Class<VerificationtokenPersistance> type) {
        super(type);
    }

    public VerificationTokenDao() {
        super(VerificationtokenPersistance.class);
    }

    @Override
    public VerificationtokenPersistance findByID(Integer id) {
        logger.info("trying to find by id");
        Session session = HibernateUtil.getSessionFactory().openSession();
        VerificationtokenPersistance token = null;
        try {
            session.beginTransaction();
            token = (VerificationtokenPersistance) session.get(VerificationtokenPersistance.class, id);
            Hibernate.initialize(token.getUser());
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
        return token;
    }
}
