//package com.index.service;
//
//import com.index.dto.AddBehaviourDto;
//import com.index.dto.BehaviourDto;
//import com.index.repository.BehaviourRepository;
//import com.index.model.User;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions.*;
//import org.mockito.Mock;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.mock;
//
//public class AddBehaviourTest {
//
//    BehaviourRepository behaviourRepository = mock(BehaviourRepository.class);
//    BehaviourService behaviourService = BehaviourService.builder().behaviourRepository(behaviourRepository).build();
//
//    @Test
//    void shouldAddBehaviour() {
//        AddBehaviourDto addBehaviour = AddBehaviourDto.builder()
//                .userId()
//
//        BehaviourDto addedBehaviour = behaviourService.addBehaviour(addBehaviour);
//
//        assertEquals(addedBehaviour.getGrade(), addBehaviour.getGrade());
//
//    }
//}
