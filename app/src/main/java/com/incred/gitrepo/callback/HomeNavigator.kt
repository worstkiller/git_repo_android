package com.incred.gitrepo.callback

import com.incred.gitrepo.model.GitRepo

/**
 * Created by incred on 11/9/18.
 */
interface HomeNavigator {

    /**
     * routes the user flow to other screen
     */
    fun onClickNavigation(position: Int, repo: GitRepo)
}