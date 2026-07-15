import org.testcontainers.containers.PostgreSQLContainer

private const val DOCKER_IMAGE = "postgres:17"

fun main() {
    PostgreSQLContainer<Nothing>(DOCKER_IMAGE).use { postgres ->
        postgres.start()

        migrateDatabase(postgres)
        generateJooq(postgres)
    }
}
