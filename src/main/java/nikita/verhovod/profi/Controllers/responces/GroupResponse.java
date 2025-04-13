package nikita.verhovod.profi.Controllers.responces;

import java.util.List;
import nikita.verhovod.profi.data.groups.Group;

public record GroupResponse(Integer id, String name, List<GroupResponse> subGroups) {
    public static GroupResponse from(Group group) {
        List<GroupResponse> subGroups = group.getSubGroups().stream()
            .map(GroupResponse::from)
            .toList();
        return new GroupResponse(group.getId(), group.getName(), subGroups);
    }
}
