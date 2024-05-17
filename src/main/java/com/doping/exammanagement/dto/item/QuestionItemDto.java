package com.doping.exammanagement.dto.item;

import com.doping.exammanagement.domain.QuestionItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuestionItemDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("tag")
    private String tag;

    @JsonProperty("text")
    private String text;

    @JsonProperty("isValid")
    private boolean valid;

    public static QuestionItemDto convert(QuestionItem from) {
        QuestionItemDto temp = new QuestionItemDto();
        temp.setId(from.getId());
        temp.setTag(from.getTag());
        temp.setText(from.getText());
        temp.setValid(from.isValid());

        return temp;
    }
}
