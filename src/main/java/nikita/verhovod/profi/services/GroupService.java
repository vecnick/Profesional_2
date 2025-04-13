package nikita.verhovod.profi.services;

import nikita.verhovod.profi.Controllers.payload.GroupDTO;
import nikita.verhovod.profi.Controllers.responces.GroupResponse;
import nikita.verhovod.profi.data.groups.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<GroupResponse> findAll();

    Group create(String name, Integer parentId);

    Optional<Group> findById(int groupId);

    GroupDTO updateGroup(Integer id, String name, Integer parentId);

    void deleteGroup(Integer id);

    List<GroupDTO> searchGroups(String query);

}
