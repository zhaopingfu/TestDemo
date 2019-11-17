package com.zhaopf.testexoplayer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.Player
import kotlinx.android.synthetic.main.fragment_video_item.*

class VideoItemFragment : Fragment() {

    private val TAG = "VideoItemFragment"
    private var mData: VideoBean? = null
    private lateinit var mProxyUrl: String

    companion object {

        fun newInstance(videoBean: VideoBean): VideoItemFragment =
            VideoItemFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("videoBean", videoBean)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mData = arguments?.getSerializable("videoBean") as VideoBean?
        Log.d(TAG, "onViewCreated ${mData?.nickname}")

        tvText.text = mData?.title ?: "未知"

        Glide.with(this)
            .load(mData?.videoCover ?: "")
            .into(ivCover)

        mProxyUrl = MyApp.proxy.getProxyUrl(mData?.videoUrl ?: "")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume ${mData?.nickname}")

        mExoPlayer.prepare(mProxyUrl)
        // 避免不显示的问题
        if (playerView.player == mExoPlayer) {
            playerView.player = null
        }
        playerView.player = mExoPlayer
        mExoPlayer.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                if (playWhenReady && playbackState == Player.STATE_READY) {
                    ivCover?.visibility = View.GONE
                } else if (playWhenReady) {
                    // Not playing because playback ended, the player is buffering, stopped or
                    // failed. Check playbackState and player.getPlaybackError for details.
                } else {
                    // Paused by app.
                }
            }
        })
        mExoPlayer.playWhenReady = true
    }

    override fun onPause() {
        super.onPause()
        mExoPlayer.playWhenReady = false
        Log.d(TAG, "onPause ${mData?.nickname}")
    }
}