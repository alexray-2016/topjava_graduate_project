package ru.alexraydev.topjava_graduate.repository.uservote;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alexraydev.topjava_graduate.model.User;
import ru.alexraydev.topjava_graduate.model.UserVote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
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
    public List<UserVote> getSortedByDate(String order) {
        Query query = em.createQuery("SELECT uv FROM UserVote uv LEFT JOIN FETCH uv.user as users LEFT JOIN FETCH uv.restaurant AS restaurants ORDER BY uv.date " + (order.toLowerCase().equals("asc") ? "ASC" : "DESC"));
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public UserVote getTodaysVote(int userId) {
        List<UserVote> userVotes = em.createQuery("SELECT uv FROM UserVote uv LEFT JOIN FETCH uv.restaurant AS restaurants WHERE uv.user.id=:userId AND uv.date=:date")
                .setParameter("userId", userId)
                .setParameter("date", LocalDate.now()).getResultList();
        return DataAccessUtils.singleResult(userVotes);
    }

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
    public List<UserVote> getFilteredByDate(LocalDate date) {
        Query query = em.createQuery("SELECT uv FROM UserVote uv LEFT JOIN FETCH uv.user as users LEFT JOIN FETCH uv.restaurant AS restaurants WHERE uv.date=:date")
                .setParameter("date", date);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserVote> getFilteredByRestaurant(int chosenRestaurantId) {
        Query query = em.createQuery("SELECT uv FROM UserVote uv LEFT JOIN FETCH uv.user as users LEFT JOIN FETCH uv.restaurant AS restaurants WHERE uv.restaurant.id=:chosenRestaurantId")
                .setParameter("chosenRestaurantId", chosenRestaurantId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserVote> getFilteredByUser(int userId) {
        Query query = em.createQuery("SELECT uv FROM UserVote uv LEFT JOIN FETCH uv.user as users WHERE uv.user.id=:userId")
                .setParameter("userId", userId);
        return query.getResultList();
    }
}
