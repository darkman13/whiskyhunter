package house.brandstore.whiskyhunter.domain

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.io.Serializable
import kotlin.jvm.internal.Intrinsics

@RedisHash("whisky_data")
data class WhiskyData(
    @Id
    val id: String,
    val classifyTag: String,
    @Indexed
    val productId: Long,
    val name: String,
    val url: String,
    val price: String,
    val imageUrl: String,
    val onSale: Boolean
) : Serializable {
    companion object {
        const val DELIMETER = ":"
    }

    @PersistenceConstructor
    constructor(
        classifyTag: String,
        productId: Long,
        name: String,
        url: String,
        price: String,
        imageUrl: String,
        onSale: Boolean
    ) : this(
        classifyTag + DELIMETER + productId,
        classifyTag,
        productId,
        name,
        url,
        price,
        imageUrl,
        onSale
    )

    override fun equals(other: Any?): Boolean {
        return if (this !== other) {
            if (other is WhiskyData) {
                val (id, price, onSale) = other
                return Intrinsics.areEqual(this.id, id) &&
                    Intrinsics.areEqual(this.price, price) &&
                    Intrinsics.areEqual(this.onSale, onSale)
            }
            false
        } else {
            true
        }
    }
}
