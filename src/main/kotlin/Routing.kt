package ru.interlinkstudio

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.interlinkstudio.Model.ApplicationRequest

fun Application.configureRouting() {
    val telegram = TelegramService()

    routing {
        staticResources("/images", "static/images")

        post("/submit") {
            val body = call.receive<ApplicationRequest>()
            telegram.sendNotification(body.name, body.communicationAddress, body.message)
            call.respond(HttpStatusCode.OK, mapOf("status" to "ok"))
        }

    }
}
