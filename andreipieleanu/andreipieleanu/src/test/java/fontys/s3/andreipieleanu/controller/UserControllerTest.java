package fontys.s3.andreipieleanu.controller;

import fontys.s3.andreipieleanu.domain.*;
import fontys.s3.andreipieleanu.domain.requestresponse.user.*;
import fontys.s3.andreipieleanu.servicelayer.serviceimpl.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    
    @Test
    @WithMockUser(username = "admin1@gmail.nl", roles = {"WORKER"})
    public void getAllUserShouldReturnListOfUsers_WhenUsersExist() throws Exception {
        // Arrange 
        GetAllUsersResponse responseDTO = new GetAllUsersResponse(
                List.of(
                        User.builder().id(1).userRole(UserRole.builder().id(1).role(Role.CLIENT).build()).firstName("User1").lastName("1").email("user1@gmail.com").build(),
                        User.builder().id(2).userRole(UserRole.builder().id(1).role(Role.CLIENT).build()).firstName("User2").lastName("2").email("user2@gmail.com").build()
                )
        );
        when(userService.getAllUsers()).thenReturn(responseDTO);
        // Act 
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                    {
                        "allUsers":[{"id":1,"email":"user1@gmail.com","password":null,"firstName":"User1","lastName":"1","userRole":{"id":1,"role":"CLIENT"}},{"id":2,"email":"user2@gmail.com","password":null,"firstName":"User2","lastName":"2","userRole":{"id":1,"role":"CLIENT"}}]
                    }
                    """));
        // Assert 
        verify(userService).getAllUsers();
    }

    @Test
    public void createUserShouldReturnStatus200Created_WhenUserIsCreated() throws Exception {
        // Arrange 
        CreateNewUserRequest request = new CreateNewUserRequest(
                "jdoe@gmail.com",
                "John",
                "Doe",
                "jdoe123"
        );
        CreateNewUserResponse response = new CreateNewUserResponse("User has been created successfully!");
        when(userService.createNewClientAccount(request)).thenReturn(response);
        // Act 
        MvcResult mvcResult = mockMvc.perform(post("/users")
                        .contentType(APPLICATION_JSON_VALUE).content(
                                """
                                {
                                    "email": "jdoe@gmail.com",
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "password": "jdoe123"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // Assert 
        verify(userService).createNewClientAccount(request);
        String expectedJson = "{\"message\":\"User has been created successfully!\"}";
        String actualJson = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedJson, actualJson);
    }
}