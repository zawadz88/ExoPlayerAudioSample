package com.github.zawadz88.audioservice.internal.extension

import android.content.Intent
import timber.log.Timber

internal fun Intent.printIntentExtras() {
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
