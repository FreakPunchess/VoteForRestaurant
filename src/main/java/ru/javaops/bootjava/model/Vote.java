package ru.javaops.bootjava.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "vote")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Vote extends BaseEntity {

    @NotNull
    @Column(name = "user_id")
    private int userId;
    @NotNull
    @Column(name = "restaurant_id")
    private int restaurantId;
    @NotNull
    @Column(name = "date_time")
    private LocalDateTime dateTime = LocalDateTime.now();
    @Column(insertable = false, updatable = false)
    private LocalDate voteDate = dateTime.toLocalDate();
    @Column(insertable = false, updatable = false)
    private LocalTime voteTime = dateTime.toLocalTime();

    public Vote(int userId, int restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        setDateTime(LocalDateTime.now());
        this.restaurantId = restaurantId;
    }
}
