package com.umain.cocoaheads

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun initLogger() {
    Napier.base(DebugAntilog())
}
