package nikita.verhovod.yaprofi.repositories;

import nikita.verhovod.yaprofi.data.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

}
