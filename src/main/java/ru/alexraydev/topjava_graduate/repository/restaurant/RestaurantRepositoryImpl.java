package ru.alexraydev.topjava_graduate.repository.restaurant;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alexraydev.topjava_graduate.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class RestaurantRepositoryImpl implements RestaurantRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.getId() == null) {
            em.persist(restaurant);
            return restaurant;
        }
        else {
            return em.merge(restaurant);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Restaurant getById(int id) {
        List<Restaurant> restaurantList = em.createQuery("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.dishList AS dishes WHERE r.id=:id")
                .setParameter("id", id)
                .getResultList();
        return DataAccessUtils.singleResult(restaurantList);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        Query query = em.createQuery("DELETE FROM Restaurant r WHERE r.id=:id");
        return query.setParameter("id", id).executeUpdate() != 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Restaurant> getAll() {
        Query query = em.createQuery("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.dishList AS dishes");
        return query.getResultList();
    }
}
