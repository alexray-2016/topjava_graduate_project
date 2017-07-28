package ru.alexraydev.service.uservote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexraydev.model.Dish;
import ru.alexraydev.model.UserVote;
import ru.alexraydev.repository.dish.DishRepository;
import ru.alexraydev.repository.uservote.UserVoteRepository;
import ru.alexraydev.util.exception.NotFoundException;

import java.util.List;

import static ru.alexraydev.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserVoteServiceImpl implements UserVoteService{

    private UserVoteRepository userVoteRepository;

    @Autowired
    public UserVoteServiceImpl(UserVoteRepository userVoteRepository) {
        this.userVoteRepository = userVoteRepository;
    }

    @Override
    public UserVote save(UserVote entity) {
        return userVoteRepository.save(entity);
    }

    @Override
    public UserVote update(UserVote entity) {
        return checkNotFoundWithId(userVoteRepository.save(entity), entity.getId());
    }

    @Override
    public UserVote getById(int id) throws NotFoundException {
        return checkNotFoundWithId(userVoteRepository.getById(id), id);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(userVoteRepository.delete(id), id);
    }

    @Override
    public List<UserVote> getAll() {
        return userVoteRepository.getAll();
    }
}
