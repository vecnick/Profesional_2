package nikita.verhovod.yaprofi.services;

import nikita.verhovod.yaprofi.data.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<Group> findAll();

    Group create(String name, String description);

    Optional<Group> find(int groupId);

    void updateGroup(Integer id, String name, String description);

    void deleteGroup(Integer id);
}
