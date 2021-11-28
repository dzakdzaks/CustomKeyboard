package com.example.customkeyboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.customkeyboard.databinding.ItemPadBinding


class KeyboardAdapter(
    val onClick: (String) -> Unit,
    val onDelete: (Boolean) -> Unit
) : ListAdapter<String, KeyboardAdapter.DataViewHolder>(
    DiffCallback<String>(
        { old, new -> old == new },
        { old, new -> old == new }
    )
) {

    class DataViewHolder(val binding: ItemPadBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemPadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentData = getItem(position)
        holder.binding.apply {
            tv.text = currentData
            if (currentData == "⌫") {
                card.setOnTouchListener { _, motionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            onDelete(true)
                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_MOVE, MotionEvent.ACTION_CANCEL -> {
                            onDelete(false)
                        }
                    }
                    false
                }
            } else {
                card.setOnClickListener {
                    onClick(currentData)
                }
            }
        }
    }

}