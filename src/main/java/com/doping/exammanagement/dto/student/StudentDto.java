package com.doping.exammanagement.dto.student;

import com.doping.exammanagement.domain.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("identityNumber")
    private String identityNumber;

    public static StudentDto convert(Student from) {
        StudentDto temp = new StudentDto();
        temp.setId(from.getId());
        temp.setFirstName(from.getFirstName());
        temp.setLastName(from.getLastName());
        temp.setIdentityNumber(from.getIdentityNumber());

        return temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDto that = (StudentDto) o;
        return id == that.id && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(identityNumber, that.identityNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, identityNumber);
    }
}
