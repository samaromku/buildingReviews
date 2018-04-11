package ru.andrey.savchenko.buildingreviews.entities.response

import pl.droidsonroids.jspoon.annotation.Selector

/**
 * Created by savchenko on 11.04.18.
 */
data class Page(
        @Selector("#developerContainer")val developerContainer:String

)