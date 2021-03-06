package com.devonfw.module.security.jwt.common.api;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import com.devonfw.module.security.common.api.authentication.AdvancedAuthentication;

import io.jsonwebtoken.Claims;

/**
 * Test of {@link JwtAuthenticator}.
 */
public class JwtAuthenticatorTest extends JwtComponentTest {

  @Inject
  private JwtAuthenticator jwtAuthenticator;

  /**
   * Test of {@link JwtAuthenticator#authenticate(String)}.
   */
  @Test
  public void testDo() {

    // prepare
    adjustClock();

    // given
    String token = TEST_JWT;

    // when
    Authentication authentication = this.jwtAuthenticator.authenticate(token);

    // then
    assertThat(authentication.getName()).isEqualTo(TEST_LOGIN);
    assertThat(AdvancedAuthentication.getPermissions(authentication))
        .containsExactlyInAnyOrder(TEST_ROLE_READ_MASTER_DATA, TEST_ROLE_SAVE_USER);
    assertThat(authentication).isInstanceOf(AdvancedAuthentication.class);
    assertThat(authentication.getCredentials()).isEqualTo(token);
    AdvancedAuthentication advancedAuthentication = (AdvancedAuthentication) authentication;
    assertThat((Number) advancedAuthentication.getAttribute(Claims.EXPIRATION)).isEqualTo(1587742156);

    // reset/cleanup
    resetClock();
  }

}
