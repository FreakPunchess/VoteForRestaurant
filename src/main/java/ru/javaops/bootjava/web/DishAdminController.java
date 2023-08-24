package ru.javaops.bootjava.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.repository.DishRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javaops.bootjava.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.bootjava.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishAdminController.DISH_ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishAdminController {

    static final String DISH_ADMIN_URL = "/api/admin/dish";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected DishRepository repository;

    @GetMapping
    public List<Dish> getAll() {
        log.info("getAll");
//        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return repository.getAllWithRestaurant(Sort.by(Sort.Direction.ASC, "name"));
    }

    @GetMapping("/by-restaurant")
    public List<Dish> getByRestaurant(@RequestParam int restaurantId) {
        log.info("getByRestaurant {}", restaurantId);
        return repository.getByRestaurantIdAAndDishDate(restaurantId, LocalDate.now());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish) {
        log.info("create {}", dish);
        checkNew(dish);
        Dish created = repository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DISH_ADMIN_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("update {} with id={}", dish, id);
        assureIdConsistent(dish, id);
        repository.save(dish);
    }
}
