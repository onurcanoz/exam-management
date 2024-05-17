package com.doping.exammanagement.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "exam_score_items")
@Getter
@Setter
@ToString
public class ExamScoreItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exam_score_id", nullable=false)
    @ToString.Exclude
    private ExamScore examScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id", nullable=false)
    @ToString.Exclude
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="answer_id", nullable=false)
    @ToString.Exclude
    private QuestionItem questionItem;

    @Column(name = "score")
    private int score;

    @Column(name = "valid")
    private boolean valid;
}
