package ru.javaops.bootjava.to;

import jakarta.validation.constraints.Min;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class VoteTo {
    @Min(1)
    int restaurantId;
}
