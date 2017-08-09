package ru.alexraydev.topjava_graduate.service.uservote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.alexraydev.topjava_graduate.model.UserVote;
import ru.alexraydev.topjava_graduate.repository.uservote.UserVoteRepository;
import ru.alexraydev.topjava_graduate.util.exception.NotFoundException;
import ru.alexraydev.topjava_graduate.util.exception.UserVoteForThisDayAlreadyExistsException;
import static ru.alexraydev.topjava_graduate.util.ValidationUtil.*;

import java.time.LocalDate;
import java.util.List;

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

    @Override
    public List<UserVote> getSortedByDate(String order) {
        return userVoteRepository.getSortedByDate(order);
    }

    @Override
    public List<UserVote> getFilteredByDate(LocalDate date) throws NotFoundException {
        return checkUserVotesFiltered(userVoteRepository.getFilteredByDate(date),
                "Not found user votes for date " + date);
    }

    @Override
    public List<UserVote> getFilteredByRestaurant(int chosenRestaurantId) throws NotFoundException {
        return checkUserVotesFiltered(userVoteRepository.getFilteredByRestaurant(chosenRestaurantId),
                "Not found user votes for restaurant with id=" + chosenRestaurantId);
    }

    @Override
    public List<UserVote> getFilteredByUser(int userId) throws NotFoundException {
        return checkUserVotesFiltered(userVoteRepository.getFilteredByUser(userId),
                "Not found user votes for user with id=" + userId);
    }
}
