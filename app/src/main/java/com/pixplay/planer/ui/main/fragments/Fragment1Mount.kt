package com.unicornlight.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pixplay.planer.R
import com.pixplay.planer.adapters.TaskAdapter
import com.pixplay.planer.ui.main.MainActivity
import com.pixplay.planer.ui.main.fragments.FRAME
import westroom.checkbook2.data.models.adapter.ModelTask

class Fragment1Mount: Fragment() {

    var listModes: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.frame_main, container, false)
        init(rootView, savedInstanceState)
        return rootView
    }

    override fun onResume() {
        super.onResume()
    }

    fun init(rootView: View, savedInstanceState: Bundle?) {
        listModes = rootView.findViewById(R.id.frame_main_listModes)
    }

    fun setListModesAdapter(list: ArrayList<ModelTask>) {
        listModes?.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        listModes?.adapter = TaskAdapter(list) { it ->
            (activity as MainActivity).presenter.frame_main_actionTask(FRAME.FRAME1Mounth, it)
        }
    }

}
