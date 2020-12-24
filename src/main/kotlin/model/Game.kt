package model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Game(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Game>(tables.Games)

    var name by tables.Games.name
    var description by tables.Games.description
    var cost by tables.Games.cost

    var studio by GameStudio referencedOn tables.Games.studioName
}