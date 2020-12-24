package model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import repo.Item

open class Person(id: EntityID<Int>) : IntEntity(id)