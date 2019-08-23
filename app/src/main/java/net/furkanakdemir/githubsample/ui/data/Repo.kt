package net.furkanakdemir.githubsample.ui.data


data class Repo(
    val id: Int?,
    val name: String?,
    val fullName: String?,
    val description: String?,
    val hasIssues: Boolean?,
    val language: String?,
    val issuesCount: Int?,
    val starCount: Int?,
    var isFavorite: Boolean = false,
    val owner: Owner?
) {
    data class Owner(
        val id: Int?,
        val name: String?,
        val imageUrl: String?
    ) {
        override fun toString(): String {
            return "Owner(id=$id, name=$name, imageUrl=$imageUrl)"
        }
    }

    override fun toString(): String {
        return "Repo(id=$id, name=$name, fullName=$fullName, description=$description, " +
                "hasIssues=$hasIssues, language=$language, issuesCount=$issuesCount, " +
                "starCount=$starCount, owner=$owner)\n"
    }
}