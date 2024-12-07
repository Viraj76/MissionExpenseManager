package com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appsv.missionexpensemanager.R
import com.appsv.missionexpensemanager.core.component.NoInternetDialog
import com.appsv.missionexpensemanager.core.presentation.ui.theme.ColorPrimary
import com.appsv.missionexpensemanager.core.presentation.ui.theme.ColorSecondary
import com.appsv.missionexpensemanager.core.presentation.ui.theme.ColorSecondaryVariant
import com.appsv.missionexpensemanager.core.presentation.ui.theme.GrayishBlue
import com.appsv.missionexpensemanager.core.presentation.ui.theme.GrayishPurple
import com.appsv.missionexpensemanager.core.util.NetworkConnectionState
import com.appsv.missionexpensemanager.core.util.rememberConnectivityState
import com.appsv.missionexpensemanager.expense.data.local.room.TransactionEntity
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.TransactionCreationEvents
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.components.CustomFilterChip
import com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.components.NavIcon
import com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.components.NavItem
import com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.components.SearchBar
import com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.components.TransactionCard
import java.nio.file.WatchEvent


@Preview
@Composable
fun MainScreen(
    transactionState: TransactionState = TransactionState(),
    onTransactionCardClick: (TransactionEntity) -> Unit = {},
    onFabClick: () -> Unit = {},
    events: (TransactionCreationEvents) -> Unit = {}
) {
    var showNoInternetDialog by rememberSaveable { mutableStateOf(false) }
    val connectionState by rememberConnectivityState()

    val isConnected by remember(connectionState) {
        derivedStateOf {
            connectionState === NetworkConnectionState.Available
        }
    }

    var selectedChip by rememberSaveable { mutableStateOf("All") }
    val chipOptions = listOf("All", "Expense", "Income")
    val chipColors = listOf(Color.Blue, Color.Red, Color.Green)

    val navItemList = listOf(
        NavItem("Dashboard", icon = NavIcon.Vector(Icons.Default.Home)),
        NavItem("Settings", icon = NavIcon.Resource(R.drawable.baseline_more_horiz_24))
    )

    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ColorSecondaryVariant,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                Spacer(modifier = Modifier.weight(1f))

                navItemList.forEachIndexed { index, navItem ->
                    val iconAndTextColor =
                        if (selectedIndex == index) ColorSecondary else GrayishBlue

                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors().copy(
                            selectedIndicatorColor = Color.Transparent
                        ),
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = {
                            when (val icon = navItem.icon) {
                                is NavIcon.Vector -> {
                                    Icon(
                                        modifier = Modifier.size(35.dp),
                                        imageVector = icon.imageVector,
                                        contentDescription = navItem.label,
                                        tint = iconAndTextColor
                                    )
                                }

                                is NavIcon.Resource -> {
                                    Icon(
                                        modifier = Modifier.size(35.dp),
                                        painter = painterResource(id = icon.resId),
                                        contentDescription = navItem.label,
                                        tint = iconAndTextColor
                                    )
                                }
                            }
                        },
                        label = {
                            Text(
                                text = navItem.label,
                                color = iconAndTextColor
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        },
        floatingActionButton = {
            if (selectedIndex == 0) {
                ExtendedFloatingActionButton(
                    onClick = {
                        if (isConnected) onFabClick() else showNoInternetDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp, top = 0.dp, start = 100.dp, end = 100.dp),
                    containerColor = ColorPrimary,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Icon",
                        tint = Color.White
                    )
                    Text(
                        text = "Add New",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        when (selectedIndex) {
            0 -> TransactionDashboardContent(
                transactionState = transactionState,
                onTransactionCardClick = onTransactionCardClick,
                events = events,
                innerPadding = innerPadding,
                selectedChip = selectedChip,
                chipOptions = chipOptions,
                chipColors = chipColors,
                onChipSelected = { selectedChip = it }
            )

            1 -> SettingsScreen(innerPadding = innerPadding)
        }
    }

    if (showNoInternetDialog) {
        NoInternetDialog(
            message = "To add a transaction, please turn on the internet."
        ) {
            showNoInternetDialog = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDashboardContent(
    transactionState: TransactionState,
    onTransactionCardClick: (TransactionEntity) -> Unit,
    events: (TransactionCreationEvents) -> Unit,
    innerPadding: PaddingValues,
    selectedChip: String,
    chipOptions: List<String>,
    chipColors: List<Color>,
    onChipSelected: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        item {
            SearchBar(
                text = transactionState.searchingText,
                onExecuteSearch = {
                    events(TransactionCreationEvents.StartSearchingTransactions)
                },
                onSearchingTransactions = {
                    events(TransactionCreationEvents.SearchTransactions(it))
                }
            )
        }

        item {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = "Recent Transactions",
                color = GrayishPurple,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                chipOptions.forEachIndexed { index, label ->
                    CustomFilterChip(
                        label = label,
                        color = chipColors[index],
                        selected = selectedChip == label,
                        onClick = {
                            onChipSelected(label)
                            events(TransactionCreationEvents.FilterTransactions(label))
                        }
                    )
                }
            }
        }

        if (transactionState.isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxSize().padding(bottom = 200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else if (transactionState.modifiedFilteredTransactionList.isNotEmpty()) {
            // Show transactions
            itemsIndexed(
                items = transactionState.modifiedFilteredTransactionList,
                key = { _, transaction -> transaction.id }
            ) { _, transaction ->
                TransactionCard(transaction) {
                    onTransactionCardClick(transaction)
                }
            }
        } else {
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxSize().padding(bottom = 150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (transactionState.error.isNotEmpty()) {
                        Text(
                            text = transactionState.error,
                            fontSize = 16.sp,
                            color = ColorPrimary,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            text = "No transaction! \n Click on Add New button to add transactions",
                            fontSize = 16.sp,
                            color = ColorPrimary,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

}


@Composable
fun SettingsScreen(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Settings Screen", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

