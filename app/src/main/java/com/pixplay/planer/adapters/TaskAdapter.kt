package com.pixplay.planer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pixplay.planer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.task_item.view.*
import westroom.checkbook2.data.models.adapter.ModelTask

/**
 * Created by havil on 25.03.2018.
 */

open class TaskAdapter(val list: List<ModelTask>, private val listener: (ModelTask) -> Unit) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ModelTask, listener: (ModelTask) -> Unit) = with(itemView) {
            if(item.url != "") Picasso.get().load(item.url).into(task_item_image)
            task_item_title.text = item.title
            task_item_description.text = item.description
            setOnClickListener { listener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.task_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position], listener)

    override fun getItemCount() = list.size

}
