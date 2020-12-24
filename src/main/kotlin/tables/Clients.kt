package tables

import org.jetbrains.exposed.dao.id.IntIdTable


object Clients: IntIdTable()  {
    val name = varchar("name", length = 50)
    val valet = float("valet")

    override val primaryKey =
        PrimaryKey(id, valet, name = "PK_CLIENT_ID")
}