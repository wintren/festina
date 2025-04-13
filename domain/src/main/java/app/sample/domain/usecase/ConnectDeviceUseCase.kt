@file:OptIn(FlowPreview::class)

package app.sample.domain.usecase

import app.sample.domain.DeviceRepository
import app.sample.domain.model.DeviceStatus
import app.sample.domain.result.ConnectDeviceResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ConnectDeviceUseCase @Inject constructor(
    private val repo: DeviceRepository
) : UseCase() {

    // handleFlowResult catch any exceptions and return a Result.failure
    //  Here's it's nice to have
    operator fun invoke(id: String): Flow<ConnectDeviceResult> = handleFlowResult {
        flowOf(
            flowOf(ConnectDeviceResult.Connecting),
            repo.connect(id)
                .catch { ConnectDeviceResult.Error(id, it) }
                .map {
                    when (it.status) {
                        DeviceStatus.CONNECTED -> ConnectDeviceResult.ConnectedDevice(it)
                        DeviceStatus.DISCONNECTED -> ConnectDeviceResult.Disconnected(it.id)
                    }
                }
        ).flattenConcat()
    }.map { result -> result.getOrElse { error -> ConnectDeviceResult.Error(id, error) } }
}