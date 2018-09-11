package com.incred.gitrepo.model

/**
 * Created by incred on 11/9/18.
 */
data class GitRepoResponse(val total_count: Int,
                           val incomplete_results: Boolean,
                           val items: ArrayList<GitRepo>)

data class GitRepo(val id: Int,
                   val node_id: String,
                   val name: String,
                   val full_name: String,
                   val owner: RepoOwner,
                   val html_url: String,
                   val description: String,
                   val clone_url: String,
                   val stargazers_count: Int,
                   val language: String,
                   val forks_count: Int,
                   val updated_at: String,
                   val open_issues_count: Int,
                   val score: Float)

data class RepoOwner(val login: String,
                     val avatar_url: String)
