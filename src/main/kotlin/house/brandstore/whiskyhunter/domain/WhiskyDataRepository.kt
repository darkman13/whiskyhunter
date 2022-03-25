package house.brandstore.whiskyhunter.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WhiskyDataRepository : CrudRepository<WhiskyData, String> {
    fun findAllByProductId(productId: Long): List<WhiskyData>
}
