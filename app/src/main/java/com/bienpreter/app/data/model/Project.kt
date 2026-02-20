package com.bienpreter.app.data.model

import com.google.gson.annotations.SerializedName

data class Project(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("borrower") val borrower: String?,
    @SerializedName("amount") val amount: Double?,
    @SerializedName("duration") val duration: Int?,
    @SerializedName("rate") val rate: Double,
    @SerializedName("rating") val rating: String?,
    @SerializedName("progress") val progress: Double,
    @SerializedName("status") val status: String,
    @SerializedName("category") val category: String,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("isAutoPilot") val isAutoPilot: Boolean = false,
    @SerializedName("description") val description: String? = null,
    @SerializedName("collecteEndDate") val collecteEndDate: String? = null
)

data class ProjectStats(
    val upcoming: Int,
    val inFunding: Int,
    val inRepayment: Int,
    val fullyRepaid: Int
)

enum class ProjectStatus {
    UPCOMING,
    IN_FUNDING,
    FUNDING_COMPLETE,
    IN_REPAYMENT,
    REPAID
}

enum class ProjectCategory(val label: String) {
    ALL("Tous"),
    PRODUCTS_SERVICES("Produits & Services"),
    REAL_ESTATE("Immobilier"),
    CRAFTS_BTP("Artisanat & BTP"),
    AGRICULTURE("Agriculture"),
    TECHNOLOGY("Technologie")
}
