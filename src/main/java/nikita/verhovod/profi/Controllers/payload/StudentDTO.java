package nikita.verhovod.profi.Controllers.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import nikita.verhovod.profi.data.students.Student;

public record StudentDTO(
    Integer id,
    String name,
    @JsonProperty("group_id") Integer groupId
) {
    public static StudentDTO from(Student student) {
        return new StudentDTO(
            student.getId(),
            student.getName(),
            student.getGroup() != null ? student.getGroup().getId() : null
        );
    }
}