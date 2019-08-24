package net.furkanakdemir.githubsample.ui.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repo(
    val id: Int = -1,
    val name: String?,
    val fullName: String?,
    val description: String?,
    val hasIssues: Boolean?,
    val language: String?,
    val issuesCount: Int?,
    val starCount: Int?,
    var isFavorite: Boolean = false,
    val owner: Owner?
) : Parcelable {

    @Parcelize
    data class Owner(
        val id: Int?,
        val name: String?,
        val imageUrl: String?
    ) : Parcelable {
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
