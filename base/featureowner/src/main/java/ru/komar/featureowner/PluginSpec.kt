package ru.komar.featureowner

import ru.komar.feature.Feature

interface PluginSpec {
    val providedFeatures: Set<FeatureRegistryRecord<out Feature>>

    val dependencySpec: DependencySpec

    val installer: Installer

    val installationTester: InstallationTester?
        get() = null


    data class DependencySpec(
        val required: Set<Class<out Feature>> = emptySet(),
        val optional: Set<Class<out Feature>> = emptySet()
    )

    data class FeatureRegistryRecord<T: Feature> internal constructor(
        val klass: Class<T>,
        val target: T
    )

    infix fun <T: Feature> Class<T>.bind(feature: T): FeatureRegistryRecord<T> {
        return FeatureRegistryRecord(this, feature)
    }

    interface Installer {
        fun prepare(host: FeatureHost)

        fun setup() = Unit
    }

    class InstallationTester(private val block: () -> Unit) {
        @Throws(Exception::class)
        internal fun test() {
            block()
        }
    }
}