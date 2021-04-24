package com.example.iikoapi.general.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.iikoapi.R
import kotlinx.android.synthetic.main.contacts_list_item.view.*
import kotlinx.android.synthetic.main.fragment_contacts_list.view.*


class ContactsFragmentList : Fragment(R.layout.fragment_contacts_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.contactsRecyclerView.adapter = Adapter()

    }

    private inner class Adapter: RecyclerView.Adapter<Adapter.Holder>() {

        private inner class Holder(view: View): RecyclerView.ViewHolder(view) {
            private val textView: TextView = view.itemTextView

            fun bind(pos: Int){
                textView.text = "position $pos"
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.contacts_list_item, parent, false)

            return Holder(view)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(position)
        }

        override fun getItemCount() = 10
    }

}
