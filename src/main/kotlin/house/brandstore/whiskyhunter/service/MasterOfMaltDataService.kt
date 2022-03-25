package house.brandstore.whiskyhunter.service

import house.brandstore.whiskyhunter.domain.WhiskyData
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.stereotype.Service

@Service
class MasterOfMaltDataService : BaseDataService {
    companion object {
        const val SERVICE_TAG = "MasterOfMalt"
    }
    override val dataUrl = "https://www.masterofmalt.com/distilleries/"

    fun get(distilleryName: String): List<WhiskyData> {
        val doc: Document = getHtml(distilleryName)
        val contents: Elements = doc.select("body > form > section > div.container")
//        val whiskyDatas = contents.select("div:eq(3) div.js-product-box-wide")
//        println(whiskyDatas)
        val whiskyDatas = contents.select("div:eq(3) div.js-product-box-wide").map {
            WhiskyData(
                SERVICE_TAG + "_" + distilleryName,
                it.attr("data-productid").toLong(),
                it.attr("data-name"),
                it.attr("data-product-url"),
                it.select("div.product-box-wide-buy-wrapper > div.product-box-wide-price").text(),
                it.select("div.product-box-wide-image > img").attr("data-original"),
                !it.select("div.product-box-wide-buy-wrapper > a.mom-btn-buy").hasClass("is-disabled")
            )
        }
        return whiskyDatas
    }
}
