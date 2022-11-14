package ir.alishi.mastervpn.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.alishi.mastervpn.feature.main.data.repository.VpnRepositoryImpl
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
    fun provideMasterVpnUseCases(repository: VpnRepository): MasterVpnUseCases {
        return MasterVpnUseCases(
            connect = ConnectVpn(repository),
            disConnect = DisConnectVpn(repository)
        )
    }

    @Provides
    @Singleton
    fun provideRepository():VpnRepository {
        return VpnRepositoryImpl(

        )
    }

    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences {
        return app.getSharedPreferences("master_shp", Context.MODE_PRIVATE)
    }
}