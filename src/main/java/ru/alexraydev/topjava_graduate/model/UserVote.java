package ru.alexraydev.topjava_graduate.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import ru.alexraydev.topjava_graduate.HasId;
import ru.alexraydev.topjava_graduate.util.json.JacksonFilter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "user_votes", uniqueConstraints = {@UniqueConstraint(
        name = "user_votes_unique_user_date_idx", columnNames = {"user_id", "date"})})
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "uservotes")
@JsonFilter("admin-filter")
public class UserVote implements HasId, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(name = "date", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate date;

    @Column(name = "time", columnDefinition = "TIME DEFAULT CURRENT_TIME")
    @JsonIgnore
    private LocalTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chosen_restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@NotNull
    @JsonIgnore
    private User user;

    public UserVote() {
    }

    public UserVote(Integer id, LocalDate date, LocalTime time, Restaurant restaurant) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.restaurant = restaurant;
    }

    public UserVote(LocalDate date, LocalTime time, Restaurant restaurant) {
        this(null, date, time, restaurant);
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @JacksonFilter.Admin
    @JsonProperty("userId")
    public Integer getUserId() {
        return user == null ? null : user.getId();
    }

    @Override
    public String toString() {
        return "UserVote{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}