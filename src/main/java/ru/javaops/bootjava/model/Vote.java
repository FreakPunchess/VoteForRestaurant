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
    private LocalDateTime dateTime;
    @Column(insertable = false, updatable = false)
    private LocalDate voteDate;
    @Column(insertable = false, updatable = false)
    private LocalTime voteTime;

    public Vote(int userId, int restaurantId, LocalDateTime dateTime) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.dateTime = dateTime;
        this.voteDate = dateTime.toLocalDate();
        this.voteTime = dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        voteDate = dateTime.toLocalDate();
        voteTime = dateTime.toLocalTime();
    }
}
