package app.sample.data.repository

import app.sample.domain.DeviceRepository
import app.sample.domain.model.Device
import app.sample.domain.model.DeviceInfo
import app.sample.domain.model.DeviceStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepositoryImpl @Inject constructor() : DeviceRepository {

    override fun deviceListing(): Flow<List<DeviceInfo>> {
        return flowOf(
            listOf(
                DeviceInfo("123", DeviceStatus.DISCONNECTED),
                DeviceInfo("456", DeviceStatus.DISCONNECTED),
            )
        )
    }

    override fun connect(id: String) {
        TODO("Not yet implemented")
    }

    override fun disconnect(id: String) {
        TODO("Not yet implemented")
    }

    override fun device(id: String): Flow<Device> {
        TODO("Not yet implemented")
    }

    override fun command() {
        TODO("Not yet implemented")
    }
}
