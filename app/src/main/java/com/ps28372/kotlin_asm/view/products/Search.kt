package com.ps28372.kotlin_asm.view.products

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ps28372.kotlin_asm.R
import com.ps28372.kotlin_asm.utils.CartHelper
import com.ps28372.kotlin_asm.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(navController: NavController, productViewModel: ProductViewModel) {
    val isLoading = productViewModel.isLoading.observeAsState(initial = false)
    val searchProducts = productViewModel.searchProducts.observeAsState(initial = emptyList())

    val context = LocalContext.current
    val cartHelper = CartHelper(context)

    var query by remember {
        mutableStateOf("")
    }

    fun searchHandler(value: String) {
        Log.d("Search", "searchHandler: $value")
        productViewModel.getSearchProducts(value)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(16.dp),
            ) {
                SearchBar(query = query,
                    onQueryChange = {
                        query = it
                    },
                    colors = SearchBarDefaults.colors(
                        containerColor = Color.White,
                    ),
                    onSearch = {
                        searchHandler(it)
                    },
                    placeholder = { Text(text = "Search products") },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "Search",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .background(Color.White),
                    active = false,
                    content = { },
                    onActiveChange = { })
            }
            if (isLoading.value) {
                Dialog(onDismissRequest = { /* Dialog cannot be dismissed */ }) {
                    CircularProgressIndicator(
                        color = Color(0xff242424),
                    )
                }
            }
            if (searchProducts.value.isEmpty()) {
                Text(
                    text = "No products found", modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn {
                    items(searchProducts.value.size) { index ->
                        val product = searchProducts.value?.get(index)
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(1f)
                                .height(100.dp)
                                .padding(horizontal = 24.dp)
                                .clickable {
                                    navController.navigate(
                                        "productDetails/${product?.id}"
                                    )
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            AsyncImage(
                                model = product?.getFirstImageUrl(),
                                placeholder = painterResource(id = R.drawable.boarding_bg),
                                contentDescription = "Product Image",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .fillMaxWidth(0.9f),
                            ) {
                                product?.name?.let {
                                    Text(
                                        text = it,
                                        fontSize = 14.sp,
                                        color = Color(0xff606060),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "$ ${product?.price}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(
                                modifier = Modifier.fillMaxHeight(1f),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                                Image(painter = painterResource(id = R.drawable.add_to_card),
                                    contentDescription = "Add to cart",
                                    modifier = Modifier.clickable {
                                        product?.let { cartHelper.addItem(it) }
                                        cartHelper.saveCart(context)
                                        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT)
                                            .show()
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}
