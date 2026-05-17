package com.willydev.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.willydev.rickandmortyapp.data.api.RetrofitClient
import com.willydev.rickandmortyapp.data.repository.RickAndMortyRepository
import com.willydev.rickandmortyapp.ui.screens.CharacterListScreen
import com.willydev.rickandmortyapp.ui.screens.CharacterListViewModel
import com.willydev.rickandmortyapp.ui.theme.RickAndMortyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val repository = RickAndMortyRepository(RetrofitClient.apiService)
                    val factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return CharacterListViewModel(repository) as T
                        }
                    }
                    val viewModel: CharacterListViewModel = viewModel(factory = factory)

                    CharacterListScreen(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}