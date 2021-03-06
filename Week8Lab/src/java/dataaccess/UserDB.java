package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;

public class UserDB {

    public List<User> getAll() throws Exception {
        EntityManager em = DBUtil.getEntityManagerFactory().createEntityManager();

        try {
            List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
            return users;
        } finally {
            em.close();
        }

    }

    public User get(String email) throws Exception {

        EntityManager em = DBUtil.getEntityManagerFactory().createEntityManager();
        try {
            User user = em.find(User.class, email);
            return user;

        } finally {
            em.close();
        }

    }

    public void insert(User user) throws Exception {
        EntityManager em = DBUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            Role role = user.getRole();
            et.begin();
            em.persist(user);
            role.getUserList().add(user);
            em.merge(role);
            et.commit();

        } catch (Exception e) {
            et.rollback();
        } finally {
            em.close();

        }
    }

    public void update(User user) throws Exception {
        EntityManager em = DBUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tr = em.getTransaction();
        
        try {
            tr.begin();
            em.merge(user);
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
        } finally {
            em.close();
        }
    }

    public void delete(String email) throws Exception {
        EntityManager em = DBUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            User user = em.find(User.class, email);
            Role role = user.getRole();
            et.begin();
            em.remove(em.merge(user));
            role.getUserList().remove(user);
            em.merge(role);
            et.commit();

        } catch (Exception e) {
            et.rollback();
        } finally {
            em.close();
        }
    }

}
