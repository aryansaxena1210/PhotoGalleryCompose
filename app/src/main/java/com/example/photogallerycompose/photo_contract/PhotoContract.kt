package com.example.photogallerycompose.photo_contract

import com.example.photogallerycompose.data.PhotoItem

data class AppState(
    val isLoading :  Boolean = true,
    val mostViewedPhotos : List<PhotoItem>? = null,
    val mostRecentPhotos : List<PhotoItem>? = null,
    val error: String? = null
)


sealed class PhotoIntent {
    object FetchPhotos : PhotoIntent()
    data class PhotoSelected(val photoId: String) : PhotoIntent()
    data class Error(val message:String? = null):PhotoIntent()
}
