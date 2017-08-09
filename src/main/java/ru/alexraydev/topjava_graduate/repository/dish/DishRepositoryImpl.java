package ru.alexraydev.topjava_graduate.repository.dish;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alexraydev.topjava_graduate.model.Dish;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DishRepositoryImpl implements DishRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Dish save(Dish dish) {
        if (dish.getId() == null) {
            em.persist(dish);
            return dish;
        }
        else {
            return em.merge(dish);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Dish getById(int id) {
        List<Dish> dishList = em.createQuery("SELECT d FROM Dish d LEFT JOIN FETCH d.restaurant AS restaurants WHERE d.id=:id")
                .setParameter("id", id)
                .getResultList();
        return DataAccessUtils.singleResult(dishList);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        Query query = em.createQuery("DELETE FROM Dish d WHERE d.id=:id");
        return query.setParameter("id", id).executeUpdate() != 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Dish> getAll() {
        Query query = em.createQuery("SELECT d FROM Dish d LEFT JOIN FETCH d.restaurant AS restaurants");
        return query.getResultList();
    }
}
