package com.trinity.test.deps

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.trinity.test.MainApp
import com.trinity.test.db.ContactManager
import com.trinity.test.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .excludeFieldsWithModifiers(
                Modifier.FINAL,
                Modifier.TRANSIENT,
                Modifier.STATIC
            )
            .create()
    }

    @Singleton
    @Provides
    @Named(value = "okhttp")
    fun providesOkHttpClient(
        httpLoging: HttpLoggingInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(timeout = 15, TimeUnit.SECONDS)
            .writeTimeout(timeout = 15, TimeUnit.SECONDS)
            .connectTimeout(timeout = 15, TimeUnit.SECONDS)
            .addInterceptor(httpLoging)
            .addInterceptor { chain ->
                val requestBuilder: Request.Builder = chain.request().newBuilder()
                    .header(name = "Bearer", value = "")
                    .header(name = "Accept", value = "application/json")
                    .header(name = "Accept", value = "Content-Type: application/json")
                return@addInterceptor chain.proceed(requestBuilder.build())
            }
            .cache(cache = null)
            .protocols(mutableListOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
            .build()
    }

    @Singleton
    @Provides
    @Named(value = "api")
    fun provideRetrofitV2(
        @Named(value = "okhttp") okHttpClient: OkHttpClient,
        @ApplicationContext context: Context,
        gson: Gson
    ): Retrofit {
        okHttpClient.interceptors
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://www.blablabla.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideSchedulerProvider() = SchedulerProvider(
        Schedulers.io(), AndroidSchedulers.mainThread()
    )

    @Singleton
    @Provides
    fun providesBannerTopManager(@ApplicationContext context: Context): ContactManager {
        return ContactManager(context.applicationContext as MainApp)
    }
}