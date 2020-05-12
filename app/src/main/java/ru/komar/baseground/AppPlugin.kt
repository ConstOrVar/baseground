package ru.komar.baseground

import android.app.Application
import ru.komar.contacts_api.ContactListFragmentProvider
import ru.komar.disk_api.DiskFragmentProvider
import ru.komar.feature.Feature
import ru.komar.featureowner.FeatureHost
import ru.komar.featureowner.PluginSpec
import ru.komar.featureowner.PluginStorage

internal class AppPlugin<T>(
    private val context: T
) : PluginSpec where T: Application, T: PluginStorage {

    private lateinit var contactListFragmentProvider: ContactListFragmentProvider

    private lateinit var diskFragmentProvider: DiskFragmentProvider

    override val providedFeatures: Set<PluginSpec.FeatureRegistryRecord<out Feature>> = emptySet()

    override val dependencySpec: PluginSpec.DependencySpec = PluginSpec.DependencySpec(
        required = setOf(
            ContactListFragmentProvider::class.java,
            DiskFragmentProvider::class.java
        )
    )

    override val installer: PluginSpec.Installer = object :  PluginSpec.Installer {

        override fun prepare(host: FeatureHost) {
            contactListFragmentProvider = host.feature(this@AppPlugin, ContactListFragmentProvider::class.java)!!
            diskFragmentProvider = host.feature(this@AppPlugin, DiskFragmentProvider::class.java)!!
        }

    }

}