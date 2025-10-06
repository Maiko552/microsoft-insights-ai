package br.com.maikonspo.microsoftAI.presentation.api.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class WhoAmIController {
    @PreAuthorize("hasAuthority('SCOPE_api.read')")
    @GetMapping("/whoami")
    public Map<String,Object> who(@AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return Map.of(
                "sub", jwt.getSubject(),
                "aud", jwt.getAudience(),
                "scp", jwt.getClaimAsString("scp")
        );
    }
}
