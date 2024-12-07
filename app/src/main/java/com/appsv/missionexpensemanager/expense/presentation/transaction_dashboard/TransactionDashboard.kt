package com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard


import android.R.id.message
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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


@Preview
@Composable
fun TransactionDashboardScreen(
    transactionState: TransactionState = TransactionState(),
    onTransactionCardClick: (TransactionEntity) -> Unit = {},
    onFabClick: () -> Unit = {},
    events: (TransactionCreationEvents) -> Unit = {},
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


    var selectedIndex by remember {
        mutableIntStateOf(0)
    }


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
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            when (val icon = navItem.icon) {
                                is NavIcon.Vector -> {
                                    Icon(
                                        modifier = Modifier.size(35.dp),
                                        imageVector = icon.imageVector,
                                        contentDescription = navItem.label,
                                        tint = iconAndTextColor,
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
        },
        floatingActionButtonPosition = FabPosition.Center,
    )
    { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            SearchBar(
                text = transactionState.searchingText,
                onExecuteSearch = {
                    events(TransactionCreationEvents.StartSearchingTransactions)
                },
                onSearchingTransactions = {
                    events(TransactionCreationEvents.SearchTransactions(it))
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = "Recent Transactions",
                color = GrayishPurple,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

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
                            selectedChip = label
                            events(TransactionCreationEvents.FilterTransactions(selectedChip))
                        }
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
//                if(!isConnected){
//                    Box(
//                        modifier = Modifier.fillMaxSize()
//                            .padding(bottom = 30.dp)
//                    , contentAlignment = Alignment.Center){
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.Center,
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            val icon: Painter = painterResource(id = R.drawable.baseline_wifi_off_24) // Replace with your actual icon
//                            Icon(painter = icon, contentDescription = "No Internet", modifier = Modifier.size(80.dp), tint = Color.Red)
//                            Spacer(modifier = Modifier.height(16.dp))
//                            Text(
//                                text = "No Internet!",
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 20.sp,
//                                color = Color.Red
//                            )
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Text(
//                                modifier = Modifier.padding(horizontal = 15.dp),
//                                text = "To add a transaction, please turn on the internet.",
//                                fontSize = 18.sp,
//                                textAlign = TextAlign.Center
//                            )
//                        }
//                    }
//
//                }
                if (transactionState.isLoading) {
                    CircularProgressIndicator()
                }
                else if (transactionState.modifiedFilteredTransactionList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        itemsIndexed(
                            items = transactionState.modifiedFilteredTransactionList,
                            key = { index, transaction -> transaction.id }
                        ) { index, transaction ->

                            TransactionCard(transaction) {
                                onTransactionCardClick(transaction)
                            }

                        }
                    }
                }
                else if (transactionState.error.isNotEmpty()) {
                    Text(
                        text = transactionState.error,
                        fontSize = 16.sp,
                        color = ColorPrimary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                else {
                    Text(
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

    if (showNoInternetDialog) {
        NoInternetDialog(
            message = "To add a transaction, please turn on the internet."
        ) {
            showNoInternetDialog = false
        }
    }

}









