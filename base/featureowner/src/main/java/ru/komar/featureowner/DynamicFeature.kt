package ru.komar.featureowner

import ru.komar.feature.Feature

interface DynamicFeature<F: Feature> {
    fun lookup(): F?
}

class ProxyFeature<F: Feature>(
    private val owner: PluginSpec,
    private val host: FeatureHost,
    private val klass: Class<out F>
) : DynamicFeature<F> {

    override fun lookup(): F? {
        return host.feature(owner, klass)
    }

}