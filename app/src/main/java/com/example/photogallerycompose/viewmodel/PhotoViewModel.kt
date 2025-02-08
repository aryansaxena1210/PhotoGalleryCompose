package com.example.photogallerycompose.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.photogallerycompose.data.PhotoItem
import com.example.photogallerycompose.photo_contract.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONArray
import kotlin.math.floor

class PhotoViewModel() :ViewModel(){
    private val _appState : MutableStateFlow<AppState> = MutableStateFlow(AppState())
    val appState: StateFlow<AppState> = _appState.asStateFlow()


    fun fetchPhotos(context: Context) {
        val queue = Volley.newRequestQueue(context)
        val url1= "https://picsum.photos/v2/list?page=1&limit=10"
        val url2= "https://picsum.photos/v2/list?page=2&limit=10"

        println("calling fetchPhotos")
        val jsonRequestMostViewedPhotos = JsonArrayRequest(Request.Method.GET, url2, null,
            {response ->
                onMostViewedPhotosResponse(response)
            },
            { error ->
                _appState.update{it.copy(isLoading = false, error = error.message)}
            }
        )

        val jsonRequestMostRecentPhotos = JsonArrayRequest(Request.Method.GET, url1, null,
            {response ->
                onMostRecentPhotosResponse(response)
            },
            {error ->
                _appState.update{it.copy(isLoading = false, error = error.message)}
            }
        )

        _appState.update { it.copy(isLoading = true) }
        queue.add(jsonRequestMostViewedPhotos)
        println("fetched MostViewed photos")
        _appState.update { it.copy(isLoading = true) }
        queue.add(jsonRequestMostRecentPhotos)
        println("fetched Most Recent photos")
    }

    private fun onMostRecentPhotosResponse(response: JSONArray) {
        val _mostRecentPhotos = mutableListOf<PhotoItem>()

        for (i in 0 until response.length()) {
            val photo = response.getJSONObject(i)
            _mostRecentPhotos.add(
                PhotoItem(
                    author = photo.getString("author"),
                    download_url = photo.getString("download_url"),
                    height = photo.getString("height").toInt(),
                    id = photo.getString("id"),
                    url = photo.getString("url"),
                    width = photo.getString("width").toInt()
                )
            )
        }
        val mostRecentPhotos = _mostRecentPhotos.toList()
        _appState.update { it.copy(isLoading = false, mostRecentPhotos = mostRecentPhotos) }
    }

    private fun onMostViewedPhotosResponse(response: JSONArray) {
        println("inside response callback")
        val _mostViewPhotos = mutableListOf<PhotoItem>()
        for (i in 0 until response.length()) {
            val photo = response.getJSONObject(i)
            _mostViewPhotos.add(
                PhotoItem(
                    author = photo.getString("author"),
                    download_url = photo.getString("download_url"),
                    height = photo.getString("height").toInt(),
                    id = photo.getString("id"),
                    url = photo.getString("url"),
                    width = photo.getString("width").toInt()
                )
            )
        }
        val mostViewPhotos = _mostViewPhotos.toList()
        _appState.update { it.copy(isLoading = false, mostViewedPhotos = mostViewPhotos) }
        println("response $response")
        println("mostViewPhotos ${appState.value.mostViewedPhotos}")
        println("appState $appState")
    }

    fun print(){
        println("printing... $appState")
    }
}
