package org.ssp.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ssp.security.TokenService;

public class TokenServiceTest {

    @Test
    public void commonTest() {
        TokenService tokenService = new TokenService();
        String login = "login";

        String token = tokenService.generateToken(login);
        boolean validate = tokenService.validateToken(token);
        String loginFromToken = tokenService.getLoginFromToken(token);

        Assertions.assertTrue(validate);
        Assertions.assertEquals(login, loginFromToken);
    }

}
