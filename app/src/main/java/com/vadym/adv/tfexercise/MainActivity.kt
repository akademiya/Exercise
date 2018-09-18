package com.vadym.adv.tfexercise

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private val RECOVERY_REQUEST = 1
    private val messageNotification = MessageNotification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBar(toolbar)
        actionBar.setDisplayShowTitleEnabled(false)
        youtube_exercises.initialize(Config().YOUTUBE_API_KEY, this)
    }

    /** app bar menu */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    /** end app bar menu */

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        if (!wasRestored) {
            when {
                messageNotification.languageId == 0 -> player?.cueVideo(resources.getString(R.string.video_cod_ko))
                messageNotification.languageId == 1 -> player?.cueVideo(resources.getString(R.string.video_cod_jp))
                messageNotification.languageId == 2 -> player?.cueVideo(resources.getString(R.string.video_cod_ru))
            }
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?, errorReason: YouTubeInitializationResult?) {
        if (errorReason?.isUserRecoverableError!!) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show()
        } else {
            val error = String.format(getString(R.string.player_error), errorReason.toString())
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubePlayerProvider().initialize(Config().YOUTUBE_API_KEY, this)
        }
    }

    private fun getYouTubePlayerProvider() : YouTubePlayer.Provider {
        return youtube_exercises
    }
}
