/*
 * Copyright 2021 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Why Not Compose!
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Why-Not-Compose
 */

package org.imaginativeworld.whynotcompose.ui.screens.tutorial.reactivemodel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.imaginativeworld.whynotcompose.common.compose.compositions.AppComponent
import org.imaginativeworld.whynotcompose.common.compose.theme.AppTheme

/**
 * Resource: https://developer.android.com/codelabs/jetpack-compose-state#11
 */

@Composable
fun ReactiveModelScreen(
    viewModel: ReactiveModelViewModel,
    goBack: () -> Unit
) {
    val counter = viewModel.products.collectAsState()

    ReactiveModelScreenSkeleton(
        products = counter.value,
        totalPrice = viewModel.totalPrice.value,
        goBack = goBack,
        increaseQuantity = viewModel::incrementQuantity,
        decreaseQuantity = viewModel::decreaseQuantity
    )
}

@PreviewLightDark
@Composable
private fun ReactiveModelScreenSkeletonPreview() {
    val products by remember {
        mutableStateOf(
            ProductReactiveModelMock.items
        )
    }

    AppTheme {
        ReactiveModelScreenSkeleton(
            products = products,
            totalPrice = products.sumOf { it.totalPrice },
            increaseQuantity = { product ->
                product.increaseQuantity()
            },
            decreaseQuantity = { product ->
                product.decreaseQuantity()
            }
        )
    }
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun ReactiveModelScreenSkeleton(
    products: List<ProductReactiveModel>,
    totalPrice: Double,
    goBack: () -> Unit = {},
    increaseQuantity: (ProductReactiveModel) -> Unit = {},
    decreaseQuantity: (ProductReactiveModel) -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            AppComponent.Header(
                "Reactive Model",
                goBack = goBack
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(products) { product ->
                    ProductItemView(
                        modifier = Modifier,
                        name = product.name,
                        icon = product.icon,
                        price = product.price,
                        quantity = product.quantity,
                        totalPrice = product.totalPrice,
                        increase = { increaseQuantity(product) },
                        decrease = { decreaseQuantity(product) }
                    )
                }
            }

            HorizontalDivider()

            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("Total Price")
                Spacer(Modifier.weight(1f))
                Text(
                    "$totalPrice $",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun ProductItemView(
    name: String,
    icon: String,
    price: Double,
    quantity: Int,
    totalPrice: Double,
    increase: () -> Unit,
    decrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                icon,
                modifier = Modifier,
                style = MaterialTheme.typography.displayLarge
            )

            Text(
                name,
                modifier = Modifier,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge
            )

            Row(Modifier.padding(top = 8.dp)) {
                Text(
                    "Price:",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(Modifier.weight(1f))
                Text(
                    "$price $",
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Row {
                Text(
                    "Total:",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                Text(
                    "$totalPrice $",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Card(
                Modifier.padding(top = 8.dp),
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.onBackground.copy(0.2f)
                )
            ) {
                Row(
                    Modifier
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledIconButton(
                        onClick = { decrease() },
                        enabled = quantity > 0
                    ) {
                        Icon(imageVector = Icons.Rounded.Remove, contentDescription = "Add")
                    }

                    Text(
                        "$quantity",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleSmall
                    )

                    FilledIconButton(
                        onClick = { increase() },
                        enabled = quantity < 30
                    ) {
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add")
                    }
                }
            }
        }
    }
}
