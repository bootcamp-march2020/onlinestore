package com.movie.onlinestore.controllers;

import com.movie.onlinestore.UrlConstants;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void callingUnAuthEndPoint_ShouldReturnUnAuthorisedStatusCode() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(UrlConstants.URL_PATH_UNAUTHORISED)
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void callingEndPointWithInvalidToken_ShouldReturnUnAuthorisedStatusCode() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(UrlConstants.URL_PATH_INVALID_TOKEN)
        ).andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

}
