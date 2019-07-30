package com.zhaopf.getlauncherpackagename.activitys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.zhaopf.getlauncherpackagename.R
import kotlinx.android.synthetic.main.activity_third.*

class ThirdActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        btn_jump_to_metaapp.setOnClickListener {
            val uri = Uri.parse("metaapp://233xyx/splash")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        // 本地修改了3
    }
}