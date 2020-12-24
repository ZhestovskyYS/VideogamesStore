package tables

import org.jetbrains.exposed.dao.id.IntIdTable


object PocketData: IntIdTable() {
    val client = reference("Client", Clients)
    val game = reference("Game", Games)

    override val primaryKey =
        PrimaryKey(id, name = "PK_POCKET_ID")
}