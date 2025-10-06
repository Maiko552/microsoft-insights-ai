package br.com.maikonspo.microsoftAI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;

import java.util.List;

@Configuration
public class JwtConfig {

    @Bean
    JwtDecoder jwtDecoder(
            @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuer,
            OAuth2TokenValidator<Jwt> audienceValidator
    ) {
        NimbusJwtDecoder decoder = JwtDecoders.fromIssuerLocation(issuer);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator));
        return decoder;
    }

    @Bean
    OAuth2TokenValidator<Jwt> audienceValidator(
            @Value("${app.security.expected-audience:}") String expectedAud
    ) {
        return jwt -> {
            if (expectedAud == null || expectedAud.isBlank()) {
                return OAuth2TokenValidatorResult.success();
            }
            List<String> aud = jwt.getAudience();
            return (aud != null && aud.contains(expectedAud))
                    ? OAuth2TokenValidatorResult.success()
                    : OAuth2TokenValidatorResult.failure(
                    new OAuth2Error("invalid_token", "audience inválido", null)
            );
        };
    }
}
