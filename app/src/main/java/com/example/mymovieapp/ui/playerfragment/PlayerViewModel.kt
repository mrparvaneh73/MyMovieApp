package com.example.mymovieapp.ui.playerfragment

import android.app.Application
import android.arch.lifecycle.*
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class PlayerViewModel(application: Application)
    : AndroidViewModel(application)
    , LifecycleObserver {

    private val _player = MutableLiveData<Player?>()
    val player: LiveData<Player?> get() = _player
    private var contentPosition = 0L
    private var playWhenReady = true


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForegrounded() {
        setUpPlayer()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackgrounded() {
        releaseExoPlayer()
    }

    private fun setUpPlayer() {
        val dataSourceFactory = DefaultDataSourceFactory(
            getApplication(),
            Util.getUserAgent(getApplication(), "demo")
        )
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse("https://www.youtube.com/watch?v=pY0jUZL6SXo")))

        val player = ExoPlayer.Builder(getApplication()).build()
        player.prepare(mediaSource)
        player.playWhenReady = playWhenReady
        player.seekTo(contentPosition)

        this._player.value = player
    }

    private fun releaseExoPlayer() {
        val player = _player.value ?: return
        this._player.value = null

        contentPosition = player.contentPosition
        playWhenReady = player.playWhenReady
        player.release()
    }

    override fun onCleared() {
        super.onCleared()
        releaseExoPlayer()

    }
}