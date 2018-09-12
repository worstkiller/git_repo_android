package com.incred.gitrepo.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.incred.gitrepo.BaseActivity
import com.incred.gitrepo.R
import com.incred.gitrepo.model.GitRepo
import com.incred.gitrepo.utils.*
import kotlinx.android.synthetic.main.activity_details.*

/**
 * Created by incred on 11/9/18.
 */
class DetailsActivity : BaseActivity() {

    private lateinit var gitRepo: GitRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setToolbar()
        validatePackage()
        setRepoData(intent)
        setUiData()
        setShareListener()
        setupBackNavigation()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        title = getString(R.string.title_details)
    }

    /**
     * sets back button on action bar
     */
    private fun setupBackNavigation() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * listener for fab
     */
    private fun setShareListener() {
        fabShare.setOnClickListener { openShareIntent() }
    }

    /**
     * open share intent
     */
    private fun openShareIntent() {
        try {
            startActivity(Utility.getShareIntent(gitRepo.clone_url!!))
        } catch (exp: Exception) {
            exp.printStackTrace()
            showToast(getString(R.string.error_no_url_found))
        }
    }

    /**
     * sets the repo icon and other fields
     */
    private fun setUiData() {
        try {

            Glide.with(this)
                    .load(gitRepo.owner!!.avatar_url)
                    .apply(RequestOptions().circleCrop())
                    .apply(RequestOptions().placeholder(R.drawable.ic_github))
                    .into(ivRepoIcon)

            //git description
            tvRepoDescription.text = gitRepo.description
            //repo name
            tvRepoName.text = gitRepo.owner!!.login
            //repo title
            tvRepoTitle.text = gitRepo.name
            //repo issues count
            tvRepoIssuesCount.text = gitRepo.open_issues_count.toString()
            //repo forks count
            tvRepoForksCount.text = gitRepo.forks_count.toString()
            //repo stars count
            tvRepoStarsCount.text = gitRepo.stargazers_count.toString()
            //date for repo update
            tvRepoUpdateDate.text = getString(R.string.last_update,
                    Utility.getDateInFormatFor(DATE_FORMAT_REPO_UPDATED,
                            DATE_FORMAT_REPO_DAY_MONTH_YEAR,
                            gitRepo.updated_at!!))
            //language
            tvRepoLanguage.text = gitRepo.language
        } catch (exp: Exception) {
            exp.printStackTrace()
        }
    }

    /**
     * checks for the bundle data
     */
    private fun validatePackage() {
        if (!intent.hasExtra(EXTRA_BUNDLE_DATA)) {
            showToast(getString(R.string.error_data_not_found))
            finish()
        }
    }

    /**
     * sets the git data from intent
     */
    private fun setRepoData(intent: Intent?) {
        val bundle = intent!!.getBundleExtra(EXTRA_BUNDLE_DATA)
        gitRepo = bundle.getParcelable(EXTRA_BUNDLE_REPO)
    }

    /**
     * handle the action for menu back
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}