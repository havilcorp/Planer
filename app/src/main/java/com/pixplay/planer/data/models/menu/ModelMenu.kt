package com.pixplay.planer.data.models.menu

import androidx.fragment.app.Fragment
import com.pixplay.planer.ui.main.fragments.FRAME

data class ModelMenu (
    var fragment: Fragment = Fragment(),
    var frame: FRAME = FRAME.FRAME10Years,
    var name: String = "",
    var title: String = ""
)