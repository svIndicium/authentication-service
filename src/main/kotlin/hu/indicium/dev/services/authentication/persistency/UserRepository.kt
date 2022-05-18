package hu.indicium.dev.services.authentication.persistency

import hu.indicium.dev.mongodb.BaseRepository
import hu.indicium.dev.mongodb.MongoConfig
import hu.indicium.dev.services.authentication.model.User
import jakarta.inject.Singleton
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.eq

@Singleton
class UserRepository(
    client: CoroutineClient,
    mongoConfig: MongoConfig,
) : BaseRepository<User>(client, mongoConfig.database ?: "users", User::class) {

    suspend fun findByIdentifier(identifier: String): User? =
        collection.findOne(User::identifier eq identifier)

    suspend fun findByUsername(username: String): User? =
        collection.findOne(User::username eq username)
}