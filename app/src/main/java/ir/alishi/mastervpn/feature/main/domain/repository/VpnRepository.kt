package ir.alishi.mastervpn.feature.main.domain.repository

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import ir.alishi.mastervpn.feature.main.data.datasource.receiver.VpnReceiver
import ir.alishi.mastervpn.feature.main.domain.model.ConnectionStatus
import ir.alishi.mastervpn.feature.main.domain.model.OpenVpnStatus

interface VpnRepository {
    val dataSource : VpnReceiver
    val packetEmission: LiveData<ConnectionStatus>
    val progressEmission: LiveData<Int>
    val progressColorEmission: LiveData<Color>
    val openStateEmission: LiveData<OpenVpnStatus>
    fun connect(serverConf: String)
    fun disConnect()
}