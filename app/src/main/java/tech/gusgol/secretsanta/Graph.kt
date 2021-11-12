package tech.gusgol.secretsanta

import android.util.Base64
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import tech.gusgol.secretsanta.data.MessagesDataSource
import tech.gusgol.secretsanta.data.MessagesRepository
import tech.gusgol.secretsanta.data.MessagesService
import tech.gusgol.secretsanta.data.remote.MessagesRemoteDataSource

object Graph {

    const val HOST = "https://api.twilio.com/2010-04-01/Accounts/"

    private val mainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main

    private val ioDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    val api by lazy {

        val credentials = Base64.encodeToString(
            (Twillio.ACCOUNT_SID + ":" + Twillio.AUTH_TOKEN).toByteArray(),
            Base64.NO_WRAP
        )

        val client = OkHttpClient.Builder().apply {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
            addInterceptor{ chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                    .addHeader("Authorization", "Basic $credentials")

                val request = requestBuilder.build()
                chain.proceed(request)
            }
        }
        Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .client(client.build())
            .build()
    }

    val service by lazy {
        api.create(MessagesService::class.java)
    }

    val dataSource: MessagesDataSource by lazy {
        MessagesRemoteDataSource(service, ioDispatcher)
    }

    val repository by lazy {
        MessagesRepository(dataSource)
    }
}