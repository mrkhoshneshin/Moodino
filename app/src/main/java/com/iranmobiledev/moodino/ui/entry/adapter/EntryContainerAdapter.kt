package com.iranmobiledev.moodino.ui.entry.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.utlis.EntryEventListener
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
class EntryContainerAdapter(
    private val context: Context,
    private val entriesList: MutableList<MutableList<Entry>>,
    private val entryEventListener: EntryEventListener,
    private val entryAdapters: MutableList<EntryAdapter> = mutableListOf()
    ) : RecyclerView.Adapter<EntryContainerAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val entryListDate = itemView.findViewById<TextView>(R.id.entryListDate)
        private val entryRecyclerView = itemView.findViewById<RecyclerView>(R.id.entryRv)

        @SuppressLint("SetTextI18n")
        fun bind(entries: List<Entry>) {

            val persianDate = PersianDate()
            persianDate.grgMonth = entries[0].date?.month!!
            entryListDate.text = PersianDateFormat.format(
                persianDate,
                "j F",
                PersianDateFormat.PersianDateNumberCharacter.ENGLISH
            )

            entryRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val entryAdapter = EntryAdapter(
                entryEventListener,
                entries as MutableList<Entry>, context
            )
            entryRecyclerView.adapter = entryAdapter
            entryRecyclerView.itemAnimator = null
            entryAdapters.add(entryAdapter)
        }
    }

    fun add(entry: Entry){
        var found = false
        entryAdapters.forEach {
            if(it.entries[0].date == entry.date){
                found = true
                it.add(entry)
                return
            }
            if(!found){
                val newList = mutableListOf(entry)
                entriesList.add(0,newList)
                notifyItemInserted(0)
            }
        }
    }
    fun removeItem(entry: Entry) {
        entryAdapters.forEach {
            if(it.entries.contains(entry)){
                it.remove(entry)
                checkItemsToBeNotEmpty()
                return
            }
        }

    }
    private fun checkItemsToBeNotEmpty(){
        entriesList.forEachIndexed { index, list ->
            if(list.size == 0){
                entriesList.removeAt(index)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entry_container, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(entriesList[position])
    }
    override fun getItemCount(): Int {
        return entriesList.size
    }

}

