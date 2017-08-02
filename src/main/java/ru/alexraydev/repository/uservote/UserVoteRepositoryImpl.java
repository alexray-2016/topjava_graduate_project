package ru.alexraydev.repository.uservote;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alexraydev.model.User;
import ru.alexraydev.model.UserVote;
import ru.alexraydev.repository.GenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserVoteRepositoryImpl implements UserVoteRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public UserVote save(UserVote userVote, int userId) {
        if (!(userVote.getId() == null) && getById(userVote.getId(), userId) == null) {
            return null;
        }
        userVote.setUser(em.getReference(User.class, userId));
        if (userVote.getId() == null)
        {
            em.persist(userVote);
            return userVote;
        }
        else {
            return em.merge(userVote);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public UserVote getTodaysVote(int userId) {
        List<UserVote> userVotes = em.createQuery("SELECT uv FROM UserVote uv WHERE uv.user.id=:userId AND uv.date=:date")
                .setParameter("userId", userId)
                .setParameter("date", LocalDate.now()).getResultList();
        return DataAccessUtils.singleResult(userVotes);
    }

    /*@Override
    public UserVote getByDate(int userId, LocalDate date) {
        UserVote userVote = (UserVote)em.createQuery("SELECT uv FROM UserVote uv WHERE uv.user.id=:userId AND uv.date=:date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();
        return userVote;
    }*/

    @Override
    public UserVote getById(int id, int userId) {
        UserVote userVote = em.find(UserVote.class, id);
        return userVote != null && userVote.getUser().getId() == userId ? userVote : null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Query query = em.createQuery("DELETE FROM UserVote uv WHERE uv.id=:id AND uv.user.id=:userId");
        return query.setParameter("id", id).setParameter("userId", userId).executeUpdate() != 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserVote> getAll(int userId) {
        Query query = em.createQuery("SELECT uv FROM UserVote uv WHERE uv.user.id=:userId");
        return query.setParameter("userId", userId).getResultList();
    }
}
