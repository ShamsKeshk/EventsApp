package com.example.viagogodemo.events.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.viagogodemo.databinding.ItemEventHomeBinding
import com.example.viagogodemo.events.model.Event

class EventsHomeAdapter(val eventClickListener: OnEventClickListener): ListAdapter<Event ,EventsHomeAdapter.EventViewHolder >(
    DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }




    inner class EventViewHolder(private val binding: ItemEventHomeBinding): RecyclerView.ViewHolder(binding.root){


        init {
            binding.root.setOnClickListener {
                eventClickListener.onEventClickListener(getItem(adapterPosition))
            }
        }

        fun bind(event: Event?) {
            binding.event = event
        }
    }

    companion object {
        private val DIFF_UTIL =  object: DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    interface OnEventClickListener {
        fun onEventClickListener(event: Event)
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }


}