package com.uit.moneykeeper.transaction.viewmodel

import androidx.lifecycle.ViewModel
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.sample.giaoDichList
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import java.util.Calendar

class TransactionViewModel : ViewModel() {
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    private val _selectedYear = MutableStateFlow(Calendar.getInstance().get(Calendar.YEAR))
    val selectedYear: StateFlow<Int> = _selectedYear.asStateFlow()

    fun changeTab(index: Int) {
        _selectedTabIndex.value = index
    }

    fun changeYear(year: Int) {
        _selectedYear.value = year
    }

    fun nextYear() {
        changeYear(selectedYear.value + 1)
    }

    fun previousYear() {
        changeYear(selectedYear.value - 1)
    }
}
