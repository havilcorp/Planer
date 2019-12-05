package com.unicornlight.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pixplay.planer.R
import com.pixplay.planer.adapters.TaskAdapter
import com.pixplay.planer.ui.main.MainActivity
import com.pixplay.planer.ui.main.fragments.FRAME
import com.pixplay.planer.data.models.adapter.ModelTask

class FragmentGood: Fragment() {

    var listModes: RecyclerView? = null
    var adapter = TaskAdapter {
        (activity as MainActivity).presenter.frame_main_actionTaskMenu(FRAME.FRAMEGoods, it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.frame_main, container, false)
        init(rootView, savedInstanceState)
        return rootView
    }

    fun init(rootView: View, savedInstanceState: Bundle?) {
        listModes = rootView.findViewById(R.id.frame_main_listModes)

        rootView.findViewById<FloatingActionButton>(R.id.frame_main_actionAdd).setOnClickListener {
            (activity as MainActivity).presenter.frame_main_actionAdd(FRAME.FRAMEGoods)
        }

        listModes?.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        listModes?.adapter = adapter
    }

    fun setListModesAdapter(list: ArrayList<ModelTask>) {
        adapter.setList(list)
    }

    fun addToList(modelTask: ModelTask) {
        adapter.add(modelTask)
        updateCount()
    }

    fun modiferToList(modelTask: ModelTask) {
        adapter.modifer(modelTask)
        updateCount()
    }

    fun removeToList(modelTask: ModelTask) {
        adapter.remove(modelTask)
        updateCount()
    }

    fun updateCount() {
        (activity as MainActivity).presenter.updateCountListGood(adapter.itemCount)
    }

}
