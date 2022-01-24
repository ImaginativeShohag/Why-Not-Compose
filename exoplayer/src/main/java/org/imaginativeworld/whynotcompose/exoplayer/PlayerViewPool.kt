package org.imaginativeworld.whynotcompose.exoplayer

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.core.util.Pools
import com.google.android.exoplayer2.ui.PlayerView

object PlayerViewPool {
    var currentPlayerView: PlayerView? = null

    private val playerViewPool = Pools.SimplePool<PlayerView>(2)

    fun get(context: Context): PlayerView {
        return playerViewPool.acquire() ?: createPlayerView(context)
    }

    fun release(player: PlayerView) {
        playerViewPool.release(player)
    }

    @SuppressLint("InflateParams")
    private fun createPlayerView(context: Context): PlayerView {
        return (LayoutInflater.from(context)
            .inflate(R.layout.exoplayer_texture_view, null, false) as PlayerView)
    }
}