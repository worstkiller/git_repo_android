package com.incred.gitrepo.ui.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.incred.gitrepo.R
import com.incred.gitrepo.callback.HomeNavigator
import com.incred.gitrepo.model.GitRepo
import com.incred.gitrepo.utils.DATE_FORMAT_REPO_UPDATED
import com.incred.gitrepo.utils.Utility

/**
 * Created by incred on 11/9/18.
 */
class HomeAdapter(navigationListener: HomeNavigator) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var listOfRepo: ArrayList<GitRepo>
    private val router = navigationListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_git_repo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (::listOfRepo.isInitialized) listOfRepo.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(listOfRepo.get(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivRepoIcon = itemView.findViewById<ImageView>(R.id.ivRepoIcon)
        val tvRepoTitle = itemView.findViewById<TextView>(R.id.tvRepoTitle)
        val tvRepoUserName = itemView.findViewById<TextView>(R.id.tvRepoUserName)
        val tvDaysAgo = itemView.findViewById<TextView>(R.id.tvDaysAgo)
        val tvStars = itemView.findViewById<TextView>(R.id.tvStars)

        init {
            itemView.setOnClickListener {
                router.onClickNavigation(adapterPosition, listOfRepo.get(adapterPosition))
            }
        }

        /**
         * this will bind the data with view elements
         */
        fun bindData(get: GitRepo) {

            tvRepoTitle.text = get.name
            tvRepoUserName.text = get.owner!!.login
            tvDaysAgo.text = Utility.getDateInFormatForRelativeTime(DATE_FORMAT_REPO_UPDATED, get.updated_at!!)
            tvStars.text = get.stargazers_count.toString()

            Glide.with(context)
                    .load(get.owner.avatar_url)
                    .apply(RequestOptions().placeholder(R.drawable.ic_github))
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivRepoIcon)

        }

    }

    /**
     * updates the data from viewmodel
     * @param listOfRepo
     */
    fun updateData(listOfRepo: ArrayList<GitRepo>) {
        this.listOfRepo = listOfRepo
        notifyDataSetChanged()
    }

    /**
     * clears the current data
     */
    fun clear() {
        if (this.listOfRepo != null) this.listOfRepo.clear()
        notifyDataSetChanged()
    }

}