package ru.alexraydev.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
import java.time.LocalDateTime;

@Entity
@Table(name = "user_votes", uniqueConstraints = {@UniqueConstraint(
        name = "user_votes_unique_user_datetime_idx", columnNames = {"user_id", "date_time"})})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "uservotes")
public class UserVote implements HasId, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "date_time", columnDefinition = "TIMESTAMP DEFAULT NOW")
    private LocalDateTime dateTime;

    @Column(name = "chosen_restaurant_id", nullable = false, unique = false)
    @NotNull
    private Integer chosenRestaurantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
                ", dateTime=" + dateTime +
                ", chosenRestaurantId=" + chosenRestaurantId +
                ", user=" + user +
                '}';
    }
}