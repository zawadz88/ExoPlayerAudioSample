package com.github.zawadz88.exoplayeraudiosample.extension

import android.content.Intent
import timber.log.Timber

fun Intent.printIntentExtras() {
    val extrasBundle = this.extras
    val sb = StringBuilder()
    if (extrasBundle != null) {
        val keys = extrasBundle.keySet()
        keys.forEach { key ->
            sb.append('[').append(key).append('=').append(extrasBundle.get(key)).append(']')
        }
    }
    val intentExtrasString = sb.toString()
    Timber.i("Intent extras:$intentExtrasString")
}
