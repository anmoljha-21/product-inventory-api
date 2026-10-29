
package com.example.product_inventory_api;

import com.example.product_inventory_api.dto.AuthRequest;
import com.example.product_inventory_api.entity.Role;
import com.example.product_inventory_api.entity.User;
import com.example.product_inventory_api.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ProductInventoryApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void cleanDatabase() {
		userRepository.deleteAll();
	}

	@Test
	void registerAndLoginShouldReturnJwtToken() throws Exception {
		AuthRequest request = new AuthRequest();
		request.setEmail("integration-test@example.com");
		request.setPassword("StrongPass123");

		mockMvc.perform(post("/api/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated());

		mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").isNotEmpty())
				.andExpect(jsonPath("$.tokenType").value("Bearer"));
	}

	@Test
	void registrationShouldCreateCustomerRole() throws Exception {
		AuthRequest request = new AuthRequest();
		request.setEmail("customer-test@example.com");
		request.setPassword("StrongPass123");

		mockMvc.perform(post("/api/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated());

		User user = userRepository.findByEmail("customer-test@example.com")
				.orElseThrow();

		org.junit.jupiter.api.Assertions.assertEquals(Role.CUSTOMER, user.getRole());
		org.junit.jupiter.api.Assertions.assertTrue(
				passwordEncoder.matches("StrongPass123", user.getPassword()));
	}
}
