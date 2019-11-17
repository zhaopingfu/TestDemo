package com.zhaopf.testexoplayer

import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

val mExoPlayer by lazy {
    ExoPlayerFactory.newSimpleInstance(MyApp.instance).apply {
        repeatMode = Player.REPEAT_MODE_ONE
    }
}

private val dataSourceFactory: DataSource.Factory by lazy {
    DefaultDataSourceFactory(
        MyApp.instance,
        Util.getUserAgent(MyApp.instance, "yourApplicationName")
    )
}

private val progressiveMediaSourceFactory by lazy {
    ProgressiveMediaSource.Factory(dataSourceFactory)
}

fun ExoPlayer.prepare(proxyUrl: String) {
    val videoSource = progressiveMediaSourceFactory.createMediaSource(Uri.parse(proxyUrl))
    prepare(videoSource)
}