package andrzej.calendar.utils

import andrzej.calendar.model.User
import andrzej.calendar.room.UserEntity
import javax.inject.Inject

class UserMapper
@Inject constructor(){

    fun mapFromEntity(entity: UserEntity): User{
        return User(
            entity.userId,
            entity.periodLength,
            entity.cycleLength
        )
    }

    fun mapToEntity(user: User): UserEntity{
        return UserEntity(
            user.userId,
            user.periodLength,
            user.cycleLength
        )
    }

}