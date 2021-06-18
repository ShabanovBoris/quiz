package com.rsschool.quiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.rsschool.quiz.MainViewModel
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentLaunchBinding
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.ui.pager.QuizBundle
import com.rsschool.quiz.ui.pager.ViewPagerRecyclerAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class LaunchFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentLaunchBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var pagerBinding: FragmentQuizBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpViewPager()
        viewModel.data
            .onEach(::updatePager)
            .launchIn(lifecycleScope)
    }


    private fun setUpViewPager() {
        val tableLayout = binding.tableLayout
        val viewPager = binding.viewPager
        //tab names
        val names = mapOf(
            0 to "First",
            1 to "Second",
            2 to "Third",
            3 to "Fourth",
            4 to "Fifth",
        )

        val adapter = ViewPagerRecyclerAdapter(pagerBinding)
        //set buttons listener into adapter
        adapter.addOnCheckedListener { checkedPosition, page ->
            viewModel.putAnswer(page, checkedPosition)
        }
        adapter.addOnSubmitListener {
            findNavController().navigate(R.id.action_launchFragment_to_resultFragment)
        }
        adapter.addOnNavigateListener { currentPosition, isNext ->
            when (isNext) {
                true -> viewPager.setCurrentItem(currentPosition + 1, true)
                false -> viewPager.setCurrentItem(currentPosition - 1, true)
            }
        }

        //disable all interactions
        viewPager.isUserInputEnabled = false
        tableLayout.shouldEnableTabs = false

        viewPager.adapter = adapter

        TabLayoutMediator(tableLayout, viewPager) { tab, pos ->
            tab.text = names[pos]
        }.attach()
    }

    private fun updatePager(data: QuizBundle) {
        (binding.viewPager.adapter as ViewPagerRecyclerAdapter).apply {
            setQuizData(data)
            notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        pagerBinding = null
    }


}