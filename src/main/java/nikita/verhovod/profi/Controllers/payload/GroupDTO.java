package nikita.verhovod.profi.Controllers.payload;

import nikita.verhovod.profi.data.groups.Group;

public record GroupDTO(Integer id, String name, Integer parent_id) {
    public static GroupDTO from(Group group) {
        return new GroupDTO(
            group.getId(),
            group.getName(),
            group.getParent() != null ? group.getParent().getId() : null
        );
    }
}
