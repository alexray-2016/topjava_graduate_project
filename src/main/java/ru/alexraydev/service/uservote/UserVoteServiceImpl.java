package ru.alexraydev.service.uservote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.alexraydev.model.Dish;
import ru.alexraydev.model.UserVote;
import ru.alexraydev.repository.dish.DishRepository;
import ru.alexraydev.repository.uservote.UserVoteRepository;
import ru.alexraydev.util.exception.NotFoundException;
import ru.alexraydev.util.exception.UserVoteForThisDayAlreadyExistsException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.alexraydev.util.ValidationUtil.checkNotFound;
import static ru.alexraydev.util.ValidationUtil.checkNotFoundForToday;
import static ru.alexraydev.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserVoteServiceImpl implements UserVoteService{

    private UserVoteRepository userVoteRepository;

    @Autowired
    public UserVoteServiceImpl(UserVoteRepository userVoteRepository) {
        this.userVoteRepository = userVoteRepository;
    }

    @Override
    public UserVote save(UserVote entity, int userId) {
        Assert.notNull(entity, "userVote must not be null");
        return userVoteRepository.save(entity, userId);
    }

    @Override
    public UserVote update(UserVote entity, int userId) {
        Assert.notNull(entity, "userVote must not be null");
        return checkNotFoundWithId(userVoteRepository.save(entity, userId), entity.getId());
    }

    @Override
    public UserVote getById(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(userVoteRepository.getById(id, userId), id);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(userVoteRepository.delete(id, userId), id);
    }

    @Override
    public List<UserVote> getAll(int userId) {
        return userVoteRepository.getAll(userId);
    }

    @Override
    public UserVote getTodaysVote(int userId) throws NotFoundException {
        return checkNotFoundForToday(userVoteRepository.getTodaysVote(userId),
                String.format("User with id %d have no user vote for today", userId));
    }

    @Override
    public void checkIfUserVoteForTodayAlreadyExists(int userId) throws UserVoteForThisDayAlreadyExistsException {
        UserVote userVoteFromDB = userVoteRepository.getTodaysVote(userId);
        if (userVoteFromDB != null) {
            throw new UserVoteForThisDayAlreadyExistsException("user vote for user with id=" + userId + " for today already exists");
        }
    }
}
