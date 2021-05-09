package com.example.iikoapi.contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.iikoapi.R
import com.example.iikoapi.entities.District
import com.example.iikoapi.utils.GeneralAdapter
import kotlinx.android.synthetic.main.fragment_contacts_list.view.*


class ContactsFragmentList : Fragment(R.layout.fragment_contacts_list) {

    private var districts = listOf<District>()

    private val adapter : GeneralAdapter<District> = GeneralAdapter<District>()
        .apply {
            setData(listOf<District>())
            setLayoutId(R.layout.contacts_list_item)
        }

    fun setDistricts(districts: List<District>){
        adapter.setData(districts)
        adapter.notifyDataSetChanged()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.contactsRecyclerView.adapter = adapter

    }

//    private inner class Adapter: RecyclerView.Adapter<Adapter.Holder>() {
//
//        private inner class Holder(view: View): RecyclerView.ViewHolder(view) {
//            private val textView: TextView = view.itemTextView
//
//            fun bind(district: District){
//                textView.text = district.adr
//            }
//
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.contacts_list_item, parent, false)
//
//            return Holder(view)
//        }
//
//        override fun onBindViewHolder(holder: Holder, position: Int) {
//            holder.bind(placesNames[position])
//        }
//
//        override fun getItemCount() = placesNames.size
//    }

}
