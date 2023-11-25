package kg.ticode.domain.network.dto

data class Hotel(
    val id: Int,
    val name: String,
    val adress: String,
    val about_the_hotel: AboutTheHotel,
    val image_urls: List<String>,
    val minimal_price: Int,
    val price_for_it: String,
    val rating: Int,
    val rating_name: String
)