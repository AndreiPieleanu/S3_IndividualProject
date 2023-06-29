package fontys.s3.andreipieleanu.controller;

import fontys.s3.andreipieleanu.domain.Address;
import fontys.s3.andreipieleanu.domain.Role;
import fontys.s3.andreipieleanu.domain.User;
import fontys.s3.andreipieleanu.domain.UserRole;
import fontys.s3.andreipieleanu.domain.requestresponse.address.CreateAddressRequest;
import fontys.s3.andreipieleanu.domain.requestresponse.address.CreateAddressResponse;
import fontys.s3.andreipieleanu.domain.requestresponse.address.GetAddressRequest;
import fontys.s3.andreipieleanu.domain.requestresponse.address.GetAddressResponse;
import fontys.s3.andreipieleanu.servicelayer.serviceimpl.AddressService;
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

import java.util.Map;

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
public class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AddressService addressService;
    private final User user =
            User.builder().id(1).userRole(UserRole.builder().id(1).role(Role.CLIENT).build()).firstName("User1").lastName("1").email("user1@gmail.com").build();
    @Test
    @WithMockUser(username = "user1@gmail.com", roles = {"CLIENT"})
    public void getAllAddressesOfUserShouldReturnStatus200_WhenAddressesExist() throws Exception {
        // Arrange 
        GetAddressResponse response = new GetAddressResponse(Map.of(
                1, 
                Address.builder().id(1).user(user).build(),
                2,
                Address.builder().id(2).user(user).build()
        ));
        GetAddressRequest request = new GetAddressRequest(1);
        when(addressService.getAllAddressesOfUserWithId(request)).thenReturn(response);
        // Act 
        mockMvc.perform(get("/addresses/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                    {
                        "foundAddresses":{"2":{"id":2,"streetName":null,"streetNumber":null,"zipCode":null,"city":null,"country":null,"phone":null,"user":{"id":1,"email":"user1@gmail.com","password":null,"firstName":"User1","lastName":"1","userRole":{"id":1,"role":"CLIENT"}}},"1":{"id":1,"streetName":null,"streetNumber":null,"zipCode":null,"city":null,"country":null,"phone":null,"user":{"id":1,"email":"user1@gmail.com","password":null,"firstName":"User1","lastName":"1","userRole":{"id":1,"role":"CLIENT"}}}}
                    }
                    """));
        // Assert 
        verify(addressService).getAllAddressesOfUserWithId(request);
    }
    @Test
    @WithMockUser(username = "user1@gmail.com", roles = {"CLIENT"})
    public void addAddressShouldReturnStatus200_WhenAddressIsNew() throws Exception {
        // Arrange 
        CreateAddressRequest request = new CreateAddressRequest(
                "street",
                1,
                "zipcode",
                "city",
                "country",
                "phone",
                user.getId()
        );
        CreateAddressResponse response = new CreateAddressResponse(1);
        when(addressService.createAddress(request)).thenReturn(response);
        // Act 
        MvcResult mvcResult = mockMvc.perform(post("/addresses")
                        .contentType(APPLICATION_JSON_VALUE).content(
                                """
                                                {
                                                    "streetName": "street",
                                                    "streetNumber": 1,
                                                    "zipCode": "zipcode",
                                                    "city": "city",
                                                    "country": "country",
                                                    "phone": "phone",
                                                    "clientId": 1
                                                }
                                        """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        // Assert 
        verify(addressService).createAddress(request);
        String expectedJson = "{\"id\":1}";
        String actualJson = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedJson, actualJson);
    }
}