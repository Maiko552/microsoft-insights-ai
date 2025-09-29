package br.com.maikonspo.microsoftAI.presentation.api.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Bean
    RestClient graphRestClient() {
        return RestClient.builder().baseUrl("https://graph.microsoft.com/v1.0").build();
    }

    @GetMapping("/me")
    public Map me(@RegisteredOAuth2AuthorizedClient("azure") OAuth2AuthorizedClient client,
                  RestClient graph) {
        var token = client.getAccessToken().getTokenValue();
        return graph.get().uri("/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve().body(Map.class);
    }




}
