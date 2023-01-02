package com.tapark.military_quest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel>: Fragment() {

    lateinit var viewDataBinding: V
    abstract val viewModel: VM
    abstract val layout: Int

    private lateinit var backPressedCallback: OnBackPressedCallback

    override fun onStart() {
        super.onStart()
        backPressedCallback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)
    }

    override fun onStop() {
        super.onStop()
        backPressedCallback.remove()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layout, container, false)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        viewDataBinding.setVariable(BR.viewModel, viewModel)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObserver()
        initViews(savedInstanceState)
    }

    abstract fun onBackPressed()

    abstract fun addObserver()

    abstract fun initViews(savedInstanceState: Bundle?)

}