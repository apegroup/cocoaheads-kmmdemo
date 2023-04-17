package com.umain.cocoaheads.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RootResponse(
    @SerialName("data")
    val `data`: List<Data?>? = null,
    @SerialName("page")
    val page: Int? = null,
    @SerialName("per_page")
    val perPage: Int? = null,
    @SerialName("support")
    val support: Support? = null,
    @SerialName("total")
    val total: Int? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null,
) {
    @Serializable
    data class Data(
        @SerialName("avatar")
        val avatar: String? = null,
        @SerialName("email")
        val email: String? = null,
        @SerialName("first_name")
        val firstName: String? = null,
        @SerialName("id")
        val id: Int? = null,
        @SerialName("last_name")
        val lastName: String? = null,
    )

    @Serializable
    data class Support(
        @SerialName("text")
        val text: String? = null,
        @SerialName("url")
        val url: String? = null,
    )
}
