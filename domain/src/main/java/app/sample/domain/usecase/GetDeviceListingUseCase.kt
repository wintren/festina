package app.sample.domain.usecase

import app.sample.domain.DeviceRepository
import app.sample.domain.result.DeviceListingResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDeviceListingUseCase @Inject constructor(
    private val repo: DeviceRepository
) : UseCase() {

    // handleFlow {} is a function that sets up the usage of the Dispatchers to be io
    //  This should not always need to be the case as any data source or start of suspend/flow should set its Context to the appropriate dispatcher.
    //  Nonetheless it's a convenient handling for those edge cases - would be "optimised away" when needed in a proper project.
    // --
    // Another function that could have been used is handleFlowResult which would simply catch any exceptions and return a Result.failure
    //  This is nice to have if we don't define structured "Return Models"
    operator fun invoke(): Flow<DeviceListingResult> = handleFlow {
        repo.deviceListing()
            .catch { DeviceListingResult.Error(it) }
            .map {
                when (it.isEmpty()) {
                    true -> DeviceListingResult.NoDevices
                    false -> DeviceListingResult.Devices(it)
                }
            }
    }

}