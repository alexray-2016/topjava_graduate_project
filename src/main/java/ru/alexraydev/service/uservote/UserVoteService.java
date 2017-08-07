package ru.alexraydev.service.uservote;

import ru.alexraydev.model.UserVote;
import ru.alexraydev.util.exception.NotFoundException;
import ru.alexraydev.util.exception.UserVoteForThisDayAlreadyExistsException;

import java.time.LocalDate;
import java.util.List;

public interface UserVoteService{

    UserVote save(UserVote userVote, int userId);

    UserVote update(UserVote userVote, int userId) throws NotFoundException;

    UserVote getById(int id, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    UserVote getTodaysVote(int userId) throws NotFoundException;

    void checkIfUserVoteForTodayAlreadyExists(int userId) throws UserVoteForThisDayAlreadyExistsException;

    List<UserVote> getSortedByDate(String order);

    List<UserVote> getFilteredByDate(LocalDate date) throws NotFoundException;

    List<UserVote> getFilteredByRestaurant(int chosenRestaurantId) throws NotFoundException;

    List<UserVote> getFilteredByUser(int userId) throws NotFoundException;

}
