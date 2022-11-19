package ir.alishi.mastervpn.feature.main.domain.usecase

import android.util.Log
import ir.alishi.mastervpn.core.util.ConnectionStatus
import ir.alishi.mastervpn.feature.main.domain.repository.ConnectionStatusRepository
import ir.alishi.mastervpn.feature.main.domain.repository.VpnRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConnectVpn(
    val repository: VpnRepository,
    private val connectionStatusRepository : ConnectionStatusRepository
) {
    private val _error = "ConnectionError"

    suspend operator fun invoke(conf :String) {
       withContext(Dispatchers.IO){
           connectionStatusRepository.checkDeviceActiveConnection().collect { hasConnection->
               when (hasConnection) {
                   is ConnectionStatus.OK -> {
                       repository.connect(conf)
                   }
                   is ConnectionStatus.Error -> {
                       Log.e(_error, hasConnection.exception?.message.toString())
                   }
               }
           }
       }
    }
}