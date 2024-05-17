package com.doping.exammanagement.dto.score;

import com.doping.exammanagement.domain.ExamScoreItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExamScoreItemDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("score")
    private int score;

    @JsonProperty("valid")
    private boolean valid;

    @JsonProperty("questionId")
    private long questionId;

    @JsonProperty("answerId")
    private long answerId;

    public static ExamScoreItemDto convert(ExamScoreItem from) {
        ExamScoreItemDto temp = new ExamScoreItemDto();
        temp.setId(from.getId());
        temp.setScore(from.getScore());
        temp.setValid(from.isValid());
        temp.setQuestionId(from.getQuestion().getId());
        temp.setAnswerId(from.getQuestionItem().getId());

        return temp;
    }
}
