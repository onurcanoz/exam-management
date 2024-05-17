package com.doping.exammanagement.dto.score;

import com.doping.exammanagement.domain.ExamScore;
import com.doping.exammanagement.dto.exam.ExamSimpleDto;
import com.doping.exammanagement.dto.student.StudentDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExamScoreDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("score")
    private int score;

    @JsonProperty("exam")
    private ExamSimpleDto exam;

    @JsonProperty("student")
    private StudentDto student;

    public static ExamScoreDto convert(ExamScore from) {
        ExamScoreDto temp = new ExamScoreDto();
        temp.setId(from.getId());
        temp.setScore(from.getScore());
        temp.setExam(ExamSimpleDto.convert(from.getExam()));
        temp.setStudent(StudentDto.convert(from.getStudent()));

        return temp;
    }
}
