package app.sample.domain.usecase

import app.sample.domain.DeviceRepository
import javax.inject.Inject

class DisconnectDeviceUseCase @Inject constructor(
    private val repo: DeviceRepository
) : UseCase() {

    // handle Result makes sure to catch errors, without catching any Interruption exceptions for the suspend/coroutine
    suspend operator fun invoke(id: String): Result<String> = handleResult { // Result...
        repo.disconnect(id)
        "Disconnected $id"
    }

}