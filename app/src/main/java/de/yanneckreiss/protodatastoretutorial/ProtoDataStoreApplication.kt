package de.yanneckreiss.protodatastoretutorial

import android.app.Application
import de.yanneckreiss.protodatastoretutorial.data.proto.appStartupParamsDataStore
import de.yanneckreiss.protodatastoretutorial.proto.copy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ProtoDataStoreApplication : Application() {

    private val appCoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        appCoroutineScope.launch {
            this@ProtoDataStoreApplication.appStartupParamsDataStore.updateData { params ->
                params.copy {
                    startupUnixTimestamp = System.currentTimeMillis()
                    startupCounter = params.startupCounter + 1
                }
            }
        }
    }
}
