import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import tables.*
import tables.PocketData

fun main() {
    Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver"
    )

    transaction {
        SchemaUtils.create(Admins, Clients, Games, GameStudios, PocketData, StoreData, ClientGames)


        SchemaUtils.drop(Admins, Clients, Games, GameStudios, PocketData, StoreData, ClientGames)
    }
}
