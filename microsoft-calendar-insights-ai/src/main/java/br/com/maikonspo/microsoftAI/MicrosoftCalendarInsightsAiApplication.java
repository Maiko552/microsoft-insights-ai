package br.com.maikonspo.microsoftAI;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@SpringBootApplication
public class MicrosoftCalendarInsightsAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrosoftCalendarInsightsAiApplication.class, args);
	}



}
