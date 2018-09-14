package adv.vadym.com.tfexercise

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private val messageNotification = MessageNotification
    private var toggleSwitcher = false
    private var languageId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val spLanguageAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.language,
                R.layout.spinner_simple_item
        )
        spLanguageAdapter.setDropDownViewResource(R.layout.spinner_drop_down)
        sp_language.adapter = spLanguageAdapter
        sp_language.setSelection(messageNotification.languageId)
        sp_language.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                languageId = position
            }
        }

        button_save.setOnClickListener {
            messageNotification.saveSelectedLanguage(languageId)
            messageNotification.saveToggleSwitchNotify(toggleSwitcher)
            if (toggleSwitcher) {
                messageNotification.notify(this, "TF Exercise", 1)
            }
            startActivity(Intent(this, MainActivity::class.java))
        }

        sw_notify.isChecked = messageNotification.switcher
        sw_notify.setOnCheckedChangeListener { _, isChecked ->
            toggleSwitcher = isChecked
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> false
    }
}