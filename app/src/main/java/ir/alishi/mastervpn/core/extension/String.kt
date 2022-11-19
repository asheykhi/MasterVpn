package ir.alishi.mastervpn.core.extension

import androidx.compose.ui.graphics.Color
import de.blinkt.openvpn.R


fun String.progress():Int = when(this){
    "NONETWORK" ,"NOPROCESS" ,"DISCONNECTED" -> 0
    "VPN_GENERATE_CONFIG" -> 36
    "WAIT" -> 180
    "AUTH" -> 210
    "GET_CONFIG" -> 250
    "ASSIGN_IP" -> 320
    "CONNECTED","Unknown state" -> 360
    "RECONNECTING" -> 180
    else -> 0
}

fun String.progressColor():Color = when(this){
    "RECONNECTING","NONETWORK" ,"NOPROCESS" ,"DISCONNECTED" -> Color.Red
    "WAIT", "VPN_GENERATE_CONFIG" -> Color(0xFFF44336)
    "AUTH" ,"GET_CONFIG"  -> Color.Red
    "ASSIGN_IP" -> Color.Yellow
    "CONNECTED","Unknown state" -> Color(0xFF008B48)
    else -> Color.DarkGray
}

fun String.getLocalizedState():Int = when(this){
    "CONNECTING" ->  R.string.state_connecting
    "WAIT" ->  R.string.state_wait
    "AUTH" ->  R.string.state_auth
    "GET_CONFIG" ->  R.string.state_get_config
    "ASSIGN_IP" ->  R.string.state_assign_ip
    "ADD_ROUTES" ->  R.string.state_add_routes
    "CONNECTED" ->  R.string.state_connected
    "DISCONNECTED" ->  R.string.state_disconnected
    "RECONNECTING" ->  R.string.state_reconnecting
    "EXITING" ->  R.string.state_exiting
    "RESOLVE" ->  R.string.state_resolve
    "TCP_CONNECT" ->  R.string.state_tcp_connect
    "AUTH_PENDING" ->  R.string.state_auth_pending
    else ->  R.string.unknown_state
}

