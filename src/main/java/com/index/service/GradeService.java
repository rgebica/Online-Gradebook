package com.index.service;

import com.index.dto.AddGradeDto;
import com.index.dto.GradeDto;

import java.util.List;

public interface GradeService {
    void addGrade(AddGradeDto addGrade);

    List<GradeDto> getGradesByUser(long userId);

    void deleteGradesByIds(String gradeIds);
}
