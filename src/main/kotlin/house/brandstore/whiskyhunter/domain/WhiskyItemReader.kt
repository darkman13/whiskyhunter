package house.brandstore.whiskyhunter.domain

import house.brandstore.whiskyhunter.service.MasterOfMaltNewDataService
import org.springframework.batch.item.ItemReader
import org.springframework.beans.factory.annotation.Autowired
import javax.annotation.PostConstruct

open class WhiskyItemReader : ItemReader<WhiskyData> {
    @Autowired
    private lateinit var masterOfMaltNewDataService: MasterOfMaltNewDataService
    private lateinit var list: List<WhiskyData>
    private var nextIndex: Int = 0

    @PostConstruct
    fun postConstruct() {
        list = masterOfMaltNewDataService.get()
    }

    override fun read(): WhiskyData? {
        if (nextIndex < list.size) {
            return list[nextIndex++]
        }
        return null
    }
}
