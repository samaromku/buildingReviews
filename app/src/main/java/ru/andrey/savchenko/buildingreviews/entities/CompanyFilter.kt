package ru.andrey.savchenko.buildingreviews.entities

data class CompanyFilter (
        val regions:List<Region>,
        val start:Int,
        val end:Int
)