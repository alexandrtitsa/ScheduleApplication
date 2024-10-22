package ua.foxminded.scheduleapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.scheduleapp.model.Group;
import ua.foxminded.scheduleapp.repository.GroupRepository;
import ua.foxminded.scheduleapp.service.GroupService;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Group findByIdOrThrow(Long id) {
        return groupRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Group with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    @Transactional
    public Group updateGroup(Long id, Group group) {
        if (!groupRepository.existsById(id)) {
            throw new IllegalArgumentException("Group with id " + id + " not found");
        }
        group.setId(id);
        return groupRepository.save(group);
    }

    @Override
    @Transactional
    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new IllegalArgumentException("Group with id " + id + " not found");
        }
        groupRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Group> findByName(String groupName) {
        return groupRepository.findByGroupName(groupName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getGroupIdsByCourseId(Long courseId) {
        return groupRepository.findGroupIdsByCourseId(courseId);
    }
}
