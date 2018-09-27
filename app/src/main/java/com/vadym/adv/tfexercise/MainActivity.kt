package com.vadym.adv.tfexercise

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.analytics.HitBuilders
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private val RECOVERY_REQUEST = 1
    private var presenter = Presenter
    var isSwipeRefresh = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBar(toolbar)
        tracker().setScreenName("Main Activity")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        actionBar.setDisplayShowTitleEnabled(false)
        youtube_exercises.initialize(Config().YOUTUBE_API_KEY, this)
        adMob()
        updatePage()

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

    private fun adMob() {
        MobileAds.initialize(this, "ca-app-pub-5169531562006723~1434884025")
        val gAdView = AdView(this)
        gAdView.adSize = AdSize.SMART_BANNER
        gAdView.adUnitId = "ca-app-pub-5169531562006723/2989414940"

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        if (!wasRestored) {
            when {
                presenter.languageId == 0 -> player?.cueVideo(resources.getString(R.string.video_cod_ko))
                presenter.languageId == 1 -> player?.cueVideo(resources.getString(R.string.video_cod_jp))
                presenter.languageId == 2 -> player?.cueVideo(resources.getString(R.string.video_cod_ru))
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

    private fun updatePage() {
        swipe_refresh.setOnRefreshListener {
            isSwipeRefresh = true
            Thread(Runnable {
                this@MainActivity.runOnUiThread { swipe_refresh.isRefreshing }
                try {
                    Thread.sleep(1200)
                    startActivity(Intent(this, MainActivity::class.java))
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }).start()
        }
    }

}