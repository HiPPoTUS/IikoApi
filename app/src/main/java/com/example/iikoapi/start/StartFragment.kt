package com.example.iikoapi.start

import android.app.AlertDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.util.DisplayMetrics
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.iikoapi.R
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.utils.LoadingState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_start.view.*
import javax.inject.Inject


@AndroidEntryPoint
class StartFragment: Fragment(R.layout.fragment_start) {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.arrow.setImageResource(R.drawable.ic_start_arrow)

        val dm = DisplayMetrics()
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(dm)
        val density = dm.scaledDensity

        val text1 = "Данное приложение \n поможет Вам сделать "
        val text2 = "предзаказ\n"
        val text3 = "на "
        val text4 = "самовывоз"

        val span1 = SpannableString(text1)
        span1.setSpan(
            AbsoluteSizeSpan(30 * density.toInt()),
            0,
            text1.length,
            SPAN_INCLUSIVE_INCLUSIVE
        )

        val span2 = SpannableString(text2)
        span2.setSpan(
            AbsoluteSizeSpan(40 * density.toInt()),
            0,
            text2.length,
            SPAN_INCLUSIVE_INCLUSIVE
        )

        val span3 = SpannableString(text3)
        span3.setSpan(
            AbsoluteSizeSpan(30 * density.toInt()),
            0,
            text3.length,
            SPAN_INCLUSIVE_INCLUSIVE
        )

        val span4 = SpannableString(text4)
        span4.setSpan(
            AbsoluteSizeSpan(40 * density.toInt()),
            0,
            text4.length,
            SPAN_INCLUSIVE_INCLUSIVE
        )

        view.text.text = TextUtils.concat(span1, span2, span3, span4)

        view.button.setOnClickListener {
            viewModel.getTerminals().observe(viewLifecycleOwner, Observer {loadingState ->

                when(loadingState){
                    is LoadingState.Loading -> {
                        view.button.isVisible = false
                        view.downloading.isVisible = true
                    }
                    is LoadingState.SuccessMenu -> {

                    }
                    is LoadingState.Error -> {
                        val error = loadingState.error
                        view.button.isVisible = false
                        view.downloading.isVisible = true
                        AlertDialog.Builder(context)
                            .setTitle(requireContext().resources.getString(R.string.start_fragment_error_title))
                            .setMessage(requireContext().resources.getString(R.string.start_fragment_error_subtitle))
                            .setPositiveButton(
                                android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show()
                    }
                    is LoadingState.SuccessTerminals -> {
                        val action = StartFragmentDirections.actionStartFragmentToContactsFragment(loadingState.terminals)
                        findNavController().navigate(action)
                    }
                }

            })
        }

    }

}