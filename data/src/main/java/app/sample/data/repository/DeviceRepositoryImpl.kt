package app.sample.data.repository

import app.sample.data.model.DeviceConnection
import app.sample.data.source.scanForDevices
import app.sample.domain.CoroutineScopes
import app.sample.domain.DeviceRepository
import app.sample.domain.model.Device
import app.sample.domain.model.DeviceInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepositoryImpl @Inject constructor(
    scopes: CoroutineScopes
) : DeviceRepository {

    private val devices: MutableSharedFlow<List<DeviceConnection>> = MutableSharedFlow(replay = 1)

    init {
        // This would be some kind of scan for devices, probably not owned by this repository - but by a service, or bootstrap etc.
        // But in this project it's fine to assume it's setup correctly.


        scopes.app.launch {
            val foundDevices = scanForDevices()
                .map { DeviceConnection(source = it) }
            devices.emit(foundDevices)
        }
    }

    override fun deviceListing(): Flow<List<DeviceInfo>> {
        return devices.map { sourceList ->
            sourceList.map { deviceConnection ->
                // For larger models and projects I like to use Mappers to isolate mapping from model to model when switching between or combining data from data/domain models
                DeviceInfo(id = deviceConnection.id, status = deviceConnection.connectionStatus)
            }
        }
    }

    override fun connect(id: String): Flow<Device> {
        TODO("Not yet implemented")
    }

    override fun disconnect(id: String) {
        TODO("Not yet implemented")
    }

    override fun command() {
        TODO("Not yet implemented")
    }
}
