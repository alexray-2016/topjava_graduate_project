package ru.alexraydev.service.uservote;

import ru.alexraydev.model.UserVote;
import ru.alexraydev.service.GenericService;
import ru.alexraydev.util.exception.NotFoundException;
import ru.alexraydev.util.exception.UserVoteForThisDayAlreadyExistsException;

import java.util.List;

public interface UserVoteService{

    UserVote save(UserVote userVote, int userId);

    UserVote update(UserVote userVote, int userId) throws NotFoundException;

    UserVote getById(int id, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    List<UserVote> getAll(int userId);

    UserVote getTodaysVote(int userId) throws NotFoundException;

    //void checkIfUserVoteForThisDayAlreadyExists(UserVote userVote) throws UserVoteForThisDayAlreadyExistsException;
}
