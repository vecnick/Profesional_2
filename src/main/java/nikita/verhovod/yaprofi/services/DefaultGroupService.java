package nikita.verhovod.yaprofi.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nikita.verhovod.yaprofi.data.Group;
import nikita.verhovod.yaprofi.repositories.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultGroupService implements GroupService{
    private final GroupRepository groupRepository;

    @Override
    public List<Group> findAll() {
            return groupRepository.findAll();
    }

    @Override
    @Transactional
    public Group create(String name, String description) {
        return groupRepository.save(new Group(null, name, description));
    }

    @Override
    public Optional<Group> find(int groupId) {
        return groupRepository.findById(groupId);
    }

    @Override
    @Transactional
    public void updateGroup(Integer id, String name, String description) {
        groupRepository.findById(id)
                .ifPresentOrElse(product -> {
                    product.setName(name);
                    product.setDescription(description);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteGroup(Integer id) {
        this.groupRepository.deleteById(id);
    }
}
