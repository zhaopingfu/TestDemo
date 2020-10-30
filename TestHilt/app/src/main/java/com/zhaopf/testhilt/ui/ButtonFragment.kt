package com.zhaopf.testhilt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.zhaopf.testhilt.R
import com.zhaopf.testhilt.data.LoggerDataSource
import com.zhaopf.testhilt.data.User
import com.zhaopf.testhilt.di.DatabaseLogger
import com.zhaopf.testhilt.di.InMemoryLogger
import com.zhaopf.testhilt.navigator.AppNavigator
import com.zhaopf.testhilt.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author zhaopingfu
 * @date 2020/10/23
 */
@AndroidEntryPoint
class ButtonFragment : Fragment() {

    private val TAG = "ButtonFragment"

    @DatabaseLogger
    @Inject
    lateinit var logger: LoggerDataSource

    @Inject
    lateinit var navigator: AppNavigator

    @Inject
    lateinit var user: User

    private var num: Int = 100

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_buttons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.button1).setOnClickListener {
            logger.addLog("Interaction with 'Button 1'")
        }

        view.findViewById<Button>(R.id.button2).setOnClickListener {
            logger.addLog("Interaction with 'Button 2'")
        }

        view.findViewById<Button>(R.id.button3).setOnClickListener {
            logger.addLog("Interaction with 'Button 3'")
        }

        view.findViewById<Button>(R.id.all_logs).setOnClickListener {
            navigator.navigateTo(Screens.LOGS)
        }

        view.findViewById<Button>(R.id.delete_logs).setOnClickListener {
            logger.removeLogs()

            // user.age = num++
            android.util.Log.d(TAG, "$user")
        }
    }
}
