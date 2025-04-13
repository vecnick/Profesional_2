package nikita.verhovod.profi.repositories;

import java.util.List;
import nikita.verhovod.profi.data.groups.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findAllByParentIsNull();
    List<Group> findByNameContainingIgnoreCase(String name);
}
