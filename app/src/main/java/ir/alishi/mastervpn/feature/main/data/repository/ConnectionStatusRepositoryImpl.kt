package ir.alishi.mastervpn.feature.main.data.repository

import android.content.Context
import ir.alishi.mastervpn.core.util.CheckInternetConnection
import ir.alishi.mastervpn.core.util.ConnectionStatus
import ir.alishi.mastervpn.feature.main.domain.repository.ConnectionStatusRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class ConnectionStatusRepositoryImpl(
    private val context: Context,
    private val mConnection: CheckInternetConnection
) : ConnectionStatusRepository {

    override fun checkDeviceActiveConnection(): Flow<ConnectionStatus<Boolean>> = flow {
        if (mConnection.netCheck(context)) {
            try {
                withContext(Dispatchers.IO) {
                    val socket = Socket()
                    // check internet availability with google dns address
                    socket.connect(InetSocketAddress("8.8.8.8", 53), 5000)
                    socket.close()
                    emit(ConnectionStatus.OK())
                }
            } catch (e: IOException) {
                emit(ConnectionStatus.Error(exception = e))
            }
        } else
            emit(ConnectionStatus.Error(IOException("Your device is not connected to internet")))
    }

}