package com.example.testrightmove.presentation.main

import android.content.Context
import androidx.test.runner.AndroidJUnit4
import com.example.testrightmove.helper.ReponseHelper
import com.example.testrightmove.network.service.ApiModule
import com.example.testrightmove.network.service.ApiService
import com.example.testrightmove.util.Connectivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runner.manipulation.Ordering
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainViewModelTest  {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: MainRepository
    @Inject
    lateinit var connectivity: Connectivity
    @Inject
    lateinit var helper: ReponseHelper
    lateinit var mainViewModel: MainViewModel
    @Before
    fun init(){
        hiltRule.inject()
        mainViewModel = MainViewModel(repository,connectivity,helper)
    }

    @Test
    fun firstTest() {
        assertEquals(true,mainViewModel.test())
//        assert(false).equals(false)
    }
}
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApiModule::class]
)
abstract class TestModule{
    @Binds
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }


    @Binds
    @Provides
      fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()


    @Binds
    @Provides
      fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("BASE_URL")
        .client(okHttpClient)
        .build()


    @Binds
    @Provides
      fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)



    @Binds
    @Provides
       fun providesRepository(apiService: ApiService) = MainRepository(apiService)



    @Binds
    @Provides
      fun providesConnect(@ApplicationContext   context: Context) = Connectivity(context)


    @Binds
    @Provides
      fun providesHelper() = ReponseHelper()
}