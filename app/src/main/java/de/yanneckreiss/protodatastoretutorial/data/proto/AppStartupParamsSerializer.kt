package de.yanneckreiss.protodatastoretutorial.data.proto

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import de.yanneckreiss.protodatastoretutorial.proto.AppStartupParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

object AppStartupParamsSerializer : Serializer<AppStartupParams> {

    override val defaultValue: AppStartupParams = AppStartupParams.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AppStartupParams = withContext(Dispatchers.IO) {
        try {
            return@withContext AppStartupParams.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: AppStartupParams, output: OutputStream) = withContext(Dispatchers.IO) { t.writeTo(output) }
}

val Context.appStartupParamsDataStore: DataStore<AppStartupParams> by dataStore(
    fileName = "app_startup_params.pb",
    serializer = AppStartupParamsSerializer
)
