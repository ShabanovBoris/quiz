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
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.rsschool.quiz.MainViewModel
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentLaunchBinding
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.ui.pager.QuizBundle
import com.rsschool.quiz.ui.pager.ViewPagerRecyclerAdapter
import com.rsschool.quiz.ui.utils.MenuManager
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
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        var viewPagerPosition = 0
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
            if (viewModel.currentAnswers.size < viewModel.data.replayCache[0].size) {
                Toast.makeText(
                    requireContext(),
                    "Aha! Your intention to abuse app naive developers was intercept!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                findNavController().navigate(R.id.action_launchFragment_to_resultFragment)
            }
        }
        adapter.addOnNavigateListener { currentPosition, isNext ->
            when (isNext) {
                true -> viewPager.setCurrentItem(currentPosition + 1, true)
                false -> viewPager.setCurrentItem(currentPosition - 1, true)
            }
        }
        //align adapter to viewpager
        viewPager.adapter = adapter


        //disable all interactions
        viewPager.isUserInputEnabled = false
        tabLayout.shouldEnableTabs = false
        MenuManager().handleMenuItemClick(binding)


        //menu navigation item click listener
        binding.toolbar.setNavigationOnClickListener {
            viewPager.setCurrentItem(viewPagerPosition - 1, true)
        }
        //hide navigation icon on first screen
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            val icon = binding.toolbar.navigationIcon
            override fun onPageSelected(position: Int) {
                viewPagerPosition = position
                if (position.equals(0)) {
                    binding.toolbar.navigationIcon = null
                } else {
                    binding.toolbar.navigationIcon = icon
                }
            }
        })



        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
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