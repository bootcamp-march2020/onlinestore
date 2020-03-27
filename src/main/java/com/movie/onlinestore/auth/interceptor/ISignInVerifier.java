package com.movie.onlinestore.auth.interceptor;

import com.movie.onlinestore.model.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Null;

public interface ISignInVerifier {

    boolean isValidToken(@Null String idToken);

    @Nullable
    User getUserInfo(@Nonnull String idToken);

    void onboardUser(@Nonnull User user);
}
