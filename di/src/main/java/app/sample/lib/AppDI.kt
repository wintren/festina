package app.sample.lib

import android.content.Context
import android.content.res.Resources
import app.sample.domain.CoroutineScopes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDI {

    @Singleton
    @Provides
    fun provideResources(
        @ApplicationContext context: Context
    ): Resources = context.resources ?: error("Context Resources missing through Hilt")

    @Singleton
    @Provides
    fun provideDispatcherProvider(): CoroutineScopes = object : CoroutineScopes {
        override val main: CoroutineDispatcher get() = Dispatchers.Main
        override val io: CoroutineDispatcher get() = Dispatchers.IO
        override val default: CoroutineDispatcher get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher get() = Dispatchers.Unconfined
        override val app: CoroutineScope get() = CoroutineScope(SupervisorJob() + default)
    }

}
