package ir.alishi.mastervpn.feature.main.domain.model

data class ConnectionStatus(
    val duration : String = "00:00:00",
    val lastPacketReceive: String = "0",
    val byteIn: String= "↓0 kb - 0 B/s",
    val byteOut: String = "↑0 kb - 0 B/s"
)