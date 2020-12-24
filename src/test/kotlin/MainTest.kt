package tables

import model.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Test
import kotlin.test.assertEquals

class MainTest {

    @Test
    fun setStoreTest(){
        Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver"
        )
        transaction {
            SchemaUtils.create(Admins, StoreData)

            val admin = Admin.new {
                this.name = "Yan"
                this.income = 0.0
            }

            admin.setStore("ViolentGames")
            assertEquals(1, Store.count())

            SchemaUtils.drop(Admins, StoreData)
        }
    }

    @Test
    fun setPersonTest(){
        Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver"
        )
        transaction {
            SchemaUtils.create(Admins, Clients, GameStudios)

            val admin = Admin.new {
                this.name = "Yan"
                this.income = 0.0
            }

            admin.setPerson("Client", "ZiMax")
            admin.setPerson("Client", "Embir")
            admin.setPerson("GameStudio", "Blizzard")
            admin.setPerson("Admin", "Fanila")

            assertEquals(2, Client.count())
            assertEquals(2, Admin.count())
            assertEquals(1, GameStudio.count())

            SchemaUtils.drop(Admins, Clients, GameStudios)
        }
    }

    @Test
    fun setGameTest(){
        Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver"
        )
        transaction {
            SchemaUtils.create( Games, GameStudios)

            val gameStudio = GameStudio.new {
                this.name = "Valve"
                this.income = 0.0
            }

            gameStudio.addGame("Doka Dva",
                10F, "",
                gameStudio)

            gameStudio.addGame("CS Bang",
                13F, "",
                gameStudio)

            assertEquals(2, Game.count())

            gameStudio.deleteGame("Doka Dva", gameStudio)

            assertEquals(1, Game.count())

            SchemaUtils.drop( Games, GameStudios)
        }
    }

    @Test
    fun getGameByTest(){
        Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver"
        )
        transaction {
            SchemaUtils.create(Games, GameStudios, StoreData)

            val store = Store.new { this.name = "ViolentGames" }
            val studio = GameStudio.new {
                this.name = "Ubisoft"
                this.income = 0.0
            }
            GameStudio.new {
                this.name = "Valve"
                this.income = 0.0
            }
            val game = Game.new {
                this.name = "Assasin's Creed"
                this.cost = 16F
                this.description = ""
                this.studio = studio
            }

            assertEquals(game,store.getGameByName("Assasin's Creed"))

            SchemaUtils.drop(Games, GameStudios, StoreData)
        }
    }

    @Test
    fun setPocketTest(){
        Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver"
        )
        transaction {
            SchemaUtils.create(Clients, Games, GameStudios,  PocketData, StoreData, ClientGames)

            Client.new {
                this.name = "ZiMax"
                this.valet = 0F
            }
            val studio = GameStudio.new {
                this.name = "Ubisoft"
                this.income = 0.0
            }
            Game.new {
                this.name = "Assasin's Creed"
                this.cost = 16F
                this.description = ""
                this.studio = studio
            }
            Game.new {
                this.name = "Doka Dva"
                this.cost = 13F
                this.description = ""
                this.studio = studio
            }
            val store = Store.new { this.name = "ViolentGames" }

            store.addGameToPocket("Assasin's Creed", "ZiMax")
            store.addGameToPocket("Doka Dva", "ZiMax")

            assertEquals(2, Pocket.count())

            store.deleteGameFromPocket("Doka Dva", "ZiMax")

            assertEquals(1, Pocket.count())

            store.deleteGameFromPocket("Assasin's Creed", "ZiMax")
            store.addGamesToPocket(arrayOf("Assasin's Creed", "Doka Dva"), "ZiMax")

            assertEquals(2, Pocket.count())

            SchemaUtils.drop(Clients, Games, GameStudios,  PocketData, StoreData, ClientGames)
        }
    }

    @Test
    fun buyGamesFromPocketTest(){
        Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver"
        )
        transaction {
            SchemaUtils.create(Admins, Clients, Games, GameStudios,  PocketData, StoreData, ClientGames)

            val admin = Admin.new {
                this.name = "Yan"
                this.income = 0.0
            }
            val studio = GameStudio.new {
                this.name = "UnderClouds"
                this.income = 0.0
            }
            val client = Client.new {
                this.name = "ZiMax"
                this.valet = 19F
            }
            Game.new {
                this.name = "Borders was reached"
                this.cost = 19F
                this.description = ""
                this.studio = studio
            }
            Game.new {
                this.name = "Doka Dva"
                this.cost = 13F
                this.description = ""
                this.studio = studio
            }
            val store = Store.new { this.name = "ViolentGames" }
            store.addGamesToPocket(arrayOf("Borders was reached", "Doka Dva"), "ZiMax")
            store.buyGamesFromPocket("ZiMax", admin)

            assertEquals(1, client.libraryOfGames.count())
            assertEquals(5.7, admin.income)
            assertEquals(13.299999999999999, studio.income)

            SchemaUtils.drop(Admins, Clients, Games, GameStudios,  PocketData, StoreData, ClientGames)
        }
    }
}