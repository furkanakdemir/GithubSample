package net.furkanakdemir.githubsample.ui.data

import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as Is

class RepoDomainMapperTest {

    private lateinit var repoDomainMapper: RepoDomainMapper

    @Before
    fun setUp() {
        repoDomainMapper = RepoDomainMapper()
    }

    @Test
    fun testValidRepo() {
        val repoRaw = RepoRaw(
            123, "name",
            "fullName",
            "description",
            false,
            "language",
            0,
            0,
            20, RepoRaw.OwnerRaw(
                "avatarUrl",
                1,
                "login"
            )
        )

        val expected = Repo(
            123, "name",
            "fullName",
            "description",
            false,
            "language",
            0,
            20,
            false, Repo.Owner(
                1,
                "login",
                "avatarUrl"
            )
        )

        assertThat(repoDomainMapper.map(repoRaw), Is(expected))
    }
}