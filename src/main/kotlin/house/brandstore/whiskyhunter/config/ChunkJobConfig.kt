package house.brandstore.whiskyhunter.config

import house.brandstore.whiskyhunter.domain.WhiskyData
import house.brandstore.whiskyhunter.domain.WhiskyDataRepository
import house.brandstore.whiskyhunter.domain.WhiskyItemReader
import house.brandstore.whiskyhunter.service.MasterOfMaltNewDataService
import net.thauvin.erik.bitly.Bitly
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChunkJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val masterOfMaltNewDataService: MasterOfMaltNewDataService,
    val whiskyDataRepository: WhiskyDataRepository
) {

    companion object {
        val bitly = Bitly("ca496cc1c33b8fb2021ed9e11bbd87fdf0cb8eb7")
        const val CUSTOM_READER_JOB = "CUSTOM_READER_JOB"
        const val CUSTOM_READER_JOB_STEP = CUSTOM_READER_JOB + "_STEP"
        const val CHUNK_SIZE = 10
    }

    @Bean
    fun customReaderJob(): Job {
        return jobBuilderFactory.get(CUSTOM_READER_JOB)
            .start(customReaderStep())
            .build()
    }

    @Bean
    fun customReaderStep(): Step {
        return stepBuilderFactory.get(CUSTOM_READER_JOB_STEP)
            .chunk<WhiskyData, WhiskyData>(CHUNK_SIZE)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build()
    }

    @Bean
    @StepScope
    fun reader(): WhiskyItemReader {
        return WhiskyItemReader()
    }

    @Bean
    fun processor(): ItemProcessor<WhiskyData, WhiskyData> {
        return ItemProcessor { it }
    }

    @Bean
    fun writer(): ItemWriter<WhiskyData> {
        val savedWhiskyList = whiskyDataRepository.findAll()
        return ItemWriter { list ->
            list.forEach { data ->
                if (!savedWhiskyList.contains(data) && data.onSale)
                    whiskyDataRepository.save(data.copy(url = bitly.bitlinks().shorten(data.url)))
            }
        }
    }
}
