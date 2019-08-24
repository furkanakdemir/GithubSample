package net.furkanakdemir.githubsample.ui.data

open class RepoDomainMapper {

    open fun map(repoRaw: RepoRaw): Repo {
        with(repoRaw) {
            return Repo(
                id, name, fullName, description, hasIssues, language, openIssuesCount,
                stargazersCount, owner = Repo.Owner(
                    owner.id,
                    owner.login,
                    owner.avatarUrl
                )
            )
        }
    }
}
