package ir.alishi.mastervpn.feature.main.presentation.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.alishi.mastervpn.MasterVpnApp
import ir.alishi.mastervpn.feature.main.domain.model.ConnectionStatus
import ir.alishi.mastervpn.feature.main.domain.model.OpenVpnStatus
import ir.alishi.mastervpn.feature.main.domain.usecase.MasterVpnUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MasterVpnUseCases
) : ViewModel() {

    private val _eventFLow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFLow.asSharedFlow()

    val vpnPacketEmission: LiveData<ConnectionStatus>
        get() = useCase.connect.repository.packetEmission

    val vpnStateEmission: LiveData<OpenVpnStatus>
        get() = useCase.connect.repository.openStateEmission

    val vpnProgressEmission: LiveData<Int>
        get() = useCase.connect.repository.progressEmission

    val vpnProgressColorEmission: LiveData<Color>
        get() = useCase.connect.repository.progressColorEmission

    fun connect(cof: String) {

        viewModelScope.launch(Dispatchers.IO) {
            if (!MasterVpnApp.vpnStart) {
                useCase.connect(cof)
                _eventFLow.emit(UIEvent.Toast("Connecting.."))
            } else {
                useCase.disconnect.invoke()
                _eventFLow.emit(UIEvent.Toast("Disconnecting.."))
            }
        }
    }

    sealed class UIEvent {
        class Toast(val message: String) : UIEvent()
        class ShowSnackBar(val message: String) : UIEvent()
    }


}