package com.github.godspeed010.martatraintime

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.github.godspeed010.martatraintime.feature_train.presentation.trains.TrainsScreen
import com.github.godspeed010.martatraintime.feature_train.ui.theme.MARTATrainTimeRemakeTheme
import com.google.transit.realtime.GtfsRealtime
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import io.reactivex.functions.Consumer

@AndroidEntryPoint
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {


    private val okHttpClient = OkHttpClient()

    private val observable: Observable<GtfsRealtime.VehiclePosition> =
        Observable.just("https://gtfs-rt.itsmarta.com/TMGTFSRealTimeWebService/vehicle/vehiclepositions.pb")
            .map(object : Function<String?, GtfsRealtime.VehiclePosition> {
                @Throws(Exception::class)
                override fun apply(url: String?): GtfsRealtime.VehiclePosition? {
                    val request = Request.Builder().url(url!!).build()
                    val call: Call = okHttpClient.newCall(request)
                    val response: Response = call.execute()
                    if (response.isSuccessful) {
                        val responseBody = response.body
                        if (responseBody != null) {
                            return GtfsRealtime.VehiclePosition.parseFrom(responseBody.byteStream())
                        }
                    }
                    return null
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observable.subscribe(object : Consumer<GtfsRealtime.VehiclePosition> {
            override fun accept(t: GtfsRealtime.VehiclePosition?) {
                Log.d("foo", "Vehicle=${t?.vehicle}, currentStatus=${t?.currentStatus}, Trip=${t?.trip}")
            }
        })

//        setContent {
//            MARTATrainTimeRemakeTheme {
//                App()
//            }
//        }
    }
}

@ExperimentalAnimationApi
@Composable
fun App() {
    TrainsScreen()
}