package ir.alishi.mastervpn.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.alishi.mastervpn.core.util.CheckInternetConnection
import ir.alishi.mastervpn.feature.main.data.datasource.receiver.VpnReceiver
import ir.alishi.mastervpn.feature.main.data.repository.ConnectionStatusRepositoryImpl
import ir.alishi.mastervpn.feature.main.data.repository.VpnRepositoryImpl
import ir.alishi.mastervpn.feature.main.domain.repository.ConnectionStatusRepository
import ir.alishi.mastervpn.feature.main.domain.repository.VpnRepository
import ir.alishi.mastervpn.feature.main.domain.usecase.ConnectVpn
import ir.alishi.mastervpn.feature.main.domain.usecase.DisConnectVpn
import ir.alishi.mastervpn.feature.main.domain.usecase.MasterVpnUseCases
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideMasterVpnUseCases(
        repository: VpnRepository,
        connectionRepository : ConnectionStatusRepository
    ): MasterVpnUseCases {
        return MasterVpnUseCases(
            connect = ConnectVpn(repository,connectionRepository),
            disconnect = DisConnectVpn(repository)
        )
    }

    @Provides
    @Singleton
    fun provideRepository(
        @ApplicationContext context: Context,
        dataSource : VpnReceiver
    ): VpnRepository {
        return VpnRepositoryImpl(context, dataSource)
    }

    @Provides
    @Singleton
    fun provideDataSource(): VpnReceiver = VpnReceiver()

    @Provides
    @Singleton
    fun provideConnectionStatusRepository(
        @ApplicationContext context: Context,
        mConnection: CheckInternetConnection
    ):ConnectionStatusRepository{
        return ConnectionStatusRepositoryImpl(
          context,
          mConnection
        )
    }

    @Provides
    @Singleton
    fun provideDeviceInternetStatus(): CheckInternetConnection {
        return CheckInternetConnection()
    }

    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences {
        return app.getSharedPreferences("master_shp", Context.MODE_PRIVATE)
    }


}