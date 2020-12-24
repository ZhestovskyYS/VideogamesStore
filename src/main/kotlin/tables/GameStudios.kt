package tables

import org.jetbrains.exposed.dao.id.IntIdTable

object GameStudios: IntIdTable()  {
    val name = varchar("name", length = 50)
    val income = double("income")

    override val primaryKey =
        PrimaryKey(id, income, name = "PK_GAMESTUDIO_ID")
}