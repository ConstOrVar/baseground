package ru.komar.featureowner

import ru.komar.feature.Feature

interface FeatureHost {
    fun <F: Feature> feature(caller: PluginSpec, kclass: Class<out F>): F?
}