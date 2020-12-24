package model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import tables.PocketData

class Pocket(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Pocket>(PocketData)

    var client by Client referencedOn PocketData.client
    var game by Game referencedOn PocketData.game

}