package cz.developerthomas.issuetrackerjooq.core.fieldupdate

import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.context.annotation.Bean

@Bean
fun fieldUpdateModule(): SimpleModule = SimpleModule()
    .addDeserializer(
        FieldUpdate::class.java,
        FieldUpdateDeserializer(),
    )