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
public class ExamSimpleDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("maxScore")
    private int maxScore;

    @JsonProperty("isValid")
    private boolean valid;

    public static ExamSimpleDto convert(Exam from) {
        ExamSimpleDto temp = new ExamSimpleDto();
        temp.setId(from.getId());
        temp.setName(from.getName());
        temp.setMaxScore(from.getMaxScore());
        temp.setValid(from.isValid());

        return temp;
    }

    public static ExamSimpleDto simpleDtoConvert(Exam from) {
        ExamSimpleDto examSimpleDto = new ExamSimpleDto();
        examSimpleDto.setId(from.getId());
        examSimpleDto.setName(from.getName());
        examSimpleDto.setMaxScore(from.getMaxScore());
        examSimpleDto.setValid(from.isValid());

        return examSimpleDto;
    }
}
