package com.example.praktik_zadanie3__penkov.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.praktik_zadanie3__penkov.R
import com.example.praktik_zadanie3__penkov.activity.NewPostActivity.Companion.textArg


open class Fragment : AppCompatActivity(R.layout.intent_fragment) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text?.isNotBlank() != true) {
                return@let
            }
            intent.removeExtra(Intent.EXTRA_TEXT)
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.action_feedFragment_to_newPostActivity,
                Bundle().apply {
                    textArg = text
                }
            )
        }
    }
}