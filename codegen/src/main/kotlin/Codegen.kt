import org.flywaydb.core.Flyway
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.*
import org.jooq.meta.jaxb.Target
import org.testcontainers.containers.PostgreSQLContainer

private const val DOCKER_IMAGE = "postgres:17"

private const val MIGRATIONS_LOCATION = "filesystem:../src/main/resources/db/migration"

private const val DB_DRIVER = "org.postgresql.Driver"

private val jooqGenerator: Generator = Generator()
    .withName("org.jooq.codegen.KotlinGenerator")
    .withDatabase(
        Database()
            .withName("org.jooq.meta.postgres.PostgresDatabase")
            .withInputSchema("public")
            .withExcludes("flyway_schema_history")
    )
    .withGenerate(
        Generate()
            .withJavaTimeTypes(true)
            .withPojos(true)
            .withImmutablePojos(true)
            .withKotlinNotNullPojoAttributes(true)
            .withKotlinNotNullRecordAttributes(true)
            .withKotlinNotNullInterfaceAttributes(true)
    )
    .withTarget(
        Target()
            .withPackageName("cz.developerthomas.issuetrackerjooq")
            .withDirectory("../build/generated-sources/jooq")
    )


fun main() {
    PostgreSQLContainer<Nothing>(DOCKER_IMAGE).use { postgres ->
        postgres.start()

        migrateDatabase(postgres)
        generateJooq(postgres)
    }
}

private fun migrateDatabase(postgres: PostgreSQLContainer<Nothing>) =
    Flyway.configure()
        .dataSource(
            postgres.jdbcUrl,
            postgres.username,
            postgres.password,
        )
        .locations(MIGRATIONS_LOCATION)
        .load()
        .migrate()


private fun generateJooq(postgres: PostgreSQLContainer<Nothing>) {
    val configuration = Configuration()
        .withJdbc(getJdbc(postgres))
        .withGenerator(jooqGenerator)

    GenerationTool.generate(configuration)
}


private fun getJdbc(postgres: PostgreSQLContainer<Nothing>) = Jdbc()
    .withDriver(DB_DRIVER)
    .withUrl(postgres.jdbcUrl)
    .withUser(postgres.username)
    .withPassword(postgres.password)