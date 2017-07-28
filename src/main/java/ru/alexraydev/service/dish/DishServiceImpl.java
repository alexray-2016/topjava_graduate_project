package ru.alexraydev.service.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexraydev.model.Dish;
import ru.alexraydev.repository.dish.DishRepository;
import ru.alexraydev.util.ValidationUtil;
import ru.alexraydev.util.exception.NotFoundException;
import static ru.alexraydev.util.ValidationUtil.*;

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
        return dishRepository.save(entity);
    }

    @Override
    public Dish update(Dish entity) {
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
}
