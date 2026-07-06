package cz.developerthomas.issuetrackerjooq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IssueTrackerJooqApplication

fun main(args: Array<String>) {
    runApplication<IssueTrackerJooqApplication>(*args)
}
