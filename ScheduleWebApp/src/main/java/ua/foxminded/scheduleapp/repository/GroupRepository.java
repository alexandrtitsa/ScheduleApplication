package ua.foxminded.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.scheduleapp.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
