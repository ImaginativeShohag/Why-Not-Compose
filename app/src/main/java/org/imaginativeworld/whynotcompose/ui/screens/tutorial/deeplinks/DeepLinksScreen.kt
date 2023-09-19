package org.imaginativeworld.whynotcompose.ui.screens.tutorial.deeplinks

import android.content.Intent
import android.content.pm.verify.domain.DomainVerificationManager
import android.content.pm.verify.domain.DomainVerificationUserState
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.whynotcompose.base.extensions.openUrl
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

/**
 * Resources:
 * - https://developer.android.com/training/app-links
 */

@Composable
fun DeepLinksScreen(
    goBack: () -> Unit
) {
    val context = LocalContext.current

    var verifiedDomains by remember { mutableStateOf("Loading...") }
    var selectedDomains by remember { mutableStateOf("Loading...") }
    var unapprovedDomains by remember { mutableStateOf("Loading...") }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = context.getSystemService(DomainVerificationManager::class.java)

            val userState = manager.getDomainVerificationUserState(context.packageName)

            // Domains that have passed Android App Links verification.
            verifiedDomains = userState?.hostToStateMap
                ?.filterValues { it == DomainVerificationUserState.DOMAIN_STATE_VERIFIED }
                ?.map { it.key }
                ?.joinToString() ?: "No verified domains."

            if (verifiedDomains.isEmpty()) {
                verifiedDomains = "No verified domains."
            }

            // Domains that haven't passed Android App Links verification but that the user
            // has associated with an app.
            selectedDomains = userState?.hostToStateMap
                ?.filterValues { it == DomainVerificationUserState.DOMAIN_STATE_SELECTED }
                ?.map { it.key }
                ?.joinToString() ?: "No selected domains."

            if (selectedDomains.isEmpty()) {
                selectedDomains = "No selected domains."
            }

            // All other domains.
            unapprovedDomains = userState?.hostToStateMap
                ?.filterValues { it == DomainVerificationUserState.DOMAIN_STATE_NONE }
                ?.map { it.key }
                ?.joinToString() ?: "No un-approved domains."

            if (unapprovedDomains.isEmpty()) {
                unapprovedDomains = "No un-approved domains."
            }
        } else {
            verifiedDomains = "Available from Android S (API 31)"
            selectedDomains = "Available from Android S (API 31)"
            unapprovedDomains = "Available from Android S (API 31)"
        }
    }

    DeepLinksScreenSkeleton(
        goBack = goBack,
        verifiedDomains = verifiedDomains,
        selectedDomains = selectedDomains,
        unapprovedDomains = unapprovedDomains,
        onUpdateOpenByDefaultClicked = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val intent = Intent(
                    Settings.ACTION_APP_OPEN_BY_DEFAULT_SETTINGS,
                    Uri.parse("package:${context.packageName}")
                )
                context.startActivity(intent)
            }
        }
    )
}

@Preview
@Composable
fun DeepLinksScreenSkeletonPreview() {
    AppTheme {
        DeepLinksScreenSkeleton(
            verifiedDomains = "Lorem",
            selectedDomains = "Ipsum",
            unapprovedDomains = "Dolor"
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DeepLinksScreenSkeletonPreviewDark() {
    AppTheme {
        DeepLinksScreenSkeleton(
            verifiedDomains = "Lorem",
            selectedDomains = "Ipsum",
            unapprovedDomains = "Dolor"
        )
    }
}

@Composable
fun DeepLinksScreenSkeleton(
    goBack: () -> Unit = {},
    verifiedDomains: String,
    selectedDomains: String,
    unapprovedDomains: String,
    onUpdateOpenByDefaultClicked: () -> Unit = {}
) {
    val context = LocalContext.current

    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AppComponent.Header(
                "Deep Link",
                goBack = goBack
            )

            Divider()

            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "Try the following deep links to open the deep link activity.",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )

                    OutlinedButton(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            context.openUrl("app://why-not-compose")
                        }
                    ) {
                        Text(
                            text = "app://why-not-compose",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        )
                    }

                    OutlinedButton(
                        modifier = Modifier.padding(top = 8.dp),
                        onClick = {
                            context.openUrl("http://imaginativeworld.org/why-not-compose")
                        }
                    ) {
                        Text(
                            text = "http://imaginativeworld.org/why-not-compose",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        )
                    }

                    OutlinedButton(
                        modifier = Modifier.padding(top = 8.dp),
                        onClick = {
                            context.openUrl("https://imaginativeworld.org/why-not-compose")
                        }
                    ) {
                        Text(
                            text = "https://imaginativeworld.org/why-not-compose",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        )
                    }
                }

                Divider(Modifier.padding(vertical = 16.dp))

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppComponent.SubHeader(text = "Domain Verification Status")

                    Text(
                        text = "Verified Domains",
                        fontSize = 21.sp
                    )

                    Text(
                        text = verifiedDomains,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "Selected Domains",
                        fontSize = 21.sp
                    )

                    Text(
                        text = selectedDomains,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "Unapproved Domains",
                        fontSize = 21.sp
                    )

                    Text(
                        text = unapprovedDomains,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center
                    )

                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        enabled = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
                        onClick = {
                            onUpdateOpenByDefaultClicked()
                        }) {
                        Text(text = "Update 'Open by default' Setting")
                    }
                }
            }
        }
    }
}
