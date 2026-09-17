package com.pemmob.salsabilafirzah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pemmob.salsabilafirzah.ui.screen.BasicInfoScreen
import com.pemmob.salsabilafirzah.ui.screen.HubungiKamiScreen
import com.pemmob.salsabilafirzah.ui.theme.JualanTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            JualanTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "basic_info"
                    ) {

                        composable(
                            route = "basic_info"
                        ) {
                            BasicInfoScreen(
                                onNavigateToContact = {
                                    navController.navigate(
                                        route = "form_screen"
                                    )
                                }
                            )
                        }

                        composable(
                            route = "form_screen"
                        ) {
                            HubungiKamiScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun LayoutTentangJualan(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val appIconBitmap = remember(context) {
        ContextCompat.getDrawable(
            context,
            R.mipmap.ic_launcher_round
        )?.toBitmap()?.asImageBitmap()
            ?: ContextCompat.getDrawable(
                context,
                R.mipmap.ic_launcher
            )?.toBitmap()?.asImageBitmap()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Lingkaran Foto/Logo Utama
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (appIconBitmap != null) {

                    Image(
                        bitmap = appIconBitmap,
                        contentDescription = "Jualan",
                        modifier = Modifier.size(150.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Text(
                    text = "Jualan",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // Judul Halaman
        Text(
            text = "Tentang Jualan",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Deskripsi Aplikasi
        Text(
            text = "Aplikasi Jualan adalah platform yang wadahi produk lokal UMKM di wilayah Kabupaten Purbalingga, Jawa Tengah.",
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        // Baris Misi
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0E0E0))
                .padding(all = 16.dp)
        ) {

            Text(
                text = "Misi Kami:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Memajukan UMKM Lokal",
                modifier = Modifier.weight(2f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LayoutTentangJualanPreview() {

    JualanTheme {
        LayoutTentangJualan()
    }
}