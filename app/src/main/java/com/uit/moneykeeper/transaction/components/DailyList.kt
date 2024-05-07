package com.uit.moneykeeper.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import com.uit.moneykeeper.transaction.viewmodel.CategoryDropdownViewModel
import com.uit.moneykeeper.transaction.viewmodel.DailyItemViewModel
import com.uit.moneykeeper.transaction.viewmodel.DailyListViewModel
import com.uit.moneykeeper.transaction.viewmodel.WalletListDropdownViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DailyList(viewModel: DailyListViewModel) {
    val dailyItemList by viewModel.dailyItemList.collectAsState()
    val sumIn by viewModel.sumIn.collectAsState()
    val sumOut by viewModel.sumOut.collectAsState()
    val sum by viewModel.sum.collectAsState()

    Column (
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )  {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.AccountBalanceWallet,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )
            WalletListDropdown(
                viewModel = WalletListDropdownViewModel(),
                onSelected = {
                    viewModel.updateWallet(it)
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Filled.CreditCard,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )
            CategoryDropdown(
                viewModel = CategoryDropdownViewModel(),
                onSelected = {
                    viewModel.updateCategory(it)
                },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (sumIn != 0.0) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Thu",
                        fontWeight = Bold
                    )

                    Text(
                        text = "+${DoubleToStringConverter.convert(sumIn)}",
                        color = Color.Green
                    )
                }
            }

            if (sumOut != 0.0) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Chi",
                        fontWeight = Bold
                    )

                    Text(
                        text = "-${DoubleToStringConverter.convert(sumOut)}",
                        color = Color.Red
                    )
                }
            }

            if (sumIn != 0.0 && sumOut != 0.0) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tổng",
                        fontWeight = Bold
                    )

                    Text(
                        text = "${if (sum < 0) "" else "+"}${DoubleToStringConverter.convert(sum)}",
                        color = Color.Black
                    )
                }
            }
        }

        dailyItemList.forEach { dailyItem ->
            val dailyItemViewModel = DailyItemViewModel(dailyItem.first, dailyItem.second)
            DailyItem(viewModel = dailyItemViewModel)
        }
    }
}