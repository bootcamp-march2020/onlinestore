package com.movie.onlinestore.auth.interceptor;

import com.movie.onlinestore.HeaderConstants;
import com.movie.onlinestore.UrlConstants;
import com.movie.onlinestore.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginInterceptorTest {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

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

    @Test
    public void givenValidIdToken_ShouldReturnSuccessResponse() throws Exception {
        //Arrange

        final String VALID_ID_TOKEN = "valid_token";
        ISignInVerifier mockGoogleSignInVerifier = Mockito.mock(ISignInVerifier.class);

        LoginInterceptor loginInterceptor = new LoginInterceptor(mockGoogleSignInVerifier);

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("GET", "/api/movies/");
        mockHttpServletRequest.addHeader(HeaderConstants.HEADER_ID_TOKEN, VALID_ID_TOKEN);

        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        Mockito.when(mockGoogleSignInVerifier.isValidToken(VALID_ID_TOKEN)).thenReturn(true);
        Mockito.when(mockGoogleSignInVerifier.getUserInfo(VALID_ID_TOKEN)).thenReturn(new User("valid_id", "name", "email"));

        //Act
        loginInterceptor.preHandle(mockHttpServletRequest, mockHttpServletResponse, Mockito.any());

        //Assert
        Assertions.assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
    }
}
