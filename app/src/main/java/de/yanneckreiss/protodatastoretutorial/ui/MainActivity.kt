@file:OptIn(ExperimentalLifecycleComposeApi::class)

package de.yanneckreiss.protodatastoretutorial.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import de.yanneckreiss.protodatastoretutorial.ui.theme.DatatStoreWithKotlinSupportTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatatStoreWithKotlinSupportTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SampleScreen()
                }
            }
        }
    }
}

@Composable
private fun SampleScreen(viewModel: MainViewModel = viewModel()) {

    val viewState: MainViewModel.ViewState by viewModel.viewState.collectAsStateWithLifecycle()

    SampleContent(
        appStartupCounter = viewState.appCounter,
        lastAppOpeningTimestamp = viewState.lastStartup,
        resetData = viewModel::resetData
    )
}

@Composable
private fun SampleContent(
    appStartupCounter: Int,
    lastAppOpeningTimestamp: String,
    resetData: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column() {
                    Text(text = "Counter:", fontWeight = FontWeight.Bold)
                    Text(text = "Last opening:", fontWeight = FontWeight.Bold)
                }

                MediumSpacer()

                Column {
                    Text(text = appStartupCounter.toString())
                    Text(text = lastAppOpeningTimestamp)
                }
            }

            MediumSpacer()

            Button(onClick = resetData) {
                Text(text = "Reset data")
            }
        }
    }
}

@Composable
private fun MediumSpacer(modifier: Modifier = Modifier) = Spacer(modifier = modifier.size(16.dp))

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DatatStoreWithKotlinSupportTheme {
        SampleContent(
            appStartupCounter = 0,
            lastAppOpeningTimestamp = "",
            resetData = {}
        )
    }
}
