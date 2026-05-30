package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServicesScreen(
    onNavigateToQuote: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val services = remember {
        listOf(
            ServiceDetail(
                id = "ecommerce",
                title = "Ecommerce & Digital Storefronts",
                shortDesc = "Custom, lightning-fast shopping experiences built to maximize product sales.",
                icon = Icons.Default.ShoppingCart,
                techStack = listOf("Tailwind CSS", "React Admin", "Payment Gateways", "Stripe API"),
                longDescription = "We create highly immersive, conversion-friendly storefront designs. Traditional web builders (like WordPress or Shopify) add unnecessary bloat. Our custom Jamstack and React architectures generate raw static representations that load instantly under milliseconds. Equipped with localized checkout parameters, item cards, and instant analytics databases.",
                timeLine = "2 to 3 Weeks Delivery",
                startingPrice = "Rs. 15,000"
            ),
            ServiceDetail(
                id = "webapps",
                title = "High-Performance Web Applications",
                shortDesc = "Bespoke database-integrated portals, inventory dashboard panels, and secure portals.",
                icon = Icons.Default.Dns,
                techStack = listOf("Ktor API", "React Router", "Postgres", "Material 3"),
                longDescription = "If you need a system to manage staff, clients, bookings, or internal logic, generic templates won't scale. We design robust modular architectures with clean database boundaries, real-time sync systems, custom dashboards, and user authentications. Your app will look sleek and scale up seamlessly.",
                timeLine = "3 to 5 Weeks Delivery",
                startingPrice = "Rs. 40,000"
            ),
            ServiceDetail(
                id = "seo",
                title = "SEO & Core Web Vitals Optimization",
                shortDesc = "Auditing, refactoring, and structuring to guarantee search engine positioning.",
                icon = Icons.Default.QueryStats,
                techStack = listOf("Google Lighthouse", "Schema Markup", "Sitemaps", "Compressions"),
                longDescription = "Having a gorgeous website means nothing if potential regional buyers cannot find it. We inject customized JSON structured Schema data directly into layout headers, allowing search crawl agencies to map your service boundaries instantly. We optimize Core Web Vitals to guarantee 100% scores, directly lifting search ranks.",
                timeLine = "1 Week Implementation",
                startingPrice = "Rs. 5,000"
            )
        )
    }

    var expandedServiceId by remember { mutableStateOf<String?>("ecommerce") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Applet Banner Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "High-Performance Services",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Select an architecture below to explore complete technical details.",
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        // Expandable card lists 
        items(services) { service ->
            val isExpanded = expandedServiceId == service.id

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .border(
                        width = 1.dp,
                        color = if (isExpanded) AccentSky else BorderSlate,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .background(DarkSurface)
                    .clickable {
                        expandedServiceId = if (isExpanded) null else service.id
                    }
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isExpanded) AccentSky else DarkSurfaceElevated),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = service.icon,
                            contentDescription = service.title,
                            tint = if (isExpanded) DarkBackground else PrimaryWhite,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = service.title,
                            color = PrimaryWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Starting From: ${service.startingPrice}",
                            color = AccentEmerald,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    IconButton(
                        onClick = {
                            expandedServiceId = if (isExpanded) null else service.id
                        }
                    ) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Toggle Details",
                            tint = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = service.shortDesc,
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                // Expandable Accordion Body
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        HorizontalDivider(color = BorderSlate.copy(alpha = 0.5f))
                        Spacer(modifier = Modifier.height(14.dp))

                        // Detailed breakdown text
                        Text(
                            text = service.longDescription,
                            color = TextPrimary,
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Features / Tech Tags
                        Text(
                            text = "CORE STACK INCLUDED:",
                            color = TextSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            service.techStack.forEach { tech ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(DarkSurfaceElevated)
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = tech,
                                        color = TextPrimary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Delivery Timeline parameters
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(DarkSurfaceElevated.copy(alpha = 0.5f))
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = "Timeline",
                                    tint = AccentSky,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Estimated Delivery",
                                    color = TextSecondary,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp
                                )
                            }
                            Text(
                                text = service.timeLine,
                                color = PrimaryWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Action button to start custom quote for this specific service
                        Button(
                            onClick = { onNavigateToQuote(service.title) },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryWhite),
                            modifier = Modifier
                                .testTag("accordion_quote_btn_${service.id}")
                                .fillMaxWidth()
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Get Custom Quote",
                                color = DarkBackground,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // Buffer spacer for safe scrolling
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

data class ServiceDetail(
    val id: String,
    val title: String,
    val shortDesc: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val techStack: List<String>,
    val longDescription: String,
    val timeLine: String,
    val startingPrice: String
)
