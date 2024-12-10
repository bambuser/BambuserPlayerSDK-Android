package com.bambuser.liveshopping.player

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.bambuser.liveshopping.Parameters
import com.bambuser.player.LVSPlayer
import com.bambuser.player.PiPError
import com.bambuser.player.infrastructure.utils.delegates.PiPActivityDelegate
import com.bambuser.player.infrastructure.utils.delegates.PiPDelegate

import com.bambuser.player.io.LVSPlayerInputAction
import kotlinx.coroutines.launch

/*
* Make sure this activity has android:supportsPictureInPicture="true" in its manifest.
* */
class ComposeHostActivity : ComponentActivity(),
    // Add this line if you want to use PiP, or write your own pip implementation
    PiPDelegate by PiPActivityDelegate()
{

    private var isDialogVisible = false

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parameters = intent.getParcelableExtra(KEY_PARAMETERS, Parameters::class.java)

        val playerId = "player_123"

        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ActionsButtons(playerId)
                parameters?.let {
                    LVSPlayer.GetComposeScreen(
                        playerId = playerId,
                        context = this@ComposeHostActivity,
                        isEUServer = false,
                        modifier = Modifier.fillMaxSize(),
                        showId = it.showId,
                        configuration = it.configuration.toConfiguration(),
                        pipStateFlow = this@ComposeHostActivity.pipMode,
                        onPipRequest = {
                            // This is part of PiPDelegate
                            enterPiP(
                                activity = this@ComposeHostActivity,
                                shouldEnablePiP = parameters.configuration.enablePictureInPicture,
                                aspectRatio = pipRational?.buildRational(), // OR add your own rational
                                onSuccess = {
                                    // Success callback
                                    Log.d("pip", "Entered PiP successfully")
                                },
                                onError = { error ->
                                    // Error callback, Handle different errors
                                    val pipErrorCause = when (error) {
                                        PiPError.DisabledByClient -> "PiP was disabled in the SDK configuration"
                                        PiPError.DisabledByUser -> "PiP was disabled by the Android user"
                                        PiPError.NoActivePlayer -> "There is no active LVSPlayer"
                                        PiPError.NotSupported -> "PiP is not supported on this device"
                                        PiPError.SdkIsNotInitialized -> "SDK is not initialized yet"
                                    }
                                    Log.e("pip", "Failed to enter PIP because of $pipErrorCause")
                                },
                            )
                        },
                        eventObserver = EventObserverExample,
                    )
                }
                ActionsButtons(playerId)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            EventObserverExample.errorStateFlow.collect {
                if (!isDialogVisible) {
                    it?.let { error ->
                        isDialogVisible = true
                        AlertDialog.Builder(this@ComposeHostActivity)
                            .setTitle("Something went wrong")
                            .setMessage("The player threw the following error: ${error.message}")
                            .setCancelable(false)
                            .setPositiveButton("Ok") { dialog, _ ->
                                dialog.dismiss()
                                EventObserverExample.error = null
                                isDialogVisible = false
                                finish()
                            }
                            .create()
                            .show()
                    }
                }
            }
        }
    }

    companion object {
        const val KEY_PARAMETERS: String = "KEY_PARAMETERS"
    }
}

@Composable
fun ActionsButtons(playerId: String) {
    Row {
        IconButton(onClick = { LVSPlayer.performAction(LVSPlayerInputAction.Pause(playerId))}) {
            Icon(painter = painterResource(id = com.bambuser.player.R.drawable.ic_pause) , contentDescription = "pause button" )
        }

        IconButton(onClick = { LVSPlayer.performAction(LVSPlayerInputAction.Play(playerId))}) {
            Icon(painter = painterResource(id = com.bambuser.player.R.drawable.ic_play), contentDescription = "play button" )
        }

        IconButton(onClick = { LVSPlayer.performAction(LVSPlayerInputAction.Replay(playerId))}) {
            Icon(painter = painterResource(id = com.bambuser.player.R.drawable.ic_replay), contentDescription = "replay button" )
        }

        IconButton(onClick = { LVSPlayer.performAction(LVSPlayerInputAction.ToggleChatVisibility(playerId))}) {
            Icon(painter = painterResource(id = com.bambuser.player.R.drawable.ic_chat_on), contentDescription = "Toggle chat button" )
        }

        IconButton(onClick = { LVSPlayer.performAction(LVSPlayerInputAction.Minimize(playerId))}) {
            Icon(painter = painterResource(id = com.bambuser.player.R.drawable.ic_minimize), contentDescription = "Minimize button" )
        }
    }
}
