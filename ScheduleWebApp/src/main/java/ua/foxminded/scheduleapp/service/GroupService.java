package ua.foxminded.scheduleapp.service;

import ua.foxminded.scheduleapp.model.Group;
import java.util.List;
import java.util.Optional;

public interface GroupService {

    Optional<Group> getGroupById(Long id);

    Group findByIdOrThrow(Long id);

    Group createGroup(Group group);

    Group updateGroup(Long id, Group group);

    void deleteGroup(Long id);

    List<Group> getAllGroups();

	Optional<Group> findByName(String groupName);
	
    List<Long> getGroupIdsByCourseId(Long courseId);
}
