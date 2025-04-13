package app.sample.domain

import app.sample.domain.model.Device
import app.sample.domain.model.DeviceInfo
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun deviceListing(): Flow<List<DeviceInfo>>
    fun connect(id: String): Flow<Device>
    fun disconnect(id: String)
    fun command(/*Some Input*/) // todo, later
}
