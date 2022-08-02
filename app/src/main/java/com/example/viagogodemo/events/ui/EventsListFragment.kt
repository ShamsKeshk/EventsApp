package com.example.viagogodemo.events.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viagogodemo.R
import com.example.viagogodemo.databinding.FragmentEventsListBinding
import com.example.viagogodemo.events.model.Event
import com.example.viagogodemo.events.model.EventRetryCallback
import com.example.viagogodemo.events.model.Result
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [EventsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class EventsListFragment : Fragment() , EventsHomeAdapter.OnEventClickListener {

    companion object {
        @JvmStatic
        fun newInstance() = EventsListFragment()
    }

    private lateinit var binding: FragmentEventsListBinding

    private lateinit var eventsHomeAdapter: EventsHomeAdapter

    private lateinit var eventViewModel : EventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentEventsListBinding.inflate(inflater, container, false)

        eventViewModel = ViewModelProvider(requireActivity())[EventsViewModel::class.java]

        binding.retryCallback = object : EventRetryCallback {
            override fun retry() {
                eventViewModel.syncEvents()
            }
        }


        binding.clFilterSection.root.setOnClickListener {
            displayFilterByBottomSheet()
        }

        initView()
        initEventObserver()

        return binding.root
    }

    private fun initView(){
        eventsHomeAdapter = EventsHomeAdapter(this)

        binding.rvEventsList.adapter = eventsHomeAdapter
    }

    private fun initEventObserver(){
        eventViewModel.getEventLiveData().observe(viewLifecycleOwner, Observer {

            binding.eventFilterCriteria = eventViewModel.getEventFilterCriteria()

            binding.result = it

            when(it){
                is Result.Success -> {
                    eventsHomeAdapter.submitList(it.data)

                    handleEmptyState(it.data)
                }
                else -> {
                    //TODO handle any other Scenarios
                }
            }
        })
    }

    private fun handleEmptyState(events: List<Event>?){

        if (events.isNullOrEmpty()){
            binding.clFilterEmptyState.btnClearFilter.setOnClickListener {
                eventViewModel.clearFilter()
            }
        }

    }

    private fun displayFilterByBottomSheet(){
        activity?.let {
            val filterBottomSheetFragment = FilterBottomSheetFragment()

            filterBottomSheetFragment.apply {
                show(it.supportFragmentManager, tag)
            }
        }

    }

    override fun onEventClickListener(event: Event) {
        Toast.makeText(requireContext(), "Event ${event.name} was clicked", Toast.LENGTH_LONG).show()
    }
}