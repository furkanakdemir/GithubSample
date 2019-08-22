package net.furkanakdemir.githubsample.ui.data


import com.google.gson.annotations.SerializedName

data class RepoRaw(
    @SerializedName("description") val description: String?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("has_issues") val hasIssues: Boolean?,
    @SerializedName("id") val id: Int?,
    @SerializedName("language") val language: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("open_issues_count") val openIssuesCount: Int?,
    @SerializedName("owner") val owner: OwnerRaw?,
    @SerializedName("size") val size: Int?,
    @SerializedName("stargazers_count") val stargazersCount: Int?
) {
    data class OwnerRaw(
        @SerializedName("avatar_url") val avatarUrl: String?,
        @SerializedName("id") val id: Int?,
        @SerializedName("login") val login: String?,
        @SerializedName("url") val url: String?
    )
}