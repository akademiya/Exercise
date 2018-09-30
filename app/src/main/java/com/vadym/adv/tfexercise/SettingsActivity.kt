package com.vadym.adv.tfexercise

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.gms.analytics.HitBuilders
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private val messageNotification = MessageNotification
    private val presenter = Presenter
    private var toggleSwitcher = false
    private var languageId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tracker().setScreenName("Settings Activity")
        tracker().send(HitBuilders.ScreenViewBuilder().build())

        val spLanguageAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.language,
                R.layout.spinner_simple_item
        )
        spLanguageAdapter.setDropDownViewResource(R.layout.spinner_drop_down)
        sp_language.adapter = spLanguageAdapter
        sp_language.setSelection(presenter.languageId)
        sp_language.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                languageId = position
                tracker().send(HitBuilders.EventBuilder()
                        .setAction("Selected language")
                        .setValue(languageId.toLong())
                        .build()
                )
            }
        }

        button_save.setOnClickListener {
            presenter.saveSelectedLanguage(languageId)
            presenter.saveToggleSwitchNotify(toggleSwitcher)
//            if (toggleSwitcher) { TODO: save toggle notification
//                messageNotification.notify(this, "TF Exercise", 1)
//            }
            startActivity(Intent(this, MainActivity::class.java))
        }

        button_rate_app.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + this.packageName)))
            } catch (e: android.content.ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + this.packageName)))
            }

        }

//        TODO: notification
//        sw_notify.isChecked = presenter.switcher
//        sw_notify.setOnCheckedChangeListener { _, isChecked ->
//            toggleSwitcher = isChecked
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> false
    }
}