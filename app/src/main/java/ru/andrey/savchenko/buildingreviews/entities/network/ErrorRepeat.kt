package ru.andrey.savchenko.buildingreviews.entities.network

/**
 * Created by savchenko on 27.05.18.
 */
data class ErrorRepeat(val error: ErrorResponse,
                  val repeat: () -> Unit)