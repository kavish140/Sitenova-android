package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun ShowcaseScreen(
    onShowLinkError: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    val cardGradBrush = remember {
        Brush.linearGradient(
            colors = listOf(Color(0xFF161616), Color(0xFF0B0B0B))
        )
    }

    val customerSites = remember {
        listOf(
            CustomerSite("drdiptiganatra.com", "Medical & Consulting", "Medical portfolio & consulting hub optimized for local searches. Top homeopathic doctor in Maharashtra.", "https://drdiptiganatra.com"),
            CustomerSite("jupiterfastfinance.com", "Finance", "Corporate financial service discovery framework with ultra-low latencies.", "https://jupiterfastfinance.com"),
            CustomerSite("aismartkit.tech", "Technology & AI", "AI micro-tool aggregator built with modern React components and highly scalable architecture.", "https://aismartkit.tech"),
            CustomerSite("design.sitenova.dev", "Design Portfolio", "Modern interface design showcase with fluid animations, bold typography, and visual aesthetics.", "https://design.sitenova.dev/"),
            CustomerSite("ecommerce.sitenova.dev", "E-Commerce", "High-performance digital storefront optimized for conversions, fast checkout flows, and product discovery.", "https://ecommerce.sitenova.dev/"),
            CustomerSite("buisness-showcase.sitenova.dev", "Business Showcase", "Comprehensive corporate presentation highlighting essential services, client portfolios, and enterprise capabilities.", "https://buisness-showcase.sitenova.dev/")
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        item {
            Text(
                text = "Showcase & Portfolio",
                style = MaterialTheme.typography.displaySmall,
                color = PrimaryWhite,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Live implementations of our high-performance frontend architecture and SEO optimization across different industries.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        items(customerSites) { site ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, BorderSlate, RoundedCornerShape(20.dp))
                    .background(brush = cardGradBrush)
                    .clickable {
                        try {
                            uriHandler.openUri(site.url)
                        } catch (e: Exception) {
                            onShowLinkError("Could not open ${site.url}")
                        }
                    }
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = AccentSky,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = site.category.uppercase(),
                                color = AccentSky,
                                style = MaterialTheme.typography.labelLarge,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = site.domain,
                            color = PrimaryWhite,
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(DarkSurfaceElevated),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Visit Site",
                            tint = PrimaryWhite,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = site.description,
                    color = TextSecondary,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

data class CustomerSite(val domain: String, val category: String, val description: String, val url: String)
