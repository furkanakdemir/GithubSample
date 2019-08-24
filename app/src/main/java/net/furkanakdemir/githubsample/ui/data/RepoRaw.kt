package net.furkanakdemir.githubsample.ui.data


import com.google.gson.annotations.SerializedName

data class RepoRaw(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("name") val name: String?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("has_issues") val hasIssues: Boolean?,
    @SerializedName("language") val language: String?,
    @SerializedName("size") val size: Int?,
    @SerializedName("open_issues_count") val openIssuesCount: Int?,
    @SerializedName("stargazers_count") val stargazersCount: Int?,
    @SerializedName("owner") val owner: OwnerRaw = OwnerRaw()
) {
    data class OwnerRaw(
        @SerializedName("avatar_url") val avatarUrl: String? = "",
        @SerializedName("id") val id: Int? = -1,
        @SerializedName("login") val login: String? = ""
    )
}