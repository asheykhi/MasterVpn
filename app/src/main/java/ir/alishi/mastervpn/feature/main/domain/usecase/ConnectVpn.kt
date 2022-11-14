package ir.alishi.mastervpn.feature.main.domain.usecase

import ir.alishi.mastervpn.feature.main.domain.repository.VpnRepository

class ConnectVpn(
    private val repository: VpnRepository
) {
    operator fun invoke() {
        repository.connect()
    }
}