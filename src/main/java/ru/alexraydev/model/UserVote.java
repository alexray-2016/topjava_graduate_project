package ru.alexraydev.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import ru.alexraydev.HasId;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "user_votes", uniqueConstraints = {@UniqueConstraint(
        name = "user_votes_unique_user_date_idx", columnNames = {"user_id", "date"})})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "uservotes")
public class UserVote implements HasId, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate date;

    @Column(name = "time", columnDefinition = "TIME DEFAULT CURRENT_TIME")
    @JsonIgnore
    private LocalTime time;

    @Column(name = "chosen_restaurant_id", nullable = false, unique = false)
    @NotNull
    private Integer chosenRestaurantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnore
    private User user;

    public UserVote() {
    }

    public UserVote(Integer id, Integer chosenRestaurantId, User user) {
        this.id = id;
        this.chosenRestaurantId = chosenRestaurantId;
        this.user = user;
    }

    public UserVote(Integer chosenRestaurantId, User user) {
        this(null, chosenRestaurantId, user);
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

    public Integer getChosenRestaurantId() {
        return chosenRestaurantId;
    }

    public void setChosenRestaurantId(Integer chosenRestaurantId) {
        this.chosenRestaurantId = chosenRestaurantId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserVote{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", chosenRestaurantId=" + chosenRestaurantId +
                '}';
    }
}