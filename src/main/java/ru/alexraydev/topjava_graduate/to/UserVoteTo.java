package ru.alexraydev.topjava_graduate.to;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.alexraydev.topjava_graduate.HasId;
import ru.alexraydev.topjava_graduate.util.json.JacksonFilter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@JsonFilter("admin-filter")
public class UserVoteTo implements Serializable, HasId {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Integer id;

    private LocalDate date;

    @JsonIgnore
    private LocalTime time;

    @NotNull
    private Integer chosenRestaurantId;

    @JacksonFilter.Admin
    private Integer userId;

    public UserVoteTo() {

    }

    public UserVoteTo(Integer id, LocalDate date, LocalTime time, Integer chosenRestaurantId, Integer userId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.chosenRestaurantId = chosenRestaurantId;
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getChosenRestaurantId() {
        return chosenRestaurantId;
    }

    public void setChosenRestaurantId(Integer chosenRestaurantId) {
        this.chosenRestaurantId = chosenRestaurantId;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
