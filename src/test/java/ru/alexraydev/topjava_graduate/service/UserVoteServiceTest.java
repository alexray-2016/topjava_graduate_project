package ru.alexraydev.topjava_graduate.service;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alexraydev.topjava_graduate.model.UserVote;
import ru.alexraydev.topjava_graduate.service.uservote.UserVoteService;
import ru.alexraydev.topjava_graduate.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static ru.alexraydev.topjava_graduate.UserVoteTestData.*;
import static ru.alexraydev.topjava_graduate.UserTestData.FIRST_USER_ID;
import static ru.alexraydev.topjava_graduate.UserTestData.SECOND_USER_ID;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class UserVoteServiceTest extends AbstractServiceTest{

    @Autowired
    private UserVoteService service;

    @Test
    public void testDelete() throws Exception {
        service.delete(USERVOTE1.getId(), FIRST_USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USERVOTE2, USERVOTE3, USERVOTE4, USERVOTE5),
                service.getFilteredByUser(FIRST_USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception{
        service.delete(100500, FIRST_USER_ID);
    }

    @Test
    public void testSave() throws Exception {
        UserVote created = new UserVote(LocalDate.now(), LocalTime.of(10, 0), 1);
        service.save(created, FIRST_USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USERVOTE1, USERVOTE2, USERVOTE3, USERVOTE4, USERVOTE5, created), service.getFilteredByUser(FIRST_USER_ID));
    }

    @Test
    public void testGet() throws Exception {
        UserVote actual = service.getById(USERVOTE1.getId(), FIRST_USER_ID);
        MATCHER.assertEquals(USERVOTE1, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.getById(100500, FIRST_USER_ID);
    }

    @Test
    public void testUpdate() throws Exception {
        UserVote updated = new UserVote(USERVOTE1.getId(), USERVOTE1.getDate(), USERVOTE1.getTime(), 2);
        service.update(updated, FIRST_USER_ID);
        MATCHER.assertEquals(updated, service.getById(USERVOTE1.getId(), FIRST_USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        service.update(new UserVote(100500, LocalDate.now(), LocalTime.now(), 2), FIRST_USER_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(
                Arrays.asList(USERVOTE1, USERVOTE2, USERVOTE3, USERVOTE4, USERVOTE5)
                , service.getFilteredByUser(FIRST_USER_ID));
    }

    @Test
    public void testGetFilteredByUser() throws Exception {
        List<UserVote> userVotes = service.getFilteredByUser(FIRST_USER_ID);
        userVotes.forEach(uv -> Assert.assertTrue(uv.getUser().getId() == FIRST_USER_ID));
    }

    @Test
    public void testGetFilteredByDate() throws Exception {
        LocalDate date = LocalDate.of(2017, 8, 2);
        List<UserVote> userVotes = service.getFilteredByDate(date);
        userVotes.forEach(uv -> Assert.assertTrue(uv.getDate().isEqual(date)));
    }

    @Test
    public void testGetFilteredByRestaurant() throws Exception {
        int chosenRestaurantId = 1;
        List<UserVote> userVotes = service.getFilteredByRestaurant(chosenRestaurantId);
        userVotes.forEach(uv -> Assert.assertTrue(uv.getChosenRestaurantId() == chosenRestaurantId));
    }
}
