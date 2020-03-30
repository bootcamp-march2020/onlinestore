package com.movie.onlinestore.auth.interceptor;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.movie.onlinestore.model.User;
import com.movie.onlinestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Optional;

@Component
public class GoogleAuthenticator implements ISignInAuthenticator {

    //should move this to some config file
    private static final String CLIENT_ID = "936772320028-u2gh12om9ng67voal1mck32bgvobkcu7.apps.googleusercontent.com";

    @Autowired
    private UserRepository userRepository;

    private GoogleIdTokenVerifier mGoogleTokenVerifier;

    public GoogleAuthenticator() {

        mGoogleTokenVerifier =
                new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                        .setAudience(Collections.singleton(CLIENT_ID))
                        .build();
    }

    @Override
    public boolean isValidToken(@Nullable String idToken) {
        return null != idToken && !idToken.isEmpty();
    }

    @Nullable
    @Override
    public User getUserInfo(@Nonnull String idToken) {
        try {

            GoogleIdToken googleIdToken = mGoogleTokenVerifier.verify(idToken);

            if (null == googleIdToken)
                return null;

            return toUser(googleIdToken.getPayload());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onboardUser(@Nonnull User user) {
        Optional<User> userOptional = userRepository.findById(user.getUserId());
        if (!userOptional.isPresent())
            userRepository.save(user);
    }

    @Nullable
    private User toUser(GoogleIdToken.Payload payload) {

        String userId = payload.getSubject();

        if (null == userId)
            return null;

        String name = (String) payload.get("name");
        String email = null;

        Boolean emailVerified = payload.getEmailVerified();
        if (null != emailVerified && emailVerified)
            email = payload.getEmail();

        if (null == email)
            return null;

        if (null == name)//name can come as null.
            name = email;

        return new User(userId, name, email);
    }

}
