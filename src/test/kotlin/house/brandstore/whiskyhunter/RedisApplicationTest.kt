package house.brandstore.whiskyhunter

import house.brandstore.whiskyhunter.domain.WhiskyData
import house.brandstore.whiskyhunter.domain.WhiskyDataRepository
import org.junit.After
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class RedisApplicationTest {
    @Autowired
    private lateinit var whiskyDataRepository: WhiskyDataRepository

    @After
    @Throws(Exception::class)
    fun tearDown() {
        whiskyDataRepository.deleteAll()
    }

    @Test
    fun `기본_등록_조회기능`() {
        // given
        val data = WhiskyData(
            classifyTag = "MasterOfMaltNew",
            productId = 115909L,
            name = "Bunnahabhain \"Plum Tobacco\" (cask 4451) - The Cooper's Choice (The Vintage Malt Whisky Co.)",
            url = "https://bit.ly/3uoRJK6",
            price = "93,842.98",
            imageUrl = "https://www.masterofmalt.com/whiskies/p-2812/bunnahabhain/bunnahabhain-plum-tobacco-cask-4451-the-coopers-choice-the-vintage-malt-whisky-co-whisky.jpg",
            onSale = false
        )

        // when
        whiskyDataRepository.save(data)

        // then
        val findData = whiskyDataRepository.findAllByProductId(115909L).first()
        assert(findData.productId == 115909L)
    }
}
