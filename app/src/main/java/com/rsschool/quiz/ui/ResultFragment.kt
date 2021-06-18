package com.rsschool.quiz.ui

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.rsschool.quiz.MainViewModel
import com.rsschool.quiz.R
import com.rsschool.quiz.Result
import com.rsschool.quiz.databinding.FragmentResultBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ResultFragment : BottomSheetDialogFragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.state
            .onEach (::getResult)
            .launchIn(lifecycleScope)
    }

    private fun getResult(result: Result){
        when(result){
            is Result.EmptyState -> { }
            is Result.Error -> {
                Toast.makeText(requireContext(), "There was something wrong", Toast.LENGTH_SHORT).show() }
            is Result.Success -> {
                binding.tvResults.text =
                    result.resultMap.values
                        .filter { it == 1 }
                        .size.toString()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}