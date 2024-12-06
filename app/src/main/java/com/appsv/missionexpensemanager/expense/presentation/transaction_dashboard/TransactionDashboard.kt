package com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.appsv.missionexpensemanager.core.presentation.ui.theme.ColorPrimary
import com.appsv.missionexpensemanager.core.presentation.ui.theme.ColorSecondary
import com.appsv.missionexpensemanager.core.presentation.ui.theme.ColorSecondaryVariant
import com.appsv.missionexpensemanager.core.presentation.ui.theme.GrayishBlue
import com.appsv.missionexpensemanager.core.presentation.ui.theme.GrayishPurple
import com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.components.NavIcon
import com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.components.NavItem
import com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.components.SearchBar
import com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.components.TransactionCard
import com.appsv.missionexpensemanager.expense.utils.transactionsDummyList


@Preview
@Composable
fun TransactionDashboardScreen(
    transactionState : TransactionState = TransactionState(),
    onFabClick : () -> Unit = {}
) {

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
                    val iconAndTextColor = if(selectedIndex == index) ColorSecondary else GrayishBlue

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
                    onFabClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, top = 0.dp, start = 100.dp, end = 100.dp),
                containerColor = ColorPrimary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Icon",
                    tint =  Color.White
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
            SearchBar()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = "Recent Transactions",
                color = GrayishPurple,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                if(transactionState.isLoading){
                    CircularProgressIndicator()
                }
                else if(transactionState.transactionList.isNotEmpty()){
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        items(transactionState.transactionList){
                            TransactionCard(it)
                        }
                    }
                }
                else if(transactionState.error.isNotEmpty()){
                    Text(
                        text = transactionState.error,
                        fontSize = 16.sp,
                        color = ColorPrimary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                else{
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

}









