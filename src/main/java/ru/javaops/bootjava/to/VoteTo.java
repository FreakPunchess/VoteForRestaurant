package ru.javaops.bootjava.to;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class VoteTo {
    @Min(1)
    int restaurantId;
    @NotNull
    private LocalDateTime dateTime = LocalDateTime.now();

    public VoteTo(int restaurantId, LocalDateTime dateTime) {
        this.restaurantId = restaurantId;
        this.dateTime = dateTime;
    }
}
