package nikita.verhovod.profi.services;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nikita.verhovod.profi.Controllers.payload.StudentDTO;
import nikita.verhovod.profi.data.groups.Group;
import nikita.verhovod.profi.data.students.Student;
import nikita.verhovod.profi.repositories.GroupRepository;
import nikita.verhovod.profi.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    public StudentDTO create(String name, String email, Integer groupId) {
        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));

        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setGroup(group);

        return StudentDTO.from(studentRepository.save(student));
    }

    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream()
            .map(StudentDTO::from)
            .toList();
    }

    public Optional<StudentDTO> findById(Integer id) {
        return studentRepository.findById(id)
            .map(StudentDTO::from);
    }

    public StudentDTO update(Integer id, String name, Integer groupId) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (name != null) student.setName(name);

        if (groupId != null) {
            Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            student.setGroup(group);
        }

        return StudentDTO.from(studentRepository.save(student));
    }

    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }

    public List<StudentDTO> search(String query) {
        return studentRepository.searchStudents(query).stream()
            .map(StudentDTO::from)
            .toList();
    }
}
