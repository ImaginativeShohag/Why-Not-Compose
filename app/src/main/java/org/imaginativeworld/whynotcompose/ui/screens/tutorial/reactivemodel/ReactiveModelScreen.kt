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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

    CounterWithVMScreenSkeleton(
        products = counter.value,
        goBack = goBack,
        increaseQuantity = viewModel::incrementQuantity,
        decreaseQuantity = viewModel::decreaseQuantity
    )
}

@PreviewLightDark
@Composable
private fun CounterWithVMScreenSkeletonPreview() {
    val products by remember {
        mutableStateOf(
            ProductReactiveModelMock.items
        )
    }

    AppTheme {
        CounterWithVMScreenSkeleton(
            products = products,
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
fun CounterWithVMScreenSkeleton(
    products: List<ProductReactiveModel>,
    goBack: () -> Unit = {},
    increaseQuantity: (ProductReactiveModel) -> Unit = {},
    decreaseQuantity: (ProductReactiveModel) -> Unit = {}
) {
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
        ) {
            AppComponent.Header(
                "Reactive Model",
                goBack = goBack
            )

            HorizontalDivider()

            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                LazyColumn(
                    Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(products) { product ->
                        ProductItemView(
                            modifier = Modifier.padding(vertical = 8.dp),
                            name = product.name,
                            price = product.price,
                            quantity = product.quantity,
                            totalPrice = product.totalPrice,
                            increase = { increaseQuantity(product) },
                            decrease = { decreaseQuantity(product) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductItemView(
    name: String,
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
        Column(Modifier.padding(8.dp)) {
            Text(
                name,
                style = MaterialTheme.typography.titleLarge
            )

            Row {
                Text(
                    "Price: $price $",
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(Modifier.weight(1f))

                Text(
                    "Quantity: $quantity",
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Card(
                Modifier.padding(top = 8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground.copy(0.2f))
            ) {
                Row(
                    Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier,
                        onClick = { increase() },
                        shape = CircleShape
                    ) {
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add")
                    }

                    Spacer(Modifier.width(16.dp))

                    Button(
                        modifier = Modifier,
                        onClick = { decrease() },
                        shape = CircleShape
                    ) {
                        Icon(imageVector = Icons.Rounded.Remove, contentDescription = "Add")
                    }

                    Spacer(Modifier.weight(1f))

                    Text(
                        "Total: $totalPrice $",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
