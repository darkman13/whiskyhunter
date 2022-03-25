package house.brandstore.whiskyhunter

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@EnableBatchProcessing
@SpringBootApplication
@EnableRedisRepositories
class WhiskyhunterApplication

fun main(args: Array<String>) {
    runApplication<WhiskyhunterApplication>(*args)
}
