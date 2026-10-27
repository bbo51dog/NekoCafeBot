package net.bbo51dog.nekocafebot.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import java.sql.Connection

object Database {

    private var dataSource: HikariDataSource? = null

    val connection: Connection
        get() = checkNotNull(dataSource) { "Connection has not been initialized" }.connection

    fun initialize() {
        val host = requireNotNull(System.getenv("DB_HOST"))
        val port = System.getenv("DB_PORT") ?: "3306"
        val name = requireNotNull(System.getenv("DB_NAME"))
        val user = requireNotNull(System.getenv("DB_USER"))
        val password = requireNotNull(System.getenv("DB_PASSWORD"))

        val jdbcUrl = "jdbc:mysql://$host:$port/$name?serverTimezone=Asia/Tokyo&characterEncoding=UTF-8"

        Flyway.configure()
            .dataSource(jdbcUrl, user, password)
            .locations("classpath:db/migration")
            .load()
            .migrate()
        val config = HikariConfig().apply {
            this.jdbcUrl = jdbcUrl
            username = user
            this.password = password
            maximumPoolSize = 10
            minimumIdle = 2
            connectionTimeout = 10_000
        }

        val newDataSource = HikariDataSource(config)
        newDataSource.connection.use { newConnection ->
            newConnection.createStatement().use { statement ->
                statement.executeQuery("SELECT 1").use { }
            }
        }
        dataSource = newDataSource
    }

    fun close() {
        dataSource?.close()
        dataSource = null
    }
}
