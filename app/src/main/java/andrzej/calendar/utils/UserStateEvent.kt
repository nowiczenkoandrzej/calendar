package andrzej.calendar.utils

sealed class UserStateEvent {

    object UserRegisteredEvent: UserStateEvent()

    object UserNotRegisteredEvent: UserStateEvent()

}