package ru.alexraydev;

/**
 * Created by Administrator on 28.07.2017.
 */
public interface HasId {
    Integer getId();

    void setId(Integer id);

    default boolean isNew() {
        return (getId() == null);
    }
}
