package cz.developerthomas.issuetrackerjooq.sample

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Profile("dev")
@Component
class SampleDataGenerator(
    private val userGenerator: UserGenerator,
    private val taskGenerator: TaskGenerator
) {

    @EventListener(ApplicationReadyEvent::class)
    fun generate() {
        userGenerator.generate()
        taskGenerator.generate()
    }
}