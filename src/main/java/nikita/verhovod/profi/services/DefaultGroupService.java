package nikita.verhovod.profi.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nikita.verhovod.profi.Controllers.payload.GroupDTO;
import nikita.verhovod.profi.Controllers.responces.GroupResponse;
import nikita.verhovod.profi.data.groups.Group;
import nikita.verhovod.profi.repositories.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DefaultGroupService implements GroupService {
    private final GroupRepository groupRepository;

    @Override
    public List<GroupResponse> findAll() {
        return groupRepository.findAllByParentIsNull().stream()
            .map(GroupResponse::from)
            .toList();
    }

    public Optional<Group> findById(int groupId) {
        return groupRepository.findById(groupId);
    }

    @Override
    @Transactional
    public Group create(String name, Integer parentId) {
        Group parent = parentId != null ?
            groupRepository.findById(parentId)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Parent group not found"))
            : null;

        Group group = new Group(name);
        group.setParent(parent);
        return groupRepository.save(group);
    }

    @Override
    @Transactional
    public GroupDTO updateGroup(Integer id, String name, Integer parentId) {
        Group group = groupRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (name != null) group.setName(name);

        if (parentId != null) {
            Group parent = groupRepository.findById(parentId)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "New parent group not found"));

            if (isCircularDependency(group, parent)) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Circular dependency detected");
            }

            group.setParent(parent);
        }

        return GroupDTO.from(groupRepository.save(group));
    }

    private boolean isCircularDependency(Group group, Group newParent) {
        Group current = newParent;
        while (current != null) {
            if (current.getId().equals(group.getId())) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }

    @Override
    @Transactional
    public void deleteGroup(Integer id) {
        Group group = groupRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!group.getSubGroups().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Cannot delete group with subgroups");
        }

        groupRepository.delete(group);
    }

    @Override
    public List<GroupDTO> searchGroups(String query) {
        return groupRepository.findByNameContainingIgnoreCase(query).stream()
            .map(GroupDTO::from)
            .toList();
    }
}
