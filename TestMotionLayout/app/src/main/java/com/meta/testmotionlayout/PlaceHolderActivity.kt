package com.meta.testmotionlayout

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_place_holder.*

class PlaceHolderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_holder)

        placeholder.postDelayed({
            TransitionManager.beginDelayedTransition(placeholderContainer)
            placeholder.setContentId(favorite.id)
        }, 60L)
    }

    fun onClick(v: View) {
        TransitionManager.beginDelayedTransition(placeholderContainer)
        placeholder.setContentId(v.id)
    }
}