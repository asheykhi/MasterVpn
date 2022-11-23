package ir.alishi.mastervpn.feature.main.data.datasource.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.alishi.mastervpn.core.extension.getLocalizedState
import ir.alishi.mastervpn.core.extension.progress
import ir.alishi.mastervpn.core.extension.progressColor
import ir.alishi.mastervpn.feature.main.domain.model.ConnectionStatus
import ir.alishi.mastervpn.feature.main.domain.model.OpenVpnStatus


class VpnReceiver : BroadcastReceiver() {

    private val _state = MutableLiveData<OpenVpnStatus>()
    val openVpnState: LiveData<OpenVpnStatus> = _state

    private val _emission = MutableLiveData<ConnectionStatus>()
    val packetEmission: LiveData<ConnectionStatus> = _emission

    private val _progressEmission = MutableLiveData<Int>()
    val progressEmission: LiveData<Int> = _progressEmission

    private val _progressColorEmission = MutableLiveData<Color>()
    val progressColorEmission: LiveData<Color> = _progressColorEmission


    override fun onReceive(context: Context, intent: Intent?) {

        val action = intent?.action
        if (intent != null && action.equals("vpn.master.connectionState")) {

            val state = intent.getStringExtra("state") ?: "Null"
            val duration = intent.getStringExtra("duration")
            val lastPacketReceive = intent.getStringExtra("lastPacketReceive")
            val byteIn = intent.getStringExtra("byteIn")
            val byteOut = intent.getStringExtra("byteOut")


            val statusRes = state.getLocalizedState()
            val statusValue = context.getString(statusRes)


            val openState = OpenVpnStatus(
                state = state,
                status = statusValue
            )

            if (state != "Null"){
                if (statusValue != "Unknown state"){
                    _state.value =openState
                    this._progressEmission.value = state.progress()
                    this._progressColorEmission.value = state.progressColor()
                    Log.d("RECEIVE", "STATE :: $state")
                    Log.d("RECEIVE", "STS :: $statusValue")
                }
            }

            val packet = ConnectionStatus(
                duration = duration ?: "00:00:00",
                lastPacketReceive = lastPacketReceive ?: "0",
                byteIn = byteIn ?: "↓0 kb - 0 B/s",
                byteOut = byteOut ?: "↑0 kb - 0 B/s"
            )
            this._emission.value = packet


            if (duration !=null && lastPacketReceive !=null && byteIn !=null && byteOut !=null){
                Log.d("RECEIVE", "DUR :: $duration")
                Log.d("RECEIVE", "LPR :: $lastPacketReceive")
                Log.d("RECEIVE", "DOW :: $byteIn")
                Log.d("RECEIVE", "UPL :: $byteOut")
            }


        }
    }
}