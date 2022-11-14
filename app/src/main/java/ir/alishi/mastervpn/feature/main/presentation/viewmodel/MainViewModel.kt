package ir.alishi.mastervpn.feature.main.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.alishi.mastervpn.feature.main.domain.usecase.MasterVpnUseCases
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(useCase: MasterVpnUseCases) : ViewModel() {
}