package ua.foxminded.scheduleapp.service;

import ua.foxminded.scheduleapp.model.User;

import java.util.List;

public interface UserService {
	
    List<User> getAllUsers();
    
    void saveUser(User user);
    
    boolean isEmailTaken(String email);

    void deleteUserById(Long id);

    User findUserById(Long id);

	void updateUser(User user);
}
