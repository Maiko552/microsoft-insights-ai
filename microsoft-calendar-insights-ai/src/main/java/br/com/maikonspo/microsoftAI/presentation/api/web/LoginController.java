package br.com.maikonspo.microsoftAI.presentation.api.web;

import br.com.maikonspo.microsoftAI.application.dto.UserMeResponse;
import org.springframework.core.ParameterizedTypeReference;
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
    public UserMeResponse me(@RegisteredOAuth2AuthorizedClient("azure") OAuth2AuthorizedClient client) {
        String token = client.getAccessToken().getTokenValue();

        Map<String, Object> me = graph.get()
                .uri("/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        
        String id = (String) (me != null ? me.getOrDefault("id", "") : null);
        String displayName = (String) (me != null ? me.getOrDefault("displayName", "") : null);
        String mail = (String) (me != null ? me.getOrDefault("mail", me.getOrDefault("userPrincipalName", "")) : null);

        return new UserMeResponse(id, displayName, mail);
    }
}
