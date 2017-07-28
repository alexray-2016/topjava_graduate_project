package ru.alexraydev.repository.dish;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alexraydev.model.Dish;

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

    @Override
    public Dish getById(int id) {
        return em.find(Dish.class, id);
    }

    @Override
    public boolean delete(int id) {
        Query query = em.createQuery("DELETE FROM Dish d WHERE d.id=:id");
        return query.setParameter("id", id).executeUpdate() != 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Dish> getAll() {
        Query query = em.createQuery("SELECT d FROM Dish d");
        return query.getResultList();
    }
}
