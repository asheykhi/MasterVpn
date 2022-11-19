package ir.alishi.mastervpn.feature.main.data.repository

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import de.blinkt.openvpn.OpenVpnApi
import de.blinkt.openvpn.core.OpenVPNThread
import ir.alishi.mastervpn.MasterVpnApp
import ir.alishi.mastervpn.feature.main.data.datasource.receiver.VpnReceiver
import ir.alishi.mastervpn.feature.main.domain.model.ConnectionStatus
import ir.alishi.mastervpn.feature.main.domain.model.OpenVpnStatus
import ir.alishi.mastervpn.feature.main.domain.repository.VpnRepository

class VpnRepositoryImpl(
    private val context: Context,
    override val dataSource: VpnReceiver
) : VpnRepository {


    override val packetEmission: LiveData<ConnectionStatus>
        get() = dataSource.packetEmission
    override val openStateEmission: LiveData<OpenVpnStatus>
        get() = dataSource.openVpnState
    override val progressEmission: LiveData<Int>
        get() = dataSource.progressEmission
    override val progressColorEmission: LiveData<Color>
        get() = dataSource.progressColorEmission

    override fun connect(serverConf: String) {

        val config = context.assets.open(serverConf)
            .bufferedReader()
            .use {
                it.readText()
            }
        OpenVpnApi.startVpn(
            context,
            config,
            "Japan",
            "asheykhi-vpnjantit.com",
            "1234aaa"
        )
        MasterVpnApp.vpnStart = true
    }

    override fun disConnect() {
        if (MasterVpnApp.vpnStart) {
            OpenVPNThread.stop()
            MasterVpnApp.vpnStart = false
        }
    }


}