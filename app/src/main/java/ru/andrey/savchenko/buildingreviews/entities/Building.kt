package entities

data class Building(
        val id: Int,
        val imageUrl: String,
        val title: String,
        val address: String,
        val classBuilding: String,
        val dateBuilding: String,
        val priceBuilding: String,
        val companyId:Int
)