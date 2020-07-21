package com.example.springsecurityjwt;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.springsecurityjwt.service.MyUserDetailsService;
import com.example.springsecurityjwt.util.JwtUtil;

import ch.qos.logback.core.util.ContentTypeUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloResourceTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	MyUserDetailsService myUsersDetailsService;

	@Test
	public void shouldNotAllowedUnAuthorizedAccess() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/hello")).andExpect(status().isForbidden());
	}

	@Test
    public void existingUserCanGetTokenAndAuthenticated() throws Exception {
        UserDetails userDetails = myUsersDetailsService.loadUserByUsername("foo"); 
		String token = jwtUtil.generateToken(userDetails);

        assertNotNull(token);
        mvc.perform(MockMvcRequestBuilders.get("/hello").header("Authorization", "Bearer "+token)).andExpect(status().isOk());
    }
	@Test
	public void nonExistingUserCannotGetToken() throws Exception {
		String username = "test";
		String password = "testPassword123";
		String body = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
		mvc.perform(MockMvcRequestBuilders.post("/authenticate")
					.header("Content-Type", MediaType.APPLICATION_JSON)
					.content(body))
					.andExpect(status().isForbidden()).andReturn();
	}
}
