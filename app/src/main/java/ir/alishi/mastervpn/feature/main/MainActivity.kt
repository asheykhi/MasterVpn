package ir.alishi.mastervpn.feature.main

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.net.VpnService
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dagger.hilt.android.AndroidEntryPoint
import de.blinkt.openvpn.core.OpenVPNService
import ir.alishi.mastervpn.feature.main.data.datasource.receiver.VpnReceiver
import ir.alishi.mastervpn.feature.main.domain.model.ConnectionStatus
import ir.alishi.mastervpn.feature.main.domain.model.OpenVpnStatus
import ir.alishi.mastervpn.feature.main.presentation.component.PacketCapture
import ir.alishi.mastervpn.feature.main.presentation.component.VpnConnectButton
import ir.alishi.mastervpn.feature.main.presentation.viewmodel.MainViewModel
import ir.alishi.mastervpn.ui.theme.MasterVpnTheme
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mReceiver: VpnReceiver

    private lateinit var viewModel: MainViewModel

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.connect("japan.ovpn")
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel = hiltViewModel()

            val packetEmission by viewModel.vpnPacketEmission.observeAsState(ConnectionStatus())
            val openStateEmission by viewModel.vpnStateEmission.observeAsState(OpenVpnStatus())
            val progress by viewModel.vpnProgressEmission.observeAsState(0)
            val progressColor by viewModel.vpnProgressColorEmission.observeAsState(Color.Red)
            val scope = rememberCoroutineScope()
            val scaffoldState = rememberScaffoldState()
            MasterVpnTheme {
                Surface {
                    Scaffold(
                        Modifier.fillMaxSize(),
                        scaffoldState = scaffoldState,
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "Master Vpn Client")
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Back Pressed"
                                            )
                                        }
                                    }) {
                                        Icon(
                                            Icons.Filled.ArrowBack,
                                            "backIcon"
                                        )
                                    }
                                },
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = Color.White,
                                elevation = 10.dp
                            )
                        },
                        bottomBar = {
                        }
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                VpnConnectButton(
                                    progress = progress,
                                    progressColor = progressColor,
                                    vpnState = openStateEmission,
                                    onPressed = { startVpn()}
                                )
                                PacketCapture(
                                    duration = packetEmission.duration,
                                    lastPacketReceive = packetEmission.lastPacketReceive,
                                    download = packetEmission.byteIn,
                                    upload = packetEmission.byteOut
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    private fun startVpn() {
        val intent = VpnService.prepare(this)
        if (intent != null) {
            setResult(1, intent)
            resultLauncher.launch(intent)
        } else {
            viewModel.connect("japan.ovpn")
        }
    }

    override fun onResume() {
        Log.i("MasterVpn", OpenVPNService.getStatus())
        LocalBroadcastManager
            .getInstance(this)
            .registerReceiver(mReceiver, IntentFilter("vpn.master.connectionState"))
        super.onResume()
    }

    override fun onPause() {
        LocalBroadcastManager
            .getInstance(this)
            .unregisterReceiver(mReceiver)
        super.onPause()
    }
}
