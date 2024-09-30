package ua.foxminded.scheduleapp.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.scheduleapp.model.User;
import ua.foxminded.scheduleapp.service.UserService;

import java.util.Arrays;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testShowAdminPanel() throws Exception {
		User user1 = new User();
		user1.setId(1L);
		user1.setFirstName("John");
		user1.setLastName("Doe");

		User user2 = new User();
		user2.setId(2L);
		user2.setFirstName("Jane");
		user2.setLastName("Doe");

		Mockito.when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

		mockMvc.perform(MockMvcRequestBuilders.get("/admin/panel")).andExpect(status().isOk())
				.andExpect(view().name("admin/panel")).andExpect(model().attributeExists("users"))
				.andExpect(model().attribute("users", Arrays.asList(user1, user2)));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testEditUser() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setFirstName("John");
		user.setLastName("Doe");

		Mockito.when(userService.findUserById(1L)).thenReturn(user);

		mockMvc.perform(MockMvcRequestBuilders.get("/admin/edit/1")).andExpect(status().isOk())
				.andExpect(view().name("admin/edit-user")).andExpect(model().attributeExists("user"))
				.andExpect(model().attribute("user", user));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testUpdateUser() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setFirstName("John");
		user.setLastName("Doe");

		Mockito.doNothing().when(userService).updateUser(any(User.class));

		mockMvc.perform(post("/admin/edit/1").flashAttr("user", user).with(csrf()))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/panel"));

		Mockito.verify(userService, Mockito.times(1)).updateUser(any(User.class));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testDeleteUser() throws Exception {

		Mockito.doNothing().when(userService).deleteUserById(1L);

		mockMvc.perform(post("/admin/delete/1").with(csrf())).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/admin/panel"));

		Mockito.verify(userService, Mockito.times(1)).deleteUserById(1L);
	}
}
