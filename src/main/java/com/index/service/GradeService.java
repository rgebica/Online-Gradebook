package com.index.service;

import com.index.dto.AddGradeDto;
import com.index.dto.EditGradeDto;
import com.index.dto.GradeDto;
import com.index.dto.SemesterGradeDto;

import java.util.List;

public interface GradeService {
    void addGrade(AddGradeDto addGrade);

    List<GradeDto> getGradesByUser(long userId);

    List<SemesterGradeDto> getSemesterGrades(long userId);

    void deleteGradesByIds(String gradeIds);

    void editGrade(EditGradeDto editGradeDto, long gradeId);
}
