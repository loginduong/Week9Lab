package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;

public class UserDB {

    public List<User> getAll() throws Exception {
        EntityManager em = DBUtil.getEntityManagerFactory().createEntityManager();
        
        try {
         List<User> users = em.createNamedQuery("User.findall", User.class).getResultList();
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
            et.begin();
            em.persist(user);
            em.merge(user);
        } finally {
      
        }
    }

    public void update(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE user SET active=?, first_name=?, last_name=?, password=?, role=? WHERE email=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, user.getActive());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole());
            ps.setString(6, user.getEmail());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void delete(String email) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM user WHERE email=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

}
