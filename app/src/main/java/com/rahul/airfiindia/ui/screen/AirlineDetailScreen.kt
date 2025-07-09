package com.rahul.airfiindia.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.rahul.airfiindia.ui.viewmodel.AirlineDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirlineDetailScreen(
    airlineId: String,
    onBackClick: () -> Unit,
    viewModel: AirlineDetailViewModel = hiltViewModel()
) {
    val airline by viewModel.airline.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(airlineId) {
        viewModel.loadAirline(airlineId)
    }

    airline?.let { airlineData ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Top App Bar
            TopAppBar(
                title = { Text(airlineData.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleFavorite() }) {
                        Icon(
                            imageVector = if (airlineData.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (airlineData.isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (airlineData.isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
            val imageLoader = ImageLoader.Builder(context)
                .components {
                    add(SvgDecoder.Factory())
                }
                .build()
            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                println("Logo URL: ${airlineData.logo_url}")
                // Airline Logo
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(airlineData.logo_url)
                        .crossfade(true)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    imageLoader = imageLoader,
                    contentDescription = "${airlineData.name} logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Airline Details
                DetailItem(
                    label = "Name",
                    value = airlineData.name
                )

                DetailItem(
                    label = "Country",
                    value = airlineData.country
                )

                DetailItem(
                    label = "Headquarters",
                    value = airlineData.headquarters
                )

                DetailItem(
                    label = "Fleet Size",
                    value = "${airlineData.fleet_size} aircraft"
                )

                // Website Button
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(airlineData.website))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Language, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Visit Website")
                }
            }
        }
    }
}

@Composable
private fun DetailItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}