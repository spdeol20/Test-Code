package com.example.testrightmove.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.testrightmove.ui.theme.TestRightmoveTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testrightmove.ui.theme.ErrorColor
import com.example.testrightmove.ui.theme.TxtColor
import com.example.testrightmove.util.Connectivity
import com.example.testrightmove.util.Internet
import com.example.testrightmove.util.Loader
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val TAG = "MainActivity"

    val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestRightmoveTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainContent()
                }
            }
        }
    }

    //    @Preview(showBackground = true)
    @Composable
    fun MainContent() {
        Log.e(TAG, "MainContent: recomposong")
        val data by mainViewModel.getAveragePrice().collectAsState()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "What is the average property price?",
                modifier = Modifier.padding(bottom = 10.dp),
                style = MaterialTheme.typography.body1
            )
            AveragePrice(data)
            InternetStatus()
        }
    }
    @Composable
    fun InternetStatus() {
        Log.e(TAG, "InternetStatus: ")
        val internet by mainViewModel.connectivity.internetStatus.collectAsState()
        when(internet) {
            Internet.NO_INTERNET->{
                Text(
                    text = "No Internet",
                    style = MaterialTheme.typography.subtitle1,color = ErrorColor
                )
            }
        }
    }
    @Composable
    fun AveragePrice(data: Int) {
        val loader by mainViewModel.helper.loadingStatus.collectAsState()
        when(loader){
            Loader.INTERNET_AVAILABLE,
            Loader.SUCESS->{

                Text(
                    text = "Avergae Price: $data",
                    style = MaterialTheme.typography.subtitle1,color = TxtColor
                )
            }
            Loader.FAIL->{

                Text(
                    text = "Fail to calculate average price",
                    style = MaterialTheme.typography.subtitle1,color = ErrorColor
                )
            }
            else->{

                Text(
                    text = "Loading..",
                    style = MaterialTheme.typography.subtitle1,color = TxtColor
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: unregister")
        mainViewModel.unregister()
    }
}


