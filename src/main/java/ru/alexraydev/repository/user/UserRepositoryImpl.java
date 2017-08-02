package ru.alexraydev.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alexraydev.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepository{

    @PersistenceContext
    public EntityManager em;

    @Override
    @Transactional
    public User save(User user) {
        if (user.getId() == null) {
            em.persist(user);
            //em.flush();
            return user;
        }
        else {
            return em.merge(user);
        }
    }

    @Override
    public User getById(int id) {
        return em.find(User.class, id);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        Query query = em.createQuery("DELETE FROM User u WHERE u.id=:id");
        return query.setParameter("id", id).executeUpdate() != 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAll() {
        Query query = em.createQuery("SELECT u FROM User u");//JOIN FETCH u.votes AS uservotes
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getByEmail(String email) {
        List<User> users = em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=:email")
                .setParameter("email", email)
                .getResultList();
        return DataAccessUtils.singleResult(users);
    }
}
