import org.flywaydb.core.Flyway
import org.flywaydb.core.api.output.MigrateResult
import org.testcontainers.containers.PostgreSQLContainer

private const val MIGRATIONS_LOCATION = "filesystem:../src/main/resources/db/migration"

fun migrateDatabase(postgres: PostgreSQLContainer<Nothing>): MigrateResult =
    Flyway.configure()
        .dataSource(
            postgres.jdbcUrl,
            postgres.username,
            postgres.password,
        )
        .locations(MIGRATIONS_LOCATION)
        .load()
        .migrate()
