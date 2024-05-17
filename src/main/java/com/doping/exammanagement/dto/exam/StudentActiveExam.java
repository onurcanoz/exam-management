package com.doping.exammanagement.dto.exam;

import com.doping.exammanagement.domain.Exam;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentActiveExam {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("maxScore")
    private int maxScore;

    @JsonProperty("isCompleted")
    private boolean active;

    public static StudentActiveExam convert(Exam from) {
        StudentActiveExam temp = new StudentActiveExam();
        temp.setId(from.getId());
        temp.setName(from.getName());
        temp.setMaxScore(from.getMaxScore());
        temp.setActive(from.getScores().stream().anyMatch(score -> score.getExam().getId().equals(from.getId())));


        return temp;
    }
}
