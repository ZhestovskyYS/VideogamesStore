package model

import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import tables.ClientGames


class Client (id: EntityID<Int>) : Person(id) {
    companion object : IntEntityClass<Client>(tables.Clients)

    var name by tables.Clients.name
    var valet by tables.Clients.valet

    var libraryOfGames by Game via ClientGames

}