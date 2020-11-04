package dataaccess;


import java.util.List;
import javax.persistence.EntityManager;
import models.Role;

public class RoleDB {

    public List<Role> getAll() throws Exception {
        EntityManager em = DBUtil.getEntityManagerFactory().createEntityManager();
        
        
        try {
            List <Role> role = em.createNamedQuery("Role.findAll", Role.class).getResultList();
            return role;
        } finally {
            em.close();
        }
}
    }
