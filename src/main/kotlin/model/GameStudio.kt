package model

import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class GameStudio(id: EntityID<Int>) : Person(id) {
    companion object : IntEntityClass<GameStudio>(tables.GameStudios)

    var name by tables.GameStudios.name
    var income by tables.GameStudios.income

    fun addGame(name: String,
                cost: Float, description: String,
                studio: GameStudio){
        Game.new {
            this.name = name
            this.cost = cost
            this.description = description
            this.studio = studio
        }
    }

    fun deleteGame(name: String,
                   studio: GameStudio){
        Game.all().find {
            it.name == name &&
            it.studio == studio
        }?.delete()
    }
}