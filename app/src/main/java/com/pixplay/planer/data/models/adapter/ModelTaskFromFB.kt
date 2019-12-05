package westroom.checkbook2.data.models.adapter

import com.google.firebase.firestore.DocumentChange
import com.pixplay.planer.data.models.adapter.ModelTask

data class ModelTaskFromFB (

    val type: DocumentChange.Type,
    val modelTask: ModelTask

)
