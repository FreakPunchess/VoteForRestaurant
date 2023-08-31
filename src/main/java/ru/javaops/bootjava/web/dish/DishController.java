package ru.javaops.bootjava.web.dish;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = DishController.DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional(readOnly = true)
public class DishController {

    static final String DISH_URL = "/api/restaurant/{restaurantId}/dish";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected DishRepository repository;

    @GetMapping()
    public List<Dish> getByRestaurant(@PathVariable int restaurantId) {
        log.info("get list of dish by restaurant {}", restaurantId);
        return repository.getByRestaurantIdAAndDishDate(restaurantId, LocalDate.now());
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        log.info("get dish by id {}", id);
        return repository.getExisted(id);
    }
}
