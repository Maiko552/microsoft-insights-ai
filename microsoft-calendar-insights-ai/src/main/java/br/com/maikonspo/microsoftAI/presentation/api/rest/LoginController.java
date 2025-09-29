package br.com.maikonspo.microsoftAI.presentation.api.rest;

import br.com.maikonspo.microsoftAI.application.dto.UserMeResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final RestClient graph;

    public LoginController(RestClient graphRestClient) {
        this.graph = graphRestClient;
    }

    @GetMapping("/me")
    public Object me(@RegisteredOAuth2AuthorizedClient("azure") OAuth2AuthorizedClient client) {
        String token = client.getAccessToken().getTokenValue();
        var me = graph.get()
                .uri("/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(Map.class);
        return new UserMeResponse(
                (String) me.get("id"),
                (String) me.get("displayName"),
                (String) me.get("mail")
        );
    }
}
