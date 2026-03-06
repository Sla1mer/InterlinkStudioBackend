import io.ktor.server.application.*
import io.ktor.server.plugins.origin
import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.routing.*
import kotlin.time.Duration.Companion.seconds
import io.ktor.server.request.*

fun Application.configureRateLimiting() {
    install(RateLimit) {
        global {
            rateLimiter(limit = 10, refillPeriod = 60.seconds)

            requestKey { call ->
                call.request.origin.remoteAddress
            }
        }
    }
}
