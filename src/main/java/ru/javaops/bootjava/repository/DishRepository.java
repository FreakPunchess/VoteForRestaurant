package ru.javaops.bootjava.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository extends BaseRepository<Dish> {
    @Query("select d from Dish d join fetch d.restaurant where d.restaurant.id = ?1")
    List<Dish> getByRestaurantIdAAndDishDate(int restaurantId, LocalDate date);

    @Query("select d from Dish d join fetch d.restaurant where d.restaurant.id = ?1")
    List<Dish> getAllWithRestaurant(Sort name);
}
