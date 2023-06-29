package fontys.s3.andreipieleanu.controller;

import fontys.s3.andreipieleanu.domain.requestresponse.login.LoginRequest;
import fontys.s3.andreipieleanu.domain.requestresponse.login.LoginResponse;
import fontys.s3.andreipieleanu.servicelayer.serviceimpl.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LoginService loginService;
    
    @Test
    public void loginWithExistentCredentialsShouldReturnToken_WhenUserExists() throws Exception {
        // Arrange 
        LoginRequest request = new LoginRequest("jdoe@gmail.com", "jdoe123");
        LoginResponse response = new LoginResponse("Bearer 123");
        when(loginService.login(request)).thenReturn(response);
        // Act 
        MvcResult mvcResult = mockMvc.perform(post("/login").contentType(APPLICATION_JSON_VALUE).content(
                """
                        {
                                    "email": "jdoe@gmail.com",
                                    "password": "jdoe123"
                        }
                        """))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andReturn();
        // Assert 
        verify(loginService).login(request);
        String expectedJson = "{\"accessToken\":\"Bearer 123\"}";
        String actualJson = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedJson, actualJson);
    }
}