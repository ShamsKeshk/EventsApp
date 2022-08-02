package com.example.viagogodemo.events.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.viagogodemo.databinding.FragmentFilterBottomSheetBinding
import com.example.viagogodemo.events.model.EventFilterCriteria
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [FilterBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class FilterBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var eventViewModel : EventsViewModel

    private lateinit var binding : FragmentFilterBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding =  FragmentFilterBottomSheetBinding.inflate(inflater, container, false)

        eventViewModel = ViewModelProvider(requireActivity())[EventsViewModel::class.java]



        eventViewModel.getEventFilterCriteria()?.let {
            binding.lastEventFilterCriteria = it
        }

        binding.btnApplyFilter.setOnClickListener {
            applyFilter()
        }

        binding.btnClearFilter.setOnClickListener {
            clearFilter()
        }

        return binding.root
    }

    private fun applyFilter(){
        val eventFilterCriteria = EventFilterCriteria()
        val cityValue =  binding.etCityFilterInputField.text.toString()
        val priceValue = binding.etPriceFilterInputField.text.toString()

      if (!cityValue.isNullOrEmpty())
          eventFilterCriteria.cityQuery = cityValue

        if(!priceValue.isNullOrEmpty() && priceValue.toDouble() > 0){
            eventFilterCriteria.price = priceValue.toDouble()
        }

        eventViewModel.applyFilter(eventFilterCriteria)

        dismiss()
    }

    private fun clearFilter(){
        eventViewModel.clearFilter()
        dismiss()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FilterBottomSheetFragment()
    }
}