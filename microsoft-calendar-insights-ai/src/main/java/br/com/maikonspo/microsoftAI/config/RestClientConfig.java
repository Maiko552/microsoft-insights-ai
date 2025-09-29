package br.com.maikonspo.microsoftAI.config;

import br.com.maikonspo.microsoftAI.infrastructure.exception.GraphApiException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient graphRestClient() {
        return RestClient.builder()
                .baseUrl("https://graph.microsoft.com/v1.0")
                .defaultStatusHandler(
                        s -> s.is4xxClientError() || s.is5xxServerError(),
                        (req, res) -> {
                            String text = "";
                            try {
                                if (res.getBody() != null) {
                                    text = new String(res.getBody().readAllBytes());
                                }
                            } catch (IOException e) {
                                text = "<erro ao ler corpo: " + e.getMessage() + ">";
                            }
                            throw new GraphApiException(res.getStatusCode().value(), text, "Falha ao chamar Graph");
                        }
                )
                .build();
    }
}
