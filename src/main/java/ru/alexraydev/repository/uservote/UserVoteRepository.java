package ru.alexraydev.repository.uservote;

import ru.alexraydev.model.UserVote;
import ru.alexraydev.repository.GenericRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserVoteRepository{

    List<UserVote> getAll(int userId);

    boolean delete(int id, int userId);

    UserVote getById(int id, int userId);

    UserVote save(UserVote entity, int userId);

    //UserVote getByDate(int userId, LocalDate date);

    UserVote getTodaysVote(int userId);
}
