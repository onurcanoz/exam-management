package com.doping.exammanagement.dto.question;

import com.doping.exammanagement.domain.Question;
import com.doping.exammanagement.domain.QuestionItem;
import com.doping.exammanagement.dto.item.QuestionItemDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class QuestionDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("text")
    private String text;

    @JsonProperty("point")
    private int point;

    @JsonProperty("items")
    private List<QuestionItemDto> items = new ArrayList<>();

    public static QuestionDto convert(Question from) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(from.getId());
        questionDto.setText(from.getText());
        questionDto.setPoint(from.getPoint());

        for (QuestionItem item: from.getItems()) {
            QuestionItemDto itemDto = new QuestionItemDto();
            itemDto.setId(item.getId());
            itemDto.setTag(item.getTag());
            itemDto.setText(item.getText());
            itemDto.setValid(item.isValid());

            questionDto.getItems().add(itemDto);
        }

        return questionDto;
    }

}
