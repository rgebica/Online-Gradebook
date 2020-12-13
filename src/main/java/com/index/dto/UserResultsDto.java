package com.index.dto;

import com.index.model.BehaviourName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResultsDto {
    private String firstName;
    private String lastName;
    private String className;
    private double finalSubjectsAverage;
    private double finalSubjectsPresence;
    private double finalBehaviour;
    private BehaviourName finalBehaviourName;

    public static UserResultsDto from(UserDto userDto, ClassDto classDto, double finalSubjectsPresence, double finalSubjectsAverage, double finalBehaviour, BehaviourName finalBehaviourName) {
        return UserResultsDto.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .className(classDto.getClassName())
                .finalSubjectsAverage(finalSubjectsAverage)
                .finalSubjectsPresence(finalSubjectsPresence)
                .finalBehaviour(finalBehaviour)
                .finalBehaviourName(finalBehaviourName)
                .build();
    }
}
