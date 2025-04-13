package nikita.verhovod.profi.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nikita.verhovod.profi.Controllers.payload.GroupDTO;
import nikita.verhovod.profi.Controllers.payload.NewGroupPayload;
import nikita.verhovod.profi.Controllers.payload.UpdateGroupPayload;
import nikita.verhovod.profi.Controllers.responces.GroupResponse;
import nikita.verhovod.profi.data.groups.Group;
import nikita.verhovod.profi.services.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupRestController {
    private final GroupService groupService;

    @Operation(summary = "Создать группу",
        description = "Добавление группы с возможностью указания названия (name) и идентификатора"
            + " группы родителя (parent_id)")
    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(
        @Valid @RequestBody NewGroupPayload payload
    ) {
        Group group = groupService.create(payload.name(), payload.parentId());
        return ResponseEntity.status(HttpStatus.CREATED).body(GroupDTO.from(group));
    }

    @Operation(summary = "Получить все группы")
    @GetMapping
    public List<GroupResponse> getAllGroups() {
        return groupService.findAll();
    }

    @Operation(summary = "Получить группу по id")
    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Integer id) {
        return ResponseEntity.of(groupService.findById(id).map(GroupDTO::from));
    }

    @Operation(summary = "Обновить группу")
    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> updateGroup(
        @PathVariable Integer id,
        @Valid @RequestBody UpdateGroupPayload payload
    ) {
        return ResponseEntity.ok(
            groupService.updateGroup(id, payload.name(), payload.parentId())
        );
    }

    @Operation(summary = "Удалить группу")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Integer id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Поиск по запросу")
    @GetMapping("/search")
    public List<GroupDTO> searchGroups(
        @RequestParam("query") String query
    ) {
        return groupService.searchGroups(query);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception,
                                                                      Locale locale) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, " "));
    }
}
