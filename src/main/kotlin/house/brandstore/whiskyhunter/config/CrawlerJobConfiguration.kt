package house.brandstore.whiskyhunter.config

import house.brandstore.whiskyhunter.service.MasterOfMaltDataService
import house.brandstore.whiskyhunter.service.MasterOfMaltNewDataService
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CrawlerJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val masterOfMaltNewDataService: MasterOfMaltNewDataService,
    private val masterOfMaltDataService: MasterOfMaltDataService
) {

//    @Bean
//    fun whiskyHunterJob(): Job {
//        return jobBuilderFactory
//            .get("simpleJob")
//            .start(simpleStep1())
//            .build()
//    }

    @Bean
    fun simpleStep1(): Step {
        return stepBuilderFactory
            .get("simpleStep1")
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
//                log.info(">>>>> This is Step1")
                masterOfMaltNewDataService.get().filter {
                    it.onSale
                }.forEach {
                    println(it)
                }

                println(">>>>>>>>>>>>>>>>>>>>>")
                println(">>>>>>>>>>>>>>>>>>>>>")

                masterOfMaltDataService.get("glenallachie-whisky-distillery").filter {
                    it.onSale
                }.forEach {
                    println(it)
                }

                println(">>>>>>>>>>>>>>>>>>>>>")
                println(">>>>>>>>>>>>>>>>>>>>>")

                masterOfMaltDataService.get("balvenie-whisky-distillery").filter {
                    it.onSale
                }.forEach {
                    println(it)
                }

                RepeatStatus.FINISHED
            }
            .build()
    }
}
