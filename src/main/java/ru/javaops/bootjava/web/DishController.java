package ru.javaops.bootjava.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.repository.DishRepository;
import ru.javaops.bootjava.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = DishController.DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional(readOnly = true)
public class DishController {

    static final String DISH_URL = "/api/dish";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected DishRepository dishRepository;

    @GetMapping("/by-restaurant")
    public List<Dish> getByRestaurant(@RequestParam int restaurantId) {
        log.info("getByRestaurant {}", restaurantId);
        return dishRepository.getByRestaurantIdAAndDishDate(restaurantId, LocalDate.now());
    }
}
