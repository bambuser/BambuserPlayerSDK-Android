package com.bambuser.liveshopping

import android.os.Parcelable
import com.bambuser.player.Configuration
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Parameters(
    val showId: String,
    val playerId: String = UUID.randomUUID().toString(),
    val configuration: ParcelableConfiguration,
) : Parcelable

@Parcelize
data class ParcelableConfiguration(
    val isEUServer: Boolean,
    val showHighlightedProducts: Boolean,
    val showChat: Boolean,
    val showProductsOnEndCurtain: Boolean,
    val enablePictureInPicture: Boolean,
    val enableProductDetailsPage: Boolean,
    val showShoppingCart: Boolean,
    val showLikes: Boolean,
    val preferredLocale: String = "en-US",
    val showNumberOfViewers: Boolean,
    val conversionTrackingTTLDays: Int = 30,
) : Parcelable {
    fun toConfiguration(): Configuration {
        return Configuration(
            isEUServer = isEUServer,
            showHighlightedProducts = showHighlightedProducts,
            showChat = showChat,
            showProductsOnEndCurtain = showProductsOnEndCurtain,
            enablePictureInPicture = enablePictureInPicture,
            enableProductDetailsPage = enableProductDetailsPage,
            showShoppingCart = showShoppingCart,
            showLikes = showLikes,
            preferredLocale = preferredLocale,
            showNumberOfViewers = showNumberOfViewers,
        )
    }
}
