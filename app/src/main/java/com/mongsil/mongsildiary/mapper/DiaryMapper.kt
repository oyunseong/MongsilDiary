package com.mongsil.mongsildiary.mapper

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mongsil.mongsildiary.base.emoticons
import com.mongsil.mongsildiary.data.database.entity.RecordEntity
import com.mongsil.mongsildiary.data.database.entity.SlotEntity
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.domain.Slot

fun List<RecordEntity>.toRecords(): List<Record> {
    return map {
        Record(
            date = it.date,
            text = it.text,
//            images = it.imageUrls,
        )
    }
}

fun RecordEntity.toRecord(): Record {
    return Record(
        date = date,
        text = text,
//        images = imageUrls,
    )
}

fun LiveData<List<SlotEntity>>.toSlots(): LiveData<List<Slot>> {
    return map {
        listOf(
            Slot(
                date = it[0].date,
                text = it[0].text,
                timeSlot = it[0].timeSlot,
                emoticon = emoticons[it[0].emoticonId]
            )
        )

    }
}

fun Slot.toSlotEntity(): SlotEntity {
    return SlotEntity(
        date = this.date,
        timeSlot = timeSlot,
        emoticonId = emoticon.id,
        text = text
    )
}

fun Record.toRecordEntity(): RecordEntity {
    return RecordEntity(
        date = date,
        text = text,
//        imageUrls = images
    )
}