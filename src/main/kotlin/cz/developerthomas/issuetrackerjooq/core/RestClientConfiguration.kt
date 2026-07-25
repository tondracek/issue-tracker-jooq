package cz.developerthomas.issuetrackerjooq.core

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class RestClientConfiguration {

    @Bean
    fun restClient(): RestClient =
        RestClient.builder().build()
}