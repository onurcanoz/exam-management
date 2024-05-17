package com.doping.exammanagement.dto.score;

import com.doping.exammanagement.domain.ExamScore;
import com.doping.exammanagement.domain.ExamScoreItem;
import com.doping.exammanagement.dto.exam.ExamSimpleDto;
import com.doping.exammanagement.dto.student.StudentDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class ExamScoreDetailDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("score")
    private int score;

    @JsonProperty("exam")
    private ExamSimpleDto exam;

    @JsonProperty("student")
    private StudentDto student;

    @JsonProperty("answers")
    private List<ExamScoreItemDto> answers = new ArrayList<>();

    public static ExamScoreDetailDto convert(ExamScore from) {
        ExamScoreDetailDto temp = new ExamScoreDetailDto();
        temp.setId(from.getId());
        temp.setScore(from.getScore());
        temp.setExam(ExamSimpleDto.convert(from.getExam()));
        temp.setStudent(StudentDto.convert(from.getStudent()));

        for (ExamScoreItem examScoreItem: from.getItems()) {
         temp.getAnswers().add(ExamScoreItemDto.convert(examScoreItem));
        }

        return temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamScoreDetailDto that = (ExamScoreDetailDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
