package app.sample.data.di

import app.sample.data.repository.DeviceRepositoryImpl
import app.sample.domain.DeviceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryDI {

    @Binds
    abstract fun bindDeviceRepository(repo: DeviceRepositoryImpl): DeviceRepository

}
