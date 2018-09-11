package com.incred.gitrepo.data

/**
 * Created by incred on 11/9/18.
 */
class Repository{

    companion object {

        /**
         * get a network manager for calling api
         */
        fun getApi(token:String?): WebService = NetworkManager(token).instance
    }
}