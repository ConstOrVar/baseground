package ru.komar.featureowner

import ru.komar.feature.Feature

interface PluginManager : PluginStorage, PluginSpec.Installer, FeatureHost {
    fun addPlugin(spec: PluginSpec)
    fun addPlugin(spec: PluginSpec, featureMatcher: (PluginSpec.FeatureRegistryRecord<out Feature>) -> Boolean)

    fun plugins(): Set<PluginSpec>

    companion object {
        fun default(): PluginManager = DefaultPluginManager()
    }
}

@Throws(Exception::class)
fun PluginManager.testInstallation() {
    plugins()
        .mapNotNull { it.installationTester }
        .forEach {
            it.test()
        }
}

internal class DefaultPluginManager : PluginManager  {
    private val specs = mutableMapOf<Class<out PluginSpec>, PluginSpec>()
    private val features = mutableMapOf<Class<out Feature>, Feature>()

    override fun addPlugin(spec: PluginSpec) {
        registerPlugin(spec)
        registerFeatures(spec, spec.providedFeatures)
    }

    override fun addPlugin(
        spec: PluginSpec,
        featureMatcher: (PluginSpec.FeatureRegistryRecord<out Feature>) -> Boolean
    ) {
        registerPlugin(spec)
        registerFeatures(spec, spec.providedFeatures.filter(featureMatcher))
    }

    override fun plugins(): Set<PluginSpec> {
        return specs.values.toSet()
    }

    override fun prepare(host: FeatureHost) {
        specs.values.forEach {
            it.installer.prepare(host)
        }
    }

    override fun setup() {
        specs.values.forEach {
            it.installer.setup()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : PluginSpec> spec(klass: Class<out T>): T? {
        return specs[klass] as? T
    }

    @Suppress("UNCHECKED_CAST")
    override fun <F : Feature> feature(caller: PluginSpec, kclass: Class<out F>): F? {
        val isRequired = caller.dependencySpec.required.contains(kclass)
        val isOptional = caller.dependencySpec.optional.contains(kclass)
        if(!isRequired and !isOptional) {
            throw IllegalStateException(
                """
                    ${caller::class.java.canonicalName} spec error: UNDECLARED DEPENDENCY ACCESS.
                    $kclass is nod found among dependencies.
                """.trimIndent()
            )
        }

        val implementation = features[kclass] as? F

        if(implementation == null && isRequired) {
            throw IllegalStateException(
                """
                    ${caller::class.java.canonicalName} spec error: MISSING DEPENDENCY IMPLEMENTATION.
                    Implementation of $kclass is nod provided by any plugin.
                """.trimIndent()
            )
        }

        return implementation
    }

    private fun registerPlugin(spec: PluginSpec) {
        specs += spec.javaClass to spec
    }

    private fun registerFeatures(spec: PluginSpec, featureRegistryItems: Collection<PluginSpec.FeatureRegistryRecord<out Feature>>) {
        featureRegistryItems.forEach {
            if(features.containsKey(it.klass)) {
                throw IllegalStateException(
                    """
                        ${spec::class.java.canonicalName} spec error: DUPLICATE IMPLEMENTATION.
                        ${features[it.klass]!!::class.java.canonicalName} is already registered as implementation for ${it.klass}.
                    """.trimIndent()
                )
            }
            features[it.klass] = it.target
        }
    }

}