package com.code_of_duty.u_tracker.ui.screens.profile

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code_of_duty.u_tracker.ui.components.profile.ProfileCard
import com.code_of_duty.u_tracker.ui.components.profile.StatisticCard

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    @SuppressLint("MutableCollectionMutableState")
    @Composable
    fun ProfileScreen(
        viewModel: ProfileViewModel = hiltViewModel()
    ) {
        val loading = remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            viewModel.getStudentInfo()
        }

        val code by viewModel.code.collectAsState()
        val username by viewModel.username.collectAsState()
        val image by viewModel.image.collectAsState()
        val cum by viewModel.cum.collectAsState()
        val degree by viewModel.degree.collectAsState()
        val approvedSubjects by viewModel.approvedSubjects.collectAsState()

        Log.e("Data", code)
        Log.e("Loading", loading.toString())

        if (loading.value && code.isEmpty()) {
            CircularProgressIndicator()
        } else {
            Column(
                /*modifier = Modifier
                    .padding(8.dp),*/
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                degree?.let {
                    ProfileCard(
                        code = code,
                        username = username,
                        career = it.name,
                        pensum = degree!!.pensums?.firstOrNull()?.plan ?: "",
                        image = "https://img.freepik.com/free-icon/user_318-159711.jpg?w=2000"
                    )
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatisticCard(label = "CUM", cardType = 1, approvedSubjects = null)
                    StatisticCard(label = "CUM", cardType = 2, approvedSubjects = null)
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatisticCard(label = "CUM", cardType = 3, approvedSubjects = approvedSubjects)
                    StatisticCard(label = "CUM", cardType = 4, approvedSubjects = null)
                }
            }
        }
    }

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}