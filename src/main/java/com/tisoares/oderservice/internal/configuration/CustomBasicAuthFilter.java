package com.tisoares.oderservice.internal.configuration;

import com.tisoares.oderservice.internal.domain.User;
import com.tisoares.oderservice.internal.usecase.UserRetrieve;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.Optional;

@Component
public class CustomBasicAuthFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BASIC_AUTH_PREFIX = "Basic ";
    private static final String BASIC_AUth_DELIMITER = ":";

    private final PasswordEncoder passwordEncoder;
    private final UserRetrieve userRetrieve;

    public CustomBasicAuthFilter(PasswordEncoder passwordEncoder, UserRetrieve userRetrieve) {
        this.passwordEncoder = passwordEncoder;
        this.userRetrieve = userRetrieve;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String headerAuthorization = request.getHeader(AUTH_HEADER);
        if (headerAuthorization == null || !headerAuthorization.startsWith(BASIC_AUTH_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String basicToken = headerAuthorization.substring(BASIC_AUTH_PREFIX.length());
        byte[] basicTokenDecoded = Base64.getDecoder().decode(basicToken);
        String basicTokenValue = new String(basicTokenDecoded);
        String[] basicAuthsSplit = basicTokenValue.split(BASIC_AUth_DELIMITER);
        loadUser(basicAuthsSplit[0], basicAuthsSplit[1]);
        filterChain.doFilter(request, response);
    }

    private void loadUser(String username, String password) {
        Optional<User> user = userRetrieve.execute(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user.get().getEmail(), user.get().getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("USER")));
            authToken.setDetails(user);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
}
