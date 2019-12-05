package com.pixplay.planer.data.models.adapter

data class ModelTask (

    var url : String = "",
    var title : String = "",
    var description : String = "",
    var status : String = "",
    var id : String = ""

) {

    fun getHashMap(): HashMap<String, Any> {
        val data = HashMap<String, Any>()
        data["url"] = url
        data["title"] = title
        data["description"] = description
        data["status"] = status
        return data
    }

}
