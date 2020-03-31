package com.movie.onlinestore.auth.interceptor;

import com.movie.onlinestore.HeaderConstants;
import com.movie.onlinestore.UrlConstants;
import com.movie.onlinestore.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private ISignInAuthenticator iSignInHelper;

    public LoginInterceptor(ISignInAuthenticator iSignInHelper) {
        this.iSignInHelper = iSignInHelper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String headerIDToken = request.getHeader(HeaderConstants.HEADER_ID_TOKEN);

        if (!iSignInHelper.isValidToken(headerIDToken)) {
            response.sendRedirect(UrlConstants.URL_PATH_UNAUTHORISED);
            return false;
        }

        User user = iSignInHelper.getUserInfo(headerIDToken);

        if (null == user) {//No Valid User found from the id-token.
            response.sendRedirect(UrlConstants.URL_PATH_INVALID_TOKEN);
            return false;
        }

        //User information found.

        iSignInHelper.onboardUser(user);

        request.setAttribute("user",user);

        return true;
    }

}
