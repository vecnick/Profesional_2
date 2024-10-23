package nikita.verhovod.yaprofi.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import nikita.verhovod.yaprofi.data.Group;
import nikita.verhovod.yaprofi.services.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class GroupRestController {
    private final GroupService groupService;

    @GetMapping("/groups")
    @Operation(summary = "test")
    public List<Group> findProducts() {
        return this.groupService.findAll();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception,
                                                                      Locale locale) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, " "));
    }
}
