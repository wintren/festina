package app.sample.data.repository

import android.util.Log
import app.sample.data.model.DeviceConnection
import app.sample.data.source.SourceDevice
import app.sample.data.source.SourceDeviceListener
import app.sample.data.source.scanForDevices
import app.sample.domain.CoroutineScopes
import app.sample.domain.DeviceRepository
import app.sample.domain.model.Device
import app.sample.domain.model.DeviceInfo
import app.sample.domain.model.DeviceStatus
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepositoryImpl @Inject constructor(
    scopes: CoroutineScopes
) : DeviceRepository {

    private val deviceConnections: MutableStateFlow<List<DeviceConnection>> = MutableStateFlow(emptyList())

    init {
        // This would be some kind of scan for devices, probably not owned by this repository - but by a service, or bootstrap etc.
        // But in this project it's fine to assume it's setup correctly.


        scopes.app.launch {
            val foundDevices = scanForDevices()
                .map { DeviceConnection(source = it, connectionStatus = DeviceStatus.DISCONNECTED) }
            deviceConnections.update { foundDevices }
        }
    }

    override fun deviceListing(): Flow<List<DeviceInfo>> {
        return deviceConnections.map { sourceList ->
            sourceList.map { deviceConnection ->
                // For larger models and projects I like to use Mappers to isolate mapping from model to model when switching between or combining data from data/domain models
                DeviceInfo(id = deviceConnection.id, status = deviceConnection.connectionStatus)
            }
        }
    }

    override fun connect(id: String): Flow<Device> = callbackFlow connection@{
        log("connect($id)")
        val connections = deviceConnections.firstOrNull() ?: error("No devices available at time of connect($id)")
        val deviceConnection = connections.firstOrNull { it.id == id } ?: error("No device available for id $id")
        when (deviceConnection.connectionStatus) {
            DeviceStatus.CONNECTED -> {
                log("Already connected feed forward Device model ($id)")
                trySend(
                    Device(
                        id = deviceConnection.id,
                        status = deviceConnection.connectionStatus,
                        log = deviceConnection.source.read() ?: "Empty Log"
                    )
                )
            }
            DeviceStatus.DISCONNECTED -> {
                log("No Connection ($id); add source listener")
                // I am not a big fan of var's in data classes, it would be nicer if this was immutable I think.
                deviceConnection.source.deviceListener = object : SourceDeviceListener {
                    override fun onConnected(device: SourceDevice) {
                        log("onConnected")
                        val log = device.read()
                        val update = Device(
                            id = device.id.toString(),
                            status = DeviceStatus.CONNECTED,
                            log = log ?: "Empty Log"
                        )
                        trySend(update)
                        updateDeviceConnection(id) { copy(connectionStatus = DeviceStatus.CONNECTED) }

                        log("update sent $update")
                    }

                    override fun onDisconnected(device: SourceDevice, error: Exception?) {
                        trySend(
                            Device(
                                id = device.id.toString(),
                                status = DeviceStatus.DISCONNECTED,
                                log = "Disconnected"
                            )
                        )
                        updateDeviceConnection(id) {
                            copy(
                                connectionStatus = DeviceStatus.DISCONNECTED,
                                source = source.apply {
                                    deviceListener = null
                                    close()
                                }
                            )
                        }
                    }
                }
                log("No Connection ($id); source.connect()")
                deviceConnection.source.connect()
            }
        }
        awaitClose {}
    }.onEach { log("Each: $it") }

    override fun disconnect(id: String) {
        TODO("Not yet implemented")
    }

    override fun readLog(id: String): Flow<List<String>> = flow {
        val connection = deviceConnections.first().firstOrNull { it.id == id }
            ?: error("No device available for id $id")

        var latestLog: String? = ""
        val builder = StringBuilder()
        var logPage = 0
        while (latestLog != null) {
            connection.source.write(cmd = "log", logPage++)
            latestLog = connection.source.read()
            latestLog?.let { builder.append(it) }
            emit(builder.toString().trimIndent().split("\n"))
        }
    }

    private fun updateDeviceConnection(id: String, update: DeviceConnection.() -> DeviceConnection) {
        deviceConnections.update { connections ->
            connections.map { connection ->
                if (connection.id == id) update(connection) else connection
            }
        }
    }

    private fun log(message: String) = Log.d("RepoImpl", message)
}
