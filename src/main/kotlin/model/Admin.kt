package model

import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Admin(id: EntityID<Int>) : Person(id) {
    companion object : IntEntityClass<Admin>(tables.Admins)

    var name by tables.Admins.name
    var income by tables.Admins.income

    fun setStore(name: String) {
        Store.new {
            this.name = name
        }
    }

    fun setPerson(marker: String, name: String){
        when (marker) {
            "Client" -> Client.new {
                this.name = name
                this.valet = 0F
            }
            "GameStudio" -> GameStudio.new {
                this.name = name
                this.income = 0.0
            }
            "Admin" -> Admin.new {
                this.name = name
                this.income = 0.0
            }
        }
    }
}