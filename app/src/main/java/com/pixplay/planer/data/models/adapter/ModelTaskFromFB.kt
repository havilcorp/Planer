package westroom.checkbook2.data.models.adapter

import com.google.firebase.firestore.DocumentChange

data class ModelTaskFromFB (

    val type: DocumentChange.Type,
    val modelTask: ModelTask

)
