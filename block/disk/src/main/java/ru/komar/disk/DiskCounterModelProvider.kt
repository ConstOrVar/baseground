package ru.komar.disk

import ru.komar.feature.Feature
import ru.komar.navigation_menu.CounterModel
import ru.komar.navigation_menu.CounterModelProvider

data class DiskCounterModel(
    override val totalCounter: Int,
    override val unreadCounter: Int
) : CounterModel

class DiskCounterModelProvider : CounterModelProvider<DiskCounterModel>, Feature {

    override fun loadCounters(): DiskCounterModel {
        TODO("Not yet implemented")
    }

}