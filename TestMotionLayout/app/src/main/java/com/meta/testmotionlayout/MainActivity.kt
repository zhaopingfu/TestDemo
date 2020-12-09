package com.meta.testmotionlayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.meta.testmotionlayout.circular_reveal_helper.CircularRevealActivity
import com.meta.testmotionlayout.databinding.ActivityMainBinding
import com.meta.testmotionlayout.fly_in_helper.FlyInActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnJumpCircleActivity.setOnClickListener {
            startActivity(Intent(it.context, CircleActivity::class.java))
        }

        binding.btnJumpGroupActivity.setOnClickListener {
            startActivity(Intent(it.context, GroupActivity::class.java))
        }

        binding.btnJumpLayerActivity.setOnClickListener {
            startActivity(Intent(it.context, LayerActivity::class.java))
        }

        binding.btnJumpBarrierActivity.setOnClickListener {
            startActivity(Intent(it.context, BarrierActivity::class.java))
        }

        binding.btnJumpCircularRevealActivity.setOnClickListener {
            startActivity(Intent(it.context, CircularRevealActivity::class.java))
        }

        binding.btnJumpFlyInActivity.setOnClickListener {
            startActivity(Intent(it.context, FlyInActivity::class.java))
        }

        binding.btnJumpPlaceholderActivity.setOnClickListener {
            startActivity(Intent(it.context, PlaceHolderActivity::class.java))
        }

        binding.btnJumpConstraintsetActivity.setOnClickListener {
            startActivity(Intent(it.context, ConstraintSetActivity::class.java))
        }
        binding.btnJumpConstraintset2Activity.setOnClickListener {
            startActivity(Intent(it.context, ConstraintSet2Activity::class.java))
        }

        binding.btnJumpFlowActivity.setOnClickListener {
            startActivity(Intent(it.context, FlowActivity::class.java))
        }

        binding.btnJumpObjectAnimatorActivity.setOnClickListener {
            startActivity(Intent(it.context, ObjectAnimatorActivity::class.java))
        }

        binding.btnJumpGoActivity.setOnClickListener {
            startActivity(Intent(it.context, GoActivity::class.java))
        }

        binding.btnJumpMotionlayoutActivity.setOnClickListener {
            startActivity(Intent(it.context, MotionLayoutActivity::class.java))
        }

        binding.btnJumpSampleActivity.setOnClickListener {
            startActivity(Intent(it.context, SampleActivity::class.java))
        }

        binding.btnJumpKeyPositionActivity.setOnClickListener {
            startActivity(Intent(it.context, TestKeyPositionActivity::class.java))
        }

        binding.btnYoutube.setOnClickListener {
            startActivity(Intent(this@MainActivity, YoutubeActivity::class.java))
        }

        binding.btnMotionLayout.setOnClickListener {
            startActivity(Intent(this@MainActivity, TestMotionLayoutActivity::class.java))
        }

        binding.btnJumpFlipActivity.setOnClickListener {
            startActivity(Intent(this@MainActivity, FlipActivity::class.java))
        }

        binding.btnJumpWithCoordinatorlayoutActivity.setOnClickListener {
            startActivity(Intent(it.context, WithCoordinatorLayoutActivity::class.java))
        }

        binding.btnJumpWithDrawerlayoutActivity.setOnClickListener {
            startActivity(Intent(it.context, WithDrawerLayoutActivity::class.java))
        }

        binding.btnJumpTestMotionEditorActivity.setOnClickListener {
            startActivity(Intent(it.context, TestMotionEditorActivity::class.java))
        }
    }
}
