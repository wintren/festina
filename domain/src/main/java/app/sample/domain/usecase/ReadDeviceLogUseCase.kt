package app.sample.domain.usecase

import app.sample.domain.DeviceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadDeviceLogUseCase @Inject constructor(
    private val repo: DeviceRepository
) : UseCase() {

    // handleFlowResult catch any exceptions and return a Result.failure
    //  Here's it's nice to have
    operator fun invoke(id: String): Flow<Result<List<String>>> = handleFlowResult {
        repo.readLog(id)
    }
}