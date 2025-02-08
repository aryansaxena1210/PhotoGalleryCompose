package com.example.photogallerycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.photogallerycompose.photo_contract.AppState
import com.example.photogallerycompose.ui.screens.MostRecentPhotosScreen
import com.example.photogallerycompose.viewmodel.PhotoViewModel
import com.example.photogallerycompose.ui.screens.MostViewedPhotosScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoGalleryApp(context = this)
        }
    }
}

@Composable
fun PhotoGalleryApp(context: MainActivity) {
    val photoViewModel : PhotoViewModel = viewModel<PhotoViewModel>()
    val state = photoViewModel.appState.collectAsState()

    if(state.value.isLoading){
        Text("Loading...")
    }
    else if(state.value.error!=null){
        Text(state.value.error!!)
    }
    else{
        Navigation(state)
    }

    LaunchedEffect(Unit) {
        photoViewModel.fetchPhotos(context)
    }
}

@Composable
fun Navigation(state: State<AppState>) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "most_viewed_photos_screen") {

        composable("most_viewed_photos_screen") {
            MostViewedPhotosScreen(state, navController)
        }

        composable("most_recent_photos_screen") {
            MostRecentPhotosScreen(state, navController)
        }
    }

}


//@Composable
//fun PhotoGalleryApp(context: MainActivity) {
//
//    var photoItem by remember { mutableStateOf<PhotoItem?>(null) }
//    var isLoading by remember { mutableStateOf(true) }
//    var error by remember { mutableStateOf<String?>(null) }
//
//    // Fetch data when the composable is first launched
//    LaunchedEffect(Unit) {
//        fetchPhotoData(
//            onSuccess = { item ->
//                photoItem = item
//                isLoading = false
//            },
//            onError = { message ->
//                error = message
//                isLoading = false
//            },
//            context = context
//        )
//    }
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        when {
//            isLoading -> Text("Loading...")
//            error != null -> Text("Error: $error")
//            photoItem != null -> PhotoItemView(photoItem!!)
//        }
//    }
//}
//
//@Composable
//fun PhotoItemView(photoItem: PhotoItem) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Image(
//            painter = rememberAsyncImagePainter(model = photoItem.download_url),
//            contentDescription = "Photo by ${photoItem.author}",
//            modifier = Modifier.size(300.dp)
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Text(text = "Author: ${photoItem.author}")
//    }
//}
//
//fun fetchPhotoData(
//    onSuccess: (PhotoItem) -> Unit,
//    onError: (String) -> Unit,
//    context: MainActivity,
//) {
//    val url = "https://picsum.photos/v2/list"
//    val queue = Volley.newRequestQueue(context)
//
//    val jsonArrayRequest = JsonArrayRequest(
//        Request.Method.GET, url, null,
//        { response ->
//            val firstItem = response.getJSONObject(0)
//            val photoItem = PhotoItem(
//                author = firstItem.getString("author"),
//                download_url = firstItem.getString("download_url"),
//                height = firstItem.getInt("height"),
//                id = firstItem.getString("id"),
//                url = firstItem.getString("url"),
//                width = firstItem.getInt("width")
//            )
//            onSuccess(photoItem)
//        },
//        { error ->
//            onError(error.message ?: "Unknown error occurred")
//        }
//    )
//
//    queue.add(jsonArrayRequest)
//}