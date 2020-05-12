package ru.komar.contacts

import android.app.Application
import android.content.Context
import ru.komar.contacts.root.ContactRootComponent
import ru.komar.contacts.root.DaggerContactRootComponent
import ru.komar.contacts_api.ContactDataProvider
import ru.komar.contacts_api.ContactListActivityProvider
import ru.komar.contacts_api.ContactListFragmentProvider
import ru.komar.disk_api.DiskFragmentProvider
import ru.komar.featureowner.FeatureHost
import ru.komar.featureowner.PluginSpec
import ru.komar.featureowner.PluginStorage

fun <T> T.contactsFeature(): PluginSpec
        where T: Application, T: PluginStorage {
    return ContactsPlugin(this)
}

fun PluginSpec.isContactFeature(): Boolean {
    return this::class.java == ContactsPlugin::class.java
}

internal class ContactsPlugin(
    private val appContext: Application
): PluginSpec {

    internal val rootComponent: ContactRootComponent by lazy {
        DaggerContactRootComponent
            .factory()
            .create(
                appContext,
                diskFragmentProvider
            )
    }

    private lateinit var diskFragmentProvider: DiskFragmentProvider

    override val providedFeatures by lazy {
        setOf(
            ContactListActivityProvider::class.java bind DefaultContactListActivityProvider(appContext),
            ContactListFragmentProvider::class.java bind DefaultContactListFragmentProvider(),
            ContactDataProvider::class.java bind DefaultContactDataProvider(this)
        )
    }

    override val dependencySpec: PluginSpec.DependencySpec = PluginSpec.DependencySpec(
        required = setOf(DiskFragmentProvider::class.java)
    )

    override val installer: PluginSpec.Installer = object : PluginSpec.Installer {

        override fun prepare(host: FeatureHost) {
            diskFragmentProvider = host.feature(this@ContactsPlugin, DiskFragmentProvider::class.java)!!
        }

    }

    override val installationTester: PluginSpec.InstallationTester = PluginSpec.InstallationTester {
        rootComponent
    }

}

internal interface ContactScope

@Suppress("unused")
internal fun ContactScope.rootComponent(context: Context): ContactRootComponent {
    return requireNotNull((context.applicationContext as PluginStorage).spec(ContactsPlugin::class.java)).rootComponent
}
