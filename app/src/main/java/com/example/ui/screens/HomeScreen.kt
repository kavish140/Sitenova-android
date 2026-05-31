package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import android.widget.Toast
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    onNavigateToQuote: () -> Unit,
    onNavigateToServices: () -> Unit,
    onShowLinkError: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    var activeFilterTab by remember { mutableStateOf("All") }

    val spotlightBrush = remember {
        Brush.radialGradient(
            colors = listOf(AccentSky.copy(alpha = 0.15f), Color.Transparent)
        )
    }

    val brandTextBrush = remember {
        Brush.linearGradient(
            colors = listOf(AccentSky, AccentFuchsia, AccentPink)
        )
    }

    val cardGradBrush = remember {
        Brush.linearGradient(
            colors = listOf(Color(0xFF161616), Color(0xFF0B0B0B))
        )
    }

    // Case Studies List matching the React source 
    val caseStudies = remember {
        listOf(
            CaseStudy("drdiptiganatra.com", "Medical", "Medical portfolio & consulting hub optimized for local searches.", "https://drdiptiganatra.com"),
            CaseStudy("jupiterfastfinance.com", "Finance", "Corporate financial service discovery framework with ultra-low latencies.", "https://jupiterfastfinance.com"),
            CaseStudy("aismartkit.tech", "Tech", "AI micro-tool aggregator built with modern React components.", "https://aismartkit.tech"),
            CaseStudy("design.sitenova.dev", "Design", "Modern interface design showcase with fluid animations, bold typography, and visual aesthetics.", "https://design.sitenova.dev/"),
            CaseStudy("ecommerce.sitenova.dev", "Ecommerce", "High-performance digital storefront optimized for conversions, fast checkout flows, and product discovery.", "https://ecommerce.sitenova.dev/"),
            CaseStudy("buisness-showcase.sitenova.dev", "Business", "Comprehensive corporate presentation highlighting essential services, client portfolios, and enterprise capabilities.", "https://buisness-showcase.sitenova.dev/")
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // 1. Hero Spotlight & Sparkle Badge
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                // Spotlighting gradient radial decoration layered behind content
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .align(Alignment.TopCenter)
                        .background(brush = spotlightBrush)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Sparkle badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .background(DarkSurfaceElevated)
                            .border(1.dp, BorderSlate, RoundedCornerShape(100.dp))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Sparkles",
                            tint = Color(0xFFFBBF24), // Gold 400
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "HIGH-PERFORMANCE LOCAL WEB DEV",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextPrimary,
                            fontSize = 10.sp,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Banner title text with BOLD TYPOGRAPHY
                    Text(
                        text = "BUILD",
                        style = MaterialTheme.typography.displayLarge,
                        color = PrimaryWhite
                    )
                    Text(
                        text = "BEYOND",
                        style = MaterialTheme.typography.displayLarge.copy(
                            brush = brandTextBrush
                        )
                    )
                    Text(
                        text = "LIMITS",
                        style = MaterialTheme.typography.displayLarge,
                        color = PrimaryWhite
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Modular design systems and high-velocity automation for the next generation of digital agencies.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary,
                        lineHeight = 22.sp,
                        modifier = Modifier.widthIn(max = 280.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // CTA Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Button(
                            onClick = onNavigateToQuote,
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryWhite),
                            modifier = Modifier
                                .testTag("hero_quote_btn")
                                .height(48.dp),
                            shape = RoundedCornerShape(100.dp)
                        ) {
                            Text(
                                text = "Start Your Project",
                                color = DarkBackground,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = DarkBackground,
                                modifier = Modifier.size(16.dp)
                              )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        OutlinedButton(
                            onClick = { activeFilterTab = "All" },
                            border = BorderStroke(1.dp, BorderSlate),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                            modifier = Modifier.height(48.dp),
                            shape = RoundedCornerShape(100.dp)
                        ) {
                            Text(text = "Case Studies", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 1.5 Metric Badges Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Success Rate Card
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, BorderSlate, RoundedCornerShape(16.dp))
                        .background(DarkSurface)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "SUCCESS RATE",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextSecondary,
                        fontSize = 10.sp,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "99.4%",
                        style = MaterialTheme.typography.displayMedium,
                        color = AccentSky
                    )
                }

                // Deployments Card
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, BorderSlate, RoundedCornerShape(16.dp))
                        .background(DarkSurface)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "DEPLOYMENTS",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextSecondary,
                        fontSize = 10.sp,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "1.2k+",
                        style = MaterialTheme.typography.displayMedium,
                        color = AccentPink
                    )
                }
            }
        }

        // 2. Core Web Vitals Status Card (Visual Asset mapping react container)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, BorderSlate, RoundedCornerShape(24.dp))
                    .background(DarkSurface)
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(100.dp)).background(Color.Red))
                        Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(100.dp)).background(Color.Yellow))
                        Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(100.dp)).background(Color.Green))
                    }
                    Text(
                        text = "sitenova.dev/speed-test",
                        color = TextMuted,
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(DarkSurfaceElevated)
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Core Web Vitals Speed",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "100%",
                            color = AccentEmerald,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { 1.0f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(100.dp)),
                        color = AccentEmerald,
                        trackColor = BorderSlate,
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, BorderSlate, RoundedCornerShape(12.dp))
                            .background(DarkSurfaceElevated)
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "SEO Score", color = TextSecondary, fontSize = 11.sp)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "A+ Ready", color = PrimaryWhite, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, BorderSlate, RoundedCornerShape(12.dp))
                            .background(DarkSurfaceElevated)
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Mobile Fluidity", color = TextSecondary, fontSize = 11.sp)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "Adaptive", color = PrimaryWhite, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        // 3. Features & Discovery Section (FeaturesSection.tsx equivalent)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "Engineered for Performance and Discovery",
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.SpacerHeight())
                Text(
                    text = "We eliminate unneeded bloat. Every line of code serves a purpose: making your site faster, cleaner, and simpler to locate.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                val features = remember {
                    listOf(
                        FeatureItem(Icons.Default.PhoneIphone, "Mobile-First Infrastructure", "Pixel perfect on standard smartphones, tablets, and luxury displays."),
                        FeatureItem(Icons.Default.Search, "Local Schema SEO Injection", "Structured semantic data helping engines map your physical footprint instantly."),
                        FeatureItem(Icons.Default.Code, "Clean Modern Architecture", "Built using modular components for easy iterative changes and scaling."),
                        FeatureItem(Icons.Default.Layers, "Tailwind Styling System", "Blazing fast UI lookups using modern atomic CSS tokens.")
                    )
                }

                features.forEach { feat ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, BorderSlate, RoundedCornerShape(16.dp))
                            .background(DarkSurface)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(PrimaryWhite),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = feat.icon,
                                contentDescription = null,
                                tint = DarkBackground,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = feat.title,
                                color = PrimaryWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = feat.desc,
                                color = TextSecondary,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }

        // 4. Regional SEO Optimization Hub
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, BorderSlate, RoundedCornerShape(24.dp))
                    .background(DarkSurface)
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = AccentSky,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "LOCAL OPTIMIZATION HUB",
                        style = MaterialTheme.typography.bodySmall,
                        color = AccentSky,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Targeting Regional Intent Frameworks",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryWhite,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "SiteNova targets location-aware client intent. We structure components to seamlessly serve searches originating across core central economic nodes and neighborhood hubs.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                val neighborhoods = listOf(
                    "Mulund", "Mumbai", "Bhandup", "Nahur", "Thane", "Ghatkopar", "Powai", "Central Mumbai"
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    neighborhoods.forEach { place ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .border(1.dp, BorderSlate, RoundedCornerShape(10.dp))
                                .background(DarkSurfaceElevated)
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = place,
                                color = TextPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // 5. High-Impact Transparent Pricing (Websites starting from Rs 5,000)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(DarkSurfaceElevated, DarkBackground)
                        )
                    )
                    .border(1.dp, BorderSlate.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                    .padding(24.dp)
            ) {
                Text(
                    text = "TRANSPARENT TIER CONFIGURATIONS",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Websites Starting From Rs. 5,000",
                    style = MaterialTheme.typography.headlineSmall,
                    color = PrimaryWhite,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Launch a high-impact digital showcase without unexpected overspending overhead. We provide structured deliverables explicitly tailored to project functionality and feature sets.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                val metrics = listOf(
                    "Starter Landing Deliverables",
                    "Professional Business Frameworks",
                    "Custom Tailored Architectures"
                )

                metrics.forEach { detail ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Included",
                            tint = AccentEmerald,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = detail,
                            color = TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onNavigateToQuote,
                    colors = ButtonDefaults.buttonColors(containerColor = AccentSky),
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Calculate Custom Pricing", color = DarkBackground, fontWeight = FontWeight.Bold)
                }
            }
        }

        // 6. Case Studies Filterable Showcase
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "Proven Case Studies",
                            style = MaterialTheme.typography.titleLarge,
                            color = PrimaryWhite,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Click to browse live deployments",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Filter Buttons Row
                val filterOptions = listOf("All", "Medical", "Finance", "Tech")
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filterOptions) { tab ->
                        val isSelected = activeFilterTab == tab
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) PrimaryWhite else DarkSurfaceElevated)
                                .border(1.dp, if (isSelected) AccentSky else BorderSlate, RoundedCornerShape(10.dp))
                                .clickable { activeFilterTab = tab }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = tab,
                                color = if (isSelected) DarkBackground else TextSecondary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Show projects corresponding to selection
                val filteredList = if (activeFilterTab == "All") {
                    caseStudies
                } else {
                    caseStudies.filter { it.category.equals(activeFilterTab, ignoreCase = true) }
                }

                filteredList.forEach { study ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .border(1.dp, Color(0xFF222222), RoundedCornerShape(24.dp))
                            .background(brush = cardGradBrush)
                            .clickable {
                                try {
                                    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(study.url))
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    onShowLinkError("Website link cannot be opened in this demo environment: ${study.url}")
                                }
                            }
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(AccentSky.copy(alpha = 0.2f))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = study.category.uppercase(),
                                        color = AccentSky,
                                        style = MaterialTheme.typography.labelLarge,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = study.domain,
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 24.sp
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(100.dp))
                                    .border(1.dp, Color(0xFF333333), RoundedCornerShape(100.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Link Out",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = study.desc,
                            color = TextSecondary,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Stacked active nodes
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy((-8).dp)
                            ) {
                                val nodeColors = listOf(Color(0xFF3F3F46), Color(0xFF27272A), Color(0xFF18181B))
                                nodeColors.forEach { c ->
                                    Box(
                                        modifier = Modifier
                                            .size(26.dp)
                                            .clip(RoundedCornerShape(100.dp))
                                            .border(2.dp, Color.Black, RoundedCornerShape(100.dp))
                                            .background(c)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "+42 Active Nodes",
                                color = TextSecondary,
                                style = MaterialTheme.typography.labelLarge,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // 7. Interactive Roadmaps
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "The Project Roadmap",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryWhite,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "From initialization strategy to live release",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                val steps = listOf(
                    RoadmapStep("01", "Scope Blueprinting", "We identify layout structures, target regional keywords, and establish precise functional requirements."),
                    RoadmapStep("02", "Adaptive Construction", "Rapid build utilising highly performant Compose architectures and optimized themes."),
                    RoadmapStep("03", "SEO & Speed Deploy", "Performance metrics auditing, asset compression, and formal JSON schematic injection.")
                )

                steps.forEach { step ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(DarkSurfaceElevated.copy(alpha = 0.4f))
                            .border(1.dp, BorderSlate.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = step.number,
                            color = BorderSlate,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.width(18.dp))
                        Column {
                            Text(
                                text = step.name,
                                color = PrimaryWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = step.detail,
                                color = TextSecondary,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }

        // 8. Testimonials Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "Valued Regional Feedback",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryWhite,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))

                val feedback = listOf(
                    FeedbackItem("Dr. Dipti Ganatra", "Founder, drdiptiganatra.com", "SiteNova reconstructed my portfolio from scratch. The page speed increased by 300% and clients schedule calls immediately with zero interruptions! Excellent local SEO."),
                    FeedbackItem("Finance Operations Admin", "JupiterFastFinance", "Our financial landing operates at peak efficiency. Mulund and Thane searches bring in consistent prospects daily since release.")
                )

                feedback.forEach { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(DarkSurface)
                            .border(1.dp, BorderSlate, RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatQuote,
                            contentDescription = null,
                            tint = AccentSky,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = item.comment,
                            color = TextPrimary,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = item.name,
                            color = PrimaryWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = item.role,
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // Spacer to push content so bottom nav is comfortable
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// Data Classes for Screen state holding
data class CaseStudy(val domain: String, val category: String, val desc: String, val url: String)
data class FeatureItem(val icon: androidx.compose.ui.graphics.vector.ImageVector, val title: String, val desc: String)
data class RoadmapStep(val number: String, val name: String, val detail: String)
data class FeedbackItem(val name: String, val role: String, val comment: String)

// Extension for simple padding spacing
fun Modifier.SpacerHeight(): Modifier = this.height(6.dp)
