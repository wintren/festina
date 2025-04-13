package app.sample.domain

import app.sample.domain.model.Device
import app.sample.domain.model.DeviceInfo
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun deviceListing(): Flow<List<DeviceInfo>>
    fun connect(id: String)
    fun disconnect(id: String)
    fun device(id: String): Flow<Device>
    fun command(/*Some Input*/) // todo, later
}
