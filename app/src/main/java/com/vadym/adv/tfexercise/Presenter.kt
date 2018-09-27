package com.vadym.adv.tfexercise

object Presenter {
    var switcher = false
    var languageId = 0

    fun saveToggleSwitchNotify(isChecked: Boolean) {
        switcher = isChecked
    }

    fun saveSelectedLanguage(id: Int) {
        languageId = id
    }

}