package com.rsschool.quiz.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rsschool.quiz.MainViewModel
import com.rsschool.quiz.R
import com.rsschool.quiz.Result
import com.rsschool.quiz.databinding.FragmentResultBinding
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
            .onEach(::getResult)
            .launchIn(lifecycleScope)


        binding.repeat.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_launchFragment)
            viewModel.clearStatistic()
        }
        binding.bShare.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, "Your result ${binding.tvResults.text}\n${viewModel.getStatistic()}")
                type = "*/*"

                if (resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(Intent.createChooser(this, "Share your progress, where you want"))
                }
            }
        }
        binding.bQuit.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun getResult(result: Result) {
        when (result) {
            is Result.EmptyState -> {
            }
            is Result.Error -> {
                Toast.makeText(requireContext(), "There was something wrong", Toast.LENGTH_SHORT)
                    .show()
            }
            is Result.Success -> {
                val points = result.resultMap.values
                    .filter { it == 1 }
                    .size.toString()

                binding.tvResults.text = "$points/${result.resultMap.values.size}"

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}