package house.brandstore.whiskyhunter.service

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

interface BaseDataService {
    // WHISKY_DATAS_URL
    val dataUrl: String

    fun getHtml(): Document {
        return Jsoup.connect(dataUrl).get()
    }

    fun getHtml(distilleryName: String): Document {
        return Jsoup.connect(dataUrl + distilleryName).get()
    }

    fun getContentFromHtml(document: Document, cssQuery: String): Elements {
        return document.select(cssQuery)
    }
}
