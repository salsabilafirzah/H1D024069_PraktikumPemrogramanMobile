package com.pemmob.salsabilafirzah.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pemmob.salsabilafirzah.R
import com.pemmob.salsabilafirzah.data.dummy.DummyData
import com.pemmob.salsabilafirzah.data.model.Category
import com.pemmob.salsabilafirzah.data.model.Product
import com.pemmob.salsabilafirzah.ui.theme.JualanTheme
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.compose.ui.graphics.asImageBitmap

// =========================================
// 1. ProductItemCard
// =========================================
@Composable
fun ProductItemCard(product: Product, onClick: () -> Unit) {
    val context = LocalContext.current
    val appIconBitmap = remember(context) {
        ContextCompat.getDrawable(context, R.mipmap.ic_launcher)?.toBitmap()?.asImageBitmap()
    }

    Card(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(all = 12.dp)) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (appIconBitmap != null) {
                    Image(
                        bitmap = appIconBitmap,
                        contentDescription = product.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(shape = RoundedCornerShape(size = 8.dp))
                            .background(color = Color.White),
                        contentScale = ContentScale.Fit
                    )
                }

                if (product.category != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(all = 4.dp)
                            .clip(shape = RoundedCornerShape(size = 4.dp))
                            .background(color = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(
                            text = product.category.name,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Rp ${product.price}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// =========================================
// 2. CategoryItem
// =========================================
@Composable
fun CategoryItem(category: Category, isSelected: Boolean, onClick: () -> Unit) {
    val containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

    Card(
        modifier = Modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = category.name,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontWeight = FontWeight.Medium
        )
    }
}

// =========================================
// 3. DaftarProdukScreen (Main Screen)
// =========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarProdukScreen() {
    var selectedCategoryId by remember { mutableStateOf(DummyData.categories.firstOrNull()?.id) }

    val filteredProducts = if (selectedCategoryId != null) {
        DummyData.products.filter { it.category_id == selectedCategoryId }
    } else {
        DummyData.products
    }

    val context = LocalContext.current
    val products = DummyData.products

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Produk UMKM") },
                actions = {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Cart"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Kategori Produk",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(all = 16.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(DummyData.categories) { category ->
                    CategoryItem(
                        category = category,
                        isSelected = category.id == selectedCategoryId,
                        onClick = { selectedCategoryId = category.id }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Daftar Produk",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 2),
                contentPadding = PaddingValues(all = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredProducts) { product ->
                    ProductItemCard(product = product) {
                        Toast.makeText(context, "Clicked: ${product.name}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}

// =========================================
// 4. Preview: ProductItemCard
// =========================================
@Preview(showBackground = true)
@Composable
fun PreviewProduct() {
    ProductItemCard(product = DummyData.products[0], onClick = {})
}

// =========================================
// 5. Preview: CategoryItem (icon di tengah layar)
// =========================================
@Preview(showBackground = true)
@Composable
fun PreviewCategory() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CategoryItem(
            category = DummyData.categories[0],
            isSelected = true,
            onClick = {}
        )
    }
}

// =========================================
// 6. Preview: DaftarProdukScreen (Light & Dark)
// =========================================
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun PreviewDaftarProdukScreen() {
    JualanTheme {
        DaftarProdukScreen()
    }
}