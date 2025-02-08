package com.example.photogallerycompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.photogallerycompose.photo_contract.AppState

@Composable
fun MostRecentPhotosScreen(state: State<AppState>, navController: NavHostController) {
    NavigationBar {
        Button(onClick = { navController.navigate("most_viewed_photos_screen") }){
            Text(text = "Most Viewed Photos")
        }
        Button(onClick = { navController.navigate("most_recent_photos_screen") }){
            Text(text = "Most Recent Photos")
        }
    }

    Box(modifier = Modifier.padding(top = 100.dp)) {
        Text(
            text = "Most Recent Photos", modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(state.value.mostRecentPhotos!!.size) { index ->
                val photo = state.value.mostRecentPhotos!![index]
                Image(
                    painter = rememberAsyncImagePainter(model = photo.download_url),
                    contentDescription = "Photo by ${photo.author}",
                    modifier = Modifier.size(200.dp)
                )
                Text(text = "Author: ${photo.author}")
            }
        }
    }

}