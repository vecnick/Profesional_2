package nikita.verhovod.profi.Controllers;

import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nikita.verhovod.profi.Controllers.payload.CreateStudentRequest;
import nikita.verhovod.profi.Controllers.payload.StudentDTO;
import nikita.verhovod.profi.Controllers.payload.UpdateStudentRequest;
import nikita.verhovod.profi.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDTO> create(
        @Valid @RequestBody CreateStudentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            studentService.create(
                request.name(),
                request.email(),
                request.groupId()
            )
        );
    }

    @GetMapping
    public List<StudentDTO> getAll() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable Integer id) {
        return studentService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(
        @PathVariable Integer id,
        @Valid @RequestBody UpdateStudentRequest request
    ) {
        return ResponseEntity.ok(
            studentService.update(
                id,
                request.name(),
                request.groupId()
            )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(params = "query")
    public List<StudentDTO> search(@RequestParam String query) {
        return studentService.search(query);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ProblemDetail> handleExceptions(ResponseStatusException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(ex.getStatusCode());
        problem.setDetail(ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(problem);
    }
}
