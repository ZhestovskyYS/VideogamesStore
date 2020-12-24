package model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedCollection

class Store(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<Store>(tables.StoreData)

    var name by tables.StoreData.name

    fun getGameByName(name: String) =
        Game.all().find { it.name == name }


    fun addGameToPocket(gameName: String, clientName: String){
        val client = Client.all().find { it.name == clientName }?:return
        val game = Game.all().find { it.name == gameName }?:return
        if (Pocket.all().find{ it.client == client && it.game == game} == null)
            Pocket.new {
                this.client = client
                this.game = game
            }
    }

    fun addGamesToPocket(gamesNames: Array<String>, clientName: String){
        for (i in gamesNames.indices) {
            val game = Game.all().find { it.name == gamesNames[i] }?:return
            val client = Client.all().find { it.name == clientName }?:return
            if (Pocket.all().find { it.client == client && it.game == game } == null)
                Pocket.new {
                    this.game = game
                    this.client = client
                }
        }
    }

    fun deleteGameFromPocket(gameName: String, clientName: String){
        Pocket.all().find { it.game.name == gameName &&
                it.client.name == clientName}?.delete()
    }

    fun buyGamesFromPocket(clientName: String, admin: Admin){
        val client = Client.all().find { it.name == clientName }?:return
        val pocket = Pocket.all().filter { it.client == client }
        for (i in pocket.indices) {
            if (client.valet >= pocket[i].game.cost) {
                client.valet -= pocket[i].game.cost
                client.libraryOfGames =
                    SizedCollection( client.libraryOfGames  + listOf(pocket[i].game))
                admin.income += pocket[i].game.cost * 0.3
                val studio = GameStudio.all().find { it.name == pocket[i].game.studio.name}?:return
                studio.income += pocket[i].game.cost * 0.7
                deleteGameFromPocket(pocket[i].game.name, clientName)
            }
            else {
                println("You have not enough money in your valet to buy ${pocket[i].game.name}")
                break
            }
        }

    }
}