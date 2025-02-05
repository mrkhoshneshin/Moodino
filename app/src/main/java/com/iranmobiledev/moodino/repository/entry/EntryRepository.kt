package com.iranmobiledev.moodino.repository.entry

import com.iranmobiledev.moodino.data.Entry

interface EntryRepository {
    fun add(entry : Entry) : Long
    fun update(entry: Entry) : Int
    fun delete(entry: Entry) : Int
    fun getAll() : List<Entry>
}