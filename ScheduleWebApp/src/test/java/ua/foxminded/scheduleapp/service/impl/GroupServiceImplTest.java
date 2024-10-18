package ua.foxminded.scheduleapp.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.scheduleapp.model.Group;
import ua.foxminded.scheduleapp.repository.GroupRepository;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    private Group testGroup;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setGroupName("Test Group");
    }

    @Test
    void testCreateGroup() {
        when(groupRepository.save(any(Group.class))).thenReturn(testGroup);

        Group createdGroup = groupService.createGroup(testGroup);
        
        assertNotNull(createdGroup);
        assertEquals(testGroup.getId(), createdGroup.getId());
        assertEquals(testGroup.getGroupName(), createdGroup.getGroupName());

        verify(groupRepository, times(1)).save(testGroup);
    }

    @Test
    void testGetGroupById() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(testGroup));

        Optional<Group> foundGroup = groupService.getGroupById(1L);

        assertTrue(foundGroup.isPresent());
        assertEquals(testGroup.getId(), foundGroup.get().getId());
        assertEquals(testGroup.getGroupName(), foundGroup.get().getGroupName());

        verify(groupRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdOrThrow() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(testGroup));

        Group foundGroup = groupService.findByIdOrThrow(1L);

        assertNotNull(foundGroup);
        assertEquals(testGroup.getId(), foundGroup.getId());

        verify(groupRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateGroup() {
        when(groupRepository.existsById(1L)).thenReturn(true);
        when(groupRepository.save(testGroup)).thenReturn(testGroup);

        Group updatedGroup = groupService.updateGroup(1L, testGroup);

        assertNotNull(updatedGroup);
        assertEquals(testGroup.getGroupName(), updatedGroup.getGroupName());

        verify(groupRepository, times(1)).save(testGroup);
        verify(groupRepository, times(1)).existsById(1L);
    }

    @Test
    void testDeleteGroup() {
        when(groupRepository.existsById(1L)).thenReturn(true);

        groupService.deleteGroup(1L);

        verify(groupRepository, times(1)).existsById(1L);
        verify(groupRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByName() {
        when(groupRepository.findByGroupName("Test Group")).thenReturn(Optional.of(testGroup));

        Optional<Group> foundGroup = groupService.findByName("Test Group");

        assertTrue(foundGroup.isPresent());
        assertEquals(testGroup.getGroupName(), foundGroup.get().getGroupName());

        verify(groupRepository, times(1)).findByGroupName("Test Group");
    }
}
