package ir.alishi.mastervpn.feature.main.domain.repository

import ir.alishi.mastervpn.core.util.ConnectionStatus
import kotlinx.coroutines.flow.Flow

interface ConnectionStatusRepository {
    fun checkDeviceActiveConnection() : Flow<ConnectionStatus<Boolean>>
}