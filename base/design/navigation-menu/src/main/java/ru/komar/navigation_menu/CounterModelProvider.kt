package ru.komar.navigation_menu

interface CounterModelProvider<out M: CounterModel> {
    fun loadCounters(): M
}