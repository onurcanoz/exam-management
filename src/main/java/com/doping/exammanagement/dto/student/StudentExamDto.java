package com.doping.exammanagement.dto.student;

import com.doping.exammanagement.domain.Exam;
import com.doping.exammanagement.domain.Student;
import com.doping.exammanagement.dto.exam.ExamSimpleDto;
import com.doping.exammanagement.dto.exam.StudentActiveExam;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentExamDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("identityNumber")
    private String identityNumber;

    @JsonProperty("activeExams")
    List<StudentActiveExam> exams = new ArrayList<>();

    public static StudentExamDto convert(Student from) {
        StudentExamDto temp = new StudentExamDto();
        temp.setId(from.getId());
        temp.setFirstName(from.getFirstName());
        temp.setLastName(from.getLastName());
        temp.setIdentityNumber(from.getIdentityNumber());
        temp.setExams(from.getExams().stream().map(StudentActiveExam::convert).toList());

        return temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentExamDto that = (StudentExamDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
