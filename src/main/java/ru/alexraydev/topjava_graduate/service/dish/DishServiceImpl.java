package ru.alexraydev.topjava_graduate.service.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.alexraydev.topjava_graduate.model.Dish;
import ru.alexraydev.topjava_graduate.repository.dish.DishRepository;
import ru.alexraydev.topjava_graduate.util.exception.NotFoundException;
import static ru.alexraydev.topjava_graduate.util.ValidationUtil.*;

import java.util.List;

@Service
public class DishServiceImpl implements DishService{

    private DishRepository dishRepository;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public Dish save(Dish entity) {
        Assert.notNull(entity, "dish must not be null");
        return dishRepository.save(entity);
    }

    @Override
    public Dish update(Dish entity) {
        Assert.notNull(entity, "dish must not be null");
        return checkNotFoundWithId(dishRepository.save(entity), entity.getId());
    }

    @Override
    public Dish getById(int id) throws NotFoundException {
        return checkNotFoundWithId(dishRepository.getById(id), id);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(dishRepository.delete(id), id);
    }

    @Override
    public List<Dish> getAll() {
        return dishRepository.getAll();
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Override
    public void evictCache() {

    }
}
