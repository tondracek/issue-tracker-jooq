package cz.developerthomas.issuetrackerjooq.core.jackson

import cz.developerthomas.issuetrackerjooq.core.fieldupdate.FieldUpdate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tools.jackson.databind.module.SimpleModule

@Configuration
class JacksonConfiguration {

    @Bean
    fun fieldUpdateModule(): SimpleModule = SimpleModule().addDeserializer(
        FieldUpdate::class.java,
        FieldUpdateDeserializer()
    )
}