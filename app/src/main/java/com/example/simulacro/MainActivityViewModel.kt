package com.example.simulacro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

class MainActivityViewModel : ViewModel(){

    private val _isVisible by lazy { MediatorLiveData<Boolean>() }
    val isVisible : LiveData<Boolean>
        get() = _isVisible

    suspend fun setIsVisibleInMainThread(value : Boolean) = withContext(Dispatchers.Main){
        _isVisible.value = value
    }

    private val _responseUser by lazy { MediatorLiveData<UserResponse>() }
    val responseUser : LiveData<UserResponse>
        get() = _responseUser

    suspend fun setResponseUserInMainThread(userResponse: UserResponse) = withContext(Dispatchers.Main){
        _responseUser.value = userResponse
    }

    private val _responseText by lazy { MediatorLiveData<String>() }
    val responseText : LiveData<String>
        get() = _responseText

    suspend fun setResponseTextInMainThread(value : String) = withContext(Dispatchers.Main){
        _responseText.value = value
    }

    fun getAllUsers() {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                setIsVisibleInMainThread(true)

                val client = OkHttpClient()

                val request = Request.Builder()
                request.url("http://randomuser.me/api/?results=100")


                val call = client.newCall(request.build())
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println(e.toString())
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                            setResponseTextInMainThread("Algo ha ido mal")
                            setIsVisibleInMainThread(false)
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        println(response.toString())
                        response.body?.let { responseBody ->

                            val body = responseBody.string()
                            println(body)
                            val gson = Gson()
                            val userResponse = gson.fromJson(body, UserResponse::class.java)
                            println(userResponse)
                            
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000)
                                setResponseUserInMainThread(userResponse)
                                setIsVisibleInMainThread(false)
                            }
                        }
                    }
                })
            }
        }
    }
}