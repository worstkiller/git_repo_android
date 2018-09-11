package com.incred.gitrepo.callback

import com.google.gson.JsonObject

/**
 * Created by incred on 11/9/18.
 */
open interface ServerResponse {

    //to receive data in model or POJO
    fun onModel(model: Any) {}

    //to receive data in json
    fun onJson(json: JsonObject) {}

    //to signify something went wrong while getting response
    fun onError() {}

    //to signify server call didn't made or is down
    fun onApiFailed() {}

}