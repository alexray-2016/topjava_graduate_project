package ru.alexraydev.repository.uservote;

import ru.alexraydev.model.UserVote;

import java.time.LocalDate;
import java.util.List;

public interface UserVoteRepository{

    boolean delete(int id, int userId);

    UserVote getById(int id, int userId);

    UserVote save(UserVote entity, int userId);

    List<UserVote> getSortedByDate(String order);

    UserVote getTodaysVote(int userId);

    List<UserVote> getFilteredByDate(LocalDate date);

    List<UserVote> getFilteredByRestaurant(int chosenRestaurantId);

    List<UserVote> getFilteredByUser(int userId);
}
