package com.index.service;

import com.index.dto.AddGradeDto;
import com.index.dto.GradeDto;
import com.index.model.Grade;
import com.index.repository.GradeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GradeService {

    private final GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public GradeDto addGrade(AddGradeDto addGrade) {
        return gradeRepository.save(Grade.createGrade(addGrade)).dto();
    }

    public List<GradeDto> getGradesByUser(long userId) {
        return gradeRepository.findAllByUserId(userId).stream()
            .map(Grade::dto)
            .collect(Collectors.toList());
    }

}
