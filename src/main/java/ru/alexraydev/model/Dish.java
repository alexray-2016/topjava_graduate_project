package ru.alexraydev.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.validator.constraints.NotBlank;
import ru.alexraydev.HasId;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"id", "restaurant_id"}, name = "dishes_unique_id_restaurant_id_idx")})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "dishes")
public class Dish implements HasId, Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = false)
    @NotBlank
    private String name;

    @Column(name = "price", nullable = false, unique = false)
    @NotNull
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnoreProperties("dishList")
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, String name, int price, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Dish(String name, int price, Restaurant restaurant) {
        this(null, name, price, restaurant);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    /*@JsonProperty("restaurantId")
    public Integer getRestaurantId() {
        return restaurant == null ? null : restaurant.getId();
    }*/

    /*@JsonSetter("restaurantId")
    public void setRestaurant(int restaurantId) {
        Restaurant r = new Restaurant();
        r.setId(restaurantId);
        this.restaurant = r;
    }*/

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
