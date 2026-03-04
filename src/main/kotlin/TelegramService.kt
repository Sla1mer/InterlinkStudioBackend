package ru.interlinkstudio

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class TelegramService {

    private val token = System.getenv("TELEGRAM_TOKEN") ?: error("TELEGRAM_TOKEN не задан")
    private val chatId = System.getenv("TELEGRAM_CHAT_ID") ?: error("TELEGRAM_CHAT_ID не задан")

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) { json() }
        expectSuccess = true
    }

    suspend fun sendNotification(name: String, communicationAddress: String, message: String?) {
        val text = """
            🔔 Новая заявка!
            👤 Имя: $name
            📞 Адрес для связи: $communicationAddress
            💬 Сообщение: ${message ?: "—"}
        """.trimIndent()

        client.post("https://api.telegram.org/bot$token/sendMessage") {
            contentType(ContentType.Application.Json)
            setBody(mapOf(
                "chat_id" to chatId,
                "text" to text
            ))
        }
    }
}
