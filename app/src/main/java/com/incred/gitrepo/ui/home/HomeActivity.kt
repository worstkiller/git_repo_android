package com.incred.gitrepo.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.incred.gitrepo.BaseActivity
import com.incred.gitrepo.R
import com.incred.gitrepo.callback.HomeNavigator
import com.incred.gitrepo.model.GitRepo
import com.incred.gitrepo.ui.HomeViewModeFactory
import com.incred.gitrepo.ui.details.DetailsActivity
import com.incred.gitrepo.utils.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.include_error_layout.*

class HomeActivity : BaseActivity(), HomeNavigator {

    private lateinit var viewModel: HomeViewModel
    //default search topic
    private var SEARCH_TOPIC_DEFAULT = Utility.getTopicBuilder(SEARCH_TOPIC_ANDROID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupViewModel()
        setupRecyclerView()
        observeErrorStatus()
        observeProgressChange()
        manageClickListener()
        getRepoList()
    }

    /**
     * gets the repo list
     */
    private fun getRepoList() {
        viewModel.getGitRepo(SEARCH_TOPIC_DEFAULT, SEARCH_SORT_STARS, SEARCH_ORDER_DSC)
    }

    /**
     * handles the error try again button click
     */
    private fun manageClickListener() {
        btTryAgain.setOnClickListener {
            viewModel.getGitRepo(SEARCH_TOPIC_DEFAULT, SEARCH_SORT_STARS, SEARCH_ORDER_DSC)
        }
    }

    /**
     * manages the progress dialog
     */
    private fun observeProgressChange() {
        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading == true) {
                showProgress(getString(R.string.progress_default_msg))
            } else {
                hideProgress()
            }
        })
    }

    /**
     * this observes the list getting fetched from git api
     */
    private fun observeErrorStatus() {
        viewModel.errorStatus.observe(this, Observer { errorStatus -> manageErrorStatus(errorStatus) })
    }

    /**
     * manages the error state
     * @param errorStatus
     */
    private fun manageErrorStatus(errorStatus: Int?) {
        if (errorStatus != null) {
            clErrorView.visibility = View.VISIBLE
            tvErrorStatus.text = getString(errorStatus)
            showToast(getString(errorStatus))
        } else {
            clErrorView.visibility = View.GONE
        }
    }

    /**
     * sets up view model
     */
    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, HomeViewModeFactory(this)).get(HomeViewModel::class.java)
    }

    /**
     * set up the recyclerview
     */
    private fun setupRecyclerView() {
        rvGitRepo.setHasFixedSize(true)
        rvGitRepo.layoutManager = LinearLayoutManager(this)
        rvGitRepo.adapter = viewModel.adapter
    }

    /**
     * here handle the home navigation click
     * @param position
     * @param repo
     */
    override fun onClickNavigation(position: Int, repo: GitRepo) {
        val bundle = Bundle()
        bundle.putParcelable(EXTRA_BUNDLE_REPO, repo)
        openActivity(DetailsActivity::class.java, bundle)
    }

    /**
     * menu inflation
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        createSearchMenu(menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * search menu for searching in repo
     * @param menu
     */
    private fun createSearchMenu(menu: Menu?) {
        val menuSearch = menu!!.findItem(R.id.menuSearch)
        val searchView = menuSearch.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (searchView.isIconified) {
                    searchView.isIconified = false
                    menuSearch.collapseActionView()
                }
                manageSearchQuery(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                manageSearchQuery(newText)
                return false
            }

        })
    }

    /**
     * this manages the seach view
     * @param query
     */
    private fun manageSearchQuery(query: String?) {
        viewModel.sortResults(query!!)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Utility.hideKeyboardFrom(this, window.decorView)
        when (item!!.itemId) {
            R.id.menuBackend -> {
                val topics = Utility.getTopicBuilder(SEARCH_TOPIC_NODE)
                viewModel.getGitRepo(topics, SEARCH_SORT_STARS, SEARCH_ORDER_DSC)
            }
            R.id.menuFrontEnd -> {
                val topics = Utility.getTopicBuilder(SEARCH_TOPIC_ANGULAR)
                viewModel.getGitRepo(topics, SEARCH_SORT_STARS, SEARCH_ORDER_DSC)
            }
            R.id.menuAndroid -> {
                viewModel.getGitRepo(SEARCH_TOPIC_DEFAULT, SEARCH_SORT_STARS, SEARCH_ORDER_DSC)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
