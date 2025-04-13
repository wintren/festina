package app.sample.domain.usecase

import app.sample.domain.CoroutineScopes
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

typealias UseDispatcher = CoroutineScopes.() -> CoroutineDispatcher

abstract class UseCase {

    @Inject
    protected lateinit var dispatchers: CoroutineScopes
    private val defaultUseCaseDispatcher: UseDispatcher = { io }

    protected fun <T> handleFlow(
        dispatcher: UseDispatcher = defaultUseCaseDispatcher,
        block: () -> Flow<T>
    ): Flow<T> = block()
        .flowOn(dispatcher(dispatchers))

    protected fun <T> handleFlowResult(
        dispatcher: UseDispatcher = defaultUseCaseDispatcher,
        block: () -> Flow<T>
    ): Flow<Result<T>> = handleErrorsNonSuspending(block).fold(
        onSuccess = { blockFlow -> blockFlow.flowOn(dispatcher(dispatchers)) },
        onFailure = { error -> flow { throw error } }
    )
        .map { Result.success(it) }
        .catch { error -> emit(Result.failure(error)) }
        .flowOn(dispatcher(dispatchers))

    protected suspend fun <T> handleResult(
        useDispatcher: UseDispatcher = defaultUseCaseDispatcher,
        block: suspend () -> T
    ): Result<T> = withContext(useDispatcher(dispatchers)) { handleErrors(block) }

    /**
     * Cancellation Errors should usually be a hidden part of coroutines, therefor we do not handle them in Result.
     * If you are expecting a CancellationException - make sure wrap to the calling site with some handling.
     */
    private suspend fun <T> handleErrors(block: suspend () -> T): Result<T> = try {
        Result.success(block.invoke())
    } catch (coroutineCancel: CancellationException) {
        throw coroutineCancel
    } catch (error: Throwable) {
        Result.failure(error)
    }

    /**
     * Cancellation Errors should usually be a hidden part of coroutines, therefor we do not handle them in Result.
     * If you are expecting a CancellationException - make sure wrap to the calling site with some handling.
     */
    private fun <T> handleErrorsNonSuspending(block: () -> T): Result<T> = try {
        Result.success(block.invoke())
    } catch (coroutineCancel: CancellationException) {
        throw coroutineCancel
    } catch (error: Throwable) {
        Result.failure(error)
    }
}