package ru.alexraydev.to;

import org.hibernate.validator.constraints.NotBlank;
import ru.alexraydev.HasId;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DishTo implements Serializable, HasId {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private Integer restaurantId;

    public DishTo() {

    }

    public DishTo (Integer id, String name, int price, int restaurantId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
