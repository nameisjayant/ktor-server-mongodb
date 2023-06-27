package com.example.routings

import io.ktor.resources.*


@Resource("/register")
class RegisterRoute

@Resource("/login")
class LoginRoute

@Resource("/user")
class UserRoute {
    @Resource("/{id}")
    class Id(val parent: UserRoute = UserRoute(), val id: Long)
}

@Resource("/note")
class NoteRoute {
    @Resource("/{id}")
    class Id(val noteRoute: NoteRoute = NoteRoute(), val id: Long)
}