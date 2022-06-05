package hu.indicium.dev.services.authentication.model

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class User(
    val username: String,
    val identifier: String,
    val email: String,
    val isActivated: Boolean? = false,
    val password: String?,
    @BsonId val id: Id<User> = newId()
)
