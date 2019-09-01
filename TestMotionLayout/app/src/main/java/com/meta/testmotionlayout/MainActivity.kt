package com.meta.testmotionlayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.meta.testmotionlayout.circular_reveal_helper.CircularRevealActivity
import com.meta.testmotionlayout.fly_in_helper.FlyInActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_jump_circle_activity.setOnClickListener {
            startActivity(Intent(it.context, CircleActivity::class.java))
        }

        btn_jump_group_activity.setOnClickListener {
            startActivity(Intent(it.context, GroupActivity::class.java))
        }

        btn_jump_layer_activity.setOnClickListener {
            startActivity(Intent(it.context, LayerActivity::class.java))
        }

        btn_jump_barrier_activity.setOnClickListener {
            startActivity(Intent(it.context, BarrierActivity::class.java))
        }

        btn_jump_circular_reveal_activity.setOnClickListener {
            startActivity(Intent(it.context, CircularRevealActivity::class.java))
        }

        btn_jump_fly_in_activity.setOnClickListener {
            startActivity(Intent(it.context, FlyInActivity::class.java))
        }

        btn_jump_placeholder_activity.setOnClickListener {
            startActivity(Intent(it.context, PlaceHolderActivity::class.java))
        }

        btn_jump_constraintset_activity.setOnClickListener {
            startActivity(Intent(it.context, ConstraintSetActivity::class.java))
        }
        btn_jump_constraintset2_activity.setOnClickListener {
            startActivity(Intent(it.context, ConstraintSet2Activity::class.java))
        }

        btn_jump_flow_activity.setOnClickListener {
            startActivity(Intent(it.context, FlowActivity::class.java))
        }

        btn_jump_object_animator_activity.setOnClickListener {
            startActivity(Intent(it.context, ObjectAnimatorActivity::class.java))
        }

        btn_jump_go_activity.setOnClickListener {
            startActivity(Intent(it.context, GoActivity::class.java))
        }

        btn_jump_motionlayout_activity.setOnClickListener {
            startActivity(Intent(it.context, MotionLayoutActivity::class.java))
        }

        btn_jump_sample_activity.setOnClickListener {
            startActivity(Intent(it.context, SampleActivity::class.java))
        }

        btn_jump_with_coordinatorlayout_activity.setOnClickListener {
            startActivity(Intent(it.context, WithCoordinatorLayoutActivity::class.java))
        }

        btn_jump_with_drawerlayout_activity.setOnClickListener {
            startActivity(Intent(it.context, WithDrawerLayoutActivity::class.java))
        }
    }
}