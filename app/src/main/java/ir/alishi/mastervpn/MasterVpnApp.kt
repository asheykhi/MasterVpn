package ir.alishi.mastervpn

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import de.blinkt.openvpn.core.VpnStatus

@HiltAndroidApp
class MasterVpnApp : Application(){

    companion object {
        var vpnStart : Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        VpnStatus.initLogCache(cacheDir)
    }
}