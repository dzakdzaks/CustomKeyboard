package com.example.customkeyboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.core.util.lruCache
import com.example.customkeyboard.databinding.ActivityMainBinding
import android.os.CountDownTimer
import java.util.*
import android.os.Build
import android.text.InputType

import android.widget.EditText





class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val pads: List<String> = listOf("1","2","3","4","5","6","7","8","9","000","0","⌫")

    private lateinit var mTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupView()
        setContentView(binding.root)
    }

    private fun setupView() {
        binding.apply {

            // Disable soft keyboard from appearing, use in conjunction with android:windowSoftInputMode="stateAlwaysHidden|adjustNothing"
            textInputEditText.showSoftInputOnFocus = false

            val keyboardAdapter = KeyboardAdapter(
                onClick = { clickedPad ->
                    if (clickedPad == "⌫") {
//                        val length = textInputEditText.text?.length ?: 0
//                        if (length > 0) {
//                            textInputEditText.text?.delete(length - 1, length)
//                        }
                        textInputEditText.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                    } else {
                        textInputEditText.append(clickedPad)
                    }
                },
                onDelete = { isTouched ->
                    if (isTouched) {
                        deleteChar()
                    } else {
                        mTimer.cancel()
                    }
                }
            )
            keyboardAdapter.submitList(pads)
            rv.apply {
                adapter = keyboardAdapter
            }
        }
    }

    private fun deleteChar() {
        mTimer = object : CountDownTimer(9999999, 100) {
            override fun onTick(millisUntilFinished: Long) {
                binding.textInputEditText.dispatchKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_DEL
                    )
                )
            }

            override fun onFinish() {}
        }
        mTimer.start()
    }

}