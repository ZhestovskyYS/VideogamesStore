package tables

import org.jetbrains.exposed.dao.id.IntIdTable

object ClientGames: IntIdTable() {
    val game = reference("Game", Games)
    val client = reference("Client", Clients)

    override  val primaryKey =
            PrimaryKey(client, game, name = "PK_STORE_GAMES_ID")
}