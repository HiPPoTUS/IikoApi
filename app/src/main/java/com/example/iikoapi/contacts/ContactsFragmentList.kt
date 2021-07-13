package com.example.iikoapi.contacts

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.iikoapi.R
import com.example.iikoapi.databinding.FragmentContactsListBinding
import com.example.iikoapi.databinding.LayoutForProductBinding
import com.example.iikoapi.entities.ChildRemove
import com.example.iikoapi.entities.ParentRemove
import com.example.iikoapi.entities.menu.Modifier
import com.example.iikoapi.entities.start.District
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.utils.LoadingState
import com.example.iikoapi.utils.adapters.GeneralAdapter
import com.example.iikoapi.utils.OnItemClickListener
import com.example.iikoapi.utils.adapters.ExpandableAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_contacts_list.view.*
import javax.inject.Inject

@AndroidEntryPoint
class ContactsFragmentList(private val downloading: View? = null) : Fragment(R.layout.fragment_contacts_list) {

    private lateinit var binding: FragmentContactsListBinding
    private var districts = listOf<District>()

    @Inject
    lateinit var viewModel: MainViewModel

    private val adapter = GeneralAdapter<District>()
        .apply {
            setData(listOf<District>())
            setLayoutId(R.layout.contacts_list_item)
            setListener(object : OnItemClickListener<District>{
                override fun onClick(view: View, item: District, position: Int) {
                    val action = ContactsFragmentDirections.actionContactsFragmentToMainFragment(item)
                    findNavController().navigate(action)
                }

            })
        }

    private val expAdapter =  ExpandableAdapter<ChildRemove>().apply {
        setChildLayoutId(R.layout.item_child_district)
        setParentLayoutId(R.layout.item_header_remove)
        listener = object : OnItemClickListener<ChildRemove>{
            override fun onClick(view: View, item: ChildRemove, position: Int) {
                viewModel.getMenuResponse().observe(viewLifecycleOwner, Observer{ loadingState ->
                    when(loadingState){
                        is LoadingState.Loading -> {
                            downloading?.isVisible = true
                        }
                        is LoadingState.SuccessMenu -> {
                            val menu = loadingState.menu
                            val action = ContactsFragmentDirections.actionContactsFragmentToMainFragment(District(adr = "adress number -> 3"))
                            findNavController().navigate(action)
                        }
                        is LoadingState.Error -> {
                            val error = loadingState.error
                            downloading?.isVisible = false
                            AlertDialog.Builder(context)
                                .setTitle(requireContext().resources.getString(R.string.start_fragment_error_title))
                                .setMessage(requireContext().resources.getString(R.string.start_fragment_error_subtitle))
                                .setPositiveButton(
                                    android.R.string.yes, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show()
                        }
                        is LoadingState.SuccessTerminals -> {

                        }
                    }
                })
            }

        }
    }

    fun setDistricts(districts: List<District>){
        adapter.setData(districts)
        adapter.notifyDataSetChanged()
        expAdapter.items = del.toMutableList()
    }

//    private val placesMappedList = places.map {
//        it.value
//    }
//
//    private val placesNames = mutableListOf<District>().also {
//        placesMappedList.forEach {l ->
//            it.addAll(l)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contactsRecyclerView.adapter = expAdapter.apply {
            context = requireContext()
        }

    }

    private val items = List<Modifier>(5){
        Modifier(name = "mod $it", id = it.toString(), parentGroup = it.toString(), price = "0")
    }

    val ch = List(5) {
        ChildRemove(item = items[it])
    }
    val del = MutableList(5) {
        ParentRemove(children = ch, parent = "Убрать $it")
    }

}
