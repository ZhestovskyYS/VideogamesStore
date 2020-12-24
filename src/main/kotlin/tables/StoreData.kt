package tables

import org.jetbrains.exposed.dao.id.IntIdTable

object StoreData: IntIdTable() {
    var name = varchar("name", length = 50)

    override val primaryKey =
            PrimaryKey(id, name = "PK_STORE_ID")
}
