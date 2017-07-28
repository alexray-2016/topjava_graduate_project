package ru.alexraydev.repository.uservote;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alexraydev.model.UserVote;
import ru.alexraydev.repository.GenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserVoteRepositoryImpl implements UserVoteRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public UserVote save(UserVote userVote) {
        if (userVote.getId() == null) {
            em.persist(userVote);
            return userVote;
        }
        else {
            return em.merge(userVote);
        }
    }

    @Override
    public UserVote getById(int id) {
        return em.find(UserVote.class, id);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        Query query = em.createQuery("DELETE FROM UserVote uv WHERE uv.id=:id");
        return query.setParameter("id", id).executeUpdate() != 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserVote> getAll() {
        Query query = em.createQuery("SELECT uv FROM UserVote");
        return query.getResultList();
    }
}
