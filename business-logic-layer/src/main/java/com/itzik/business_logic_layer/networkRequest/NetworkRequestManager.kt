package com.example.bl.networkRequest

import android.annotation.SuppressLint
import android.util.Log
import com.itzik.business_logic_layer.networkRequest.ResponseEvent
import com.itzik.business_logic_layer.networkRequest.model.ResponseRequest
import com.itzik.commons.datamodels.BankGenerator
import com.itzik.commons.datamodels.BankInfoData
import com.itzik.commons.datamodels.ResponseData
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class NetworkRequestManager : CoroutineScope by MainScope() {


    private val TAG: String = NetworkRequestManager::class.java.simpleName


    enum class RequestMethods {
        GET {
            override fun getMethod(): String {
                return GET.name
            }

        },
        PUT {
            override fun getMethod(): String {
                return PUT.name
            }
        },
        DELET {
            override fun getMethod(): String {
                return DELET.name
            }

        };

        abstract fun getMethod(): String
    }



    private var client: OkHttpClient

    init {
        Log.d(TAG, "INIT")
        client = OkHttpClient()
    }


    fun cleanup() {
        cancel()
        Log.d(TAG, "cleanup all request")
    }

    fun getBankInfoRequestNetwork(param: String, paramInt: String, event: ResponseEvent) {
        launch {
            val response = async(Dispatchers.IO) { requestNetwork(param, paramInt) }
            // switch from main thread and wait until receive response
            val resWait = response.await()
            event.onResponse(resWait.data.bankInfoList, resWait.codeResponse)
        }
        Log.d(TAG, "getMovieRequestNetwork")
    }


    private fun requestNetwork(paramString: String, paramInt: String): ResponseRequest {
        Log.d(TAG, "requestNetwork:")
        var response = getRequest(BankGenerator.makeApiUrlRequest(paramString, paramInt), RequestMethods.GET.getMethod())
//        val turnsType = object : TypeToken<ResponseData>() {}.type
//        var responsData: ResponseData =
//            Gson().fromJson(response.body()!!.string(), turnsType)
//        Log.d(TAG, Arrays.deepToString(responsData.result.toTypedArray()))
        var responseRequest = ResponseRequest(parseResponse(response.body()?.string(), paramInt), response.code())
        return responseRequest
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseResponse(response: String?, paramInterval: String): ResponseData{
         Log.d(TAG,"response = $response")
        val responseJson: JSONObject
        val list: ArrayList<BankInfoData> = ArrayList()
        try {
            responseJson  = JSONObject(response)
            val timeSeriesJson = responseJson.getJSONObject("Time Series (${paramInterval}min)")
            Log.d(TAG,"timeSeriesJson SIZE = ${timeSeriesJson.length()}")
            Log.d(TAG,"timeSeriesJson names = ${timeSeriesJson.names()}")
            for(i in 0 until timeSeriesJson.names().length()) {
                val temp = timeSeriesJson.getJSONObject(timeSeriesJson.names().get(i).toString())
                Log.d(TAG, "i = $i -  ${temp}")
                val open: String = temp.get("1. open") as String
                val high:String = temp.get("2. high") as String
                val low: String =  temp.get("3. low") as String
                val close: String =  temp.get("4. close") as String
                val volume: String  =  temp.get("5. volume") as String
                Log.d(TAG,"open = $open, high = $high, low = $low, close = $close, volume = $volume")
                val bankInfoData = BankInfoData(convertDateToTime(timeSeriesJson.names().getString(i)).toString(), open, high, low, close, volume)
                list.add(bankInfoData)
            }



        }catch (e: Exception){
            Log.e(TAG,"parseResponse ERROR = ${e.message}")
           return ResponseData(Collections.emptyList())
        }
        return ResponseData(list)
    }

    private fun getYesterdayDateString(): String? {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(yesterday())
    }

    private fun yesterday(): Date? {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        return cal.time
    }


    private fun convertDateToTime(dateString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.parse(dateString)
        Log.d(TAG,"Long = ${date.time}")
        val outPutFormat =  SimpleDateFormat("HH:mm:ss")
        return outPutFormat.format(date).toString()
    }

    private fun getRequest(url: String, method: String): Response {
        Log.d(TAG, "request")
        Thread.sleep(500)
        val request = Request.Builder()
            .url(url)
            .method(method, null)
            .build()
        val response = client.newCall(request).execute()

        return response

    }

}