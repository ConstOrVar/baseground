package ru.komar.featureowner

interface PluginStorage {
    fun <T: PluginSpec> spec(klass: Class<out T>): T?
}