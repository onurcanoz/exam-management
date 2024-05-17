package com.doping.exammanagement.dto.exam;

import com.doping.exammanagement.domain.Exam;
import com.doping.exammanagement.domain.Question;
import com.doping.exammanagement.domain.QuestionItem;
import com.doping.exammanagement.dto.item.QuestionItemDto;
import com.doping.exammanagement.dto.question.QuestionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class ExamDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("maxScore")
    private int maxScore;

    @JsonProperty("isValid")
    private boolean valid;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("questions")
    private List<QuestionDto> questions = new ArrayList<>();

    public static ExamDto convert(Exam from) {
        ExamDto examDto = new ExamDto();
        examDto.setId(from.getId());
        examDto.setName(from.getName());
        examDto.setMaxScore(from.getMaxScore());
        examDto.setValid(from.isValid());
        examDto.setUpdatedAt(from.getUpdatedAt());

        for (Question question: from.getQuestions()) {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setId(question.getId());
            questionDto.setText(question.getText());
            questionDto.setPoint(question.getPoint());

            for (QuestionItem item: question.getItems()) {
                QuestionItemDto itemDto = new QuestionItemDto();
                itemDto.setId(item.getId());
                itemDto.setTag(item.getTag());
                itemDto.setText(item.getText());
                itemDto.setValid(item.isValid());

                questionDto.getItems().add(itemDto);
            }

            examDto.getQuestions().add(questionDto);
        }

        return examDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamDto examDto = (ExamDto) o;
        return id == examDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
