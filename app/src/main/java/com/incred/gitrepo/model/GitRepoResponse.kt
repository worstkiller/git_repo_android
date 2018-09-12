package com.incred.gitrepo.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by incred on 11/9/18.
 */
data class GitRepoResponse(val total_count: Int,
                           val incomplete_results: Boolean,
                           val items: ArrayList<GitRepo>)

data class GitRepo(val id: Int?,
                   val node_id: String?,
                   val name: String?,
                   val full_name: String?,
                   val owner: RepoOwner?,
                   val html_url: String?,
                   val description: String?,
                   val clone_url: String?,
                   val stargazers_count: Int?,
                   val language: String?,
                   val forks_count: Int?,
                   val updated_at: String?,
                   val open_issues_count: Int?,
                   val score: Float) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(RepoOwner::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readFloat()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        id?.let { parcel.writeInt(it) }
        parcel.writeString(node_id)
        parcel.writeString(name)
        parcel.writeString(full_name)
        parcel.writeParcelable(owner, flags)
        parcel.writeString(html_url)
        parcel.writeString(description)
        parcel.writeString(clone_url)
        stargazers_count?.let { parcel.writeInt(it) }
        parcel.writeString(language)
        forks_count?.let { parcel.writeInt(it) }
        parcel.writeString(updated_at)
        open_issues_count?.let { parcel.writeInt(it) }
        parcel.writeFloat(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GitRepo> {
        override fun createFromParcel(parcel: Parcel): GitRepo {
            return GitRepo(parcel)
        }

        override fun newArray(size: Int): Array<GitRepo?> {
            return arrayOfNulls(size)
        }
    }

}

data class RepoOwner(val login: String,
                     val avatar_url: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(login)
        parcel.writeString(avatar_url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RepoOwner> {
        override fun createFromParcel(parcel: Parcel): RepoOwner {
            return RepoOwner(parcel)
        }

        override fun newArray(size: Int): Array<RepoOwner?> {
            return arrayOfNulls(size)
        }
    }
}
