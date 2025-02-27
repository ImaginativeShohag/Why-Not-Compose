package com.example.store.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.compositions.KeyValue
import kotlinx.coroutines.launch

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun ProfileScreen(
    onDismiss: () -> Unit,
    onOrdersClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    ProfileScreenSkeleton(
        onDismiss = onDismiss,
        onOrdersClick = onOrdersClick,
        onSignOutClick = onSignOutClick
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun ProfileScreenSkeleton(
    onDismiss: () -> Unit,
    onOrdersClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Profile",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    actions = {
                        TextButton(
                            onClick = { scope.launch { sheetState.hide() }.invokeOnCompletion { onDismiss() } },
                            modifier = Modifier
                        ) {
                            Text(
                                text = "Done",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                )
            },
            contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(org.imaginativeworld.whynotcompose.common.compose.R.drawable.store),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "DETAILS",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.outline,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .padding(16.dp)
                    ) {
                        KeyValue(
                            title = "Name",
                            value = "John Doe"
                        )

                        HorizontalDivider()

                        KeyValue(
                            title = "Username",
                            value = "johnd"
                        )

                        HorizontalDivider()

                        KeyValue(
                            title = "Email",
                            value = "john@gmail.com"
                        )

                        HorizontalDivider()

                        KeyValue(
                            title = "Phone",
                            value = "1-570-236-7033"
                        )

                        HorizontalDivider()

                        KeyValue(
                            title = "Address",
                            value = "New Road, Kilcoole"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onOrdersClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Orders",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onSignOutClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Sign Out",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun ProfileScreenSkeletonPreview() {
    StoreAppTheme {
        ProfileScreenSkeleton(
            onDismiss = {},
            onOrdersClick = {},
            onSignOutClick = {}
        )
    }
}
