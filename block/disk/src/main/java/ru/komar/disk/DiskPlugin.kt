package ru.komar.disk

import android.app.Application
import android.content.Context
import ru.komar.contacts_api.ContactDataProvider
import ru.komar.contacts_api.ContactListActivityProvider
import ru.komar.disk.root.DaggerDiskRootComponent
import ru.komar.disk.root.DiskRootComponent
import ru.komar.disk_api.DiskFragmentProvider
import ru.komar.feature.Feature
import ru.komar.featureowner.*
import java.util.*

data class DiskConfiguration(
    val tabletModeEnabled: Boolean = true,
    val pushSubscriptionEnabled: Boolean = true
)

@JvmOverloads
fun <T> T.diskFeature(config: DiskConfiguration = DiskConfiguration()): PluginSpec
        where T: Application, T: PluginStorage {
    return DiskPlugin(this, config)
}

fun PluginSpec.isDiskFeature(): Boolean {
    return this::class.java == DiskPlugin::class.java
}

internal class DiskPlugin(
    private val appContext: Application,
    private val configuration: DiskConfiguration
) : PluginSpec {

    val rootComponent: DiskRootComponent by lazy {
        DaggerDiskRootComponent
            .factory()
            .create(
                appContext,
                configuration,
                contactDataProvider,
                contactListActivityProvider
            )
    }

    private lateinit var contactDataProvider: DynamicFeature<ContactDataProvider>

    private var contactListActivityProvider: ContactListActivityProvider? = null

    override val providedFeatures by lazy {
        setOf<PluginSpec.FeatureRegistryRecord<out Feature>>(
            DiskFragmentProvider::class.java bind DefaultDiskFragmentProvider()
        )
    }

    override val dependencySpec: PluginSpec.DependencySpec = PluginSpec.DependencySpec(
        required = setOf(
            ContactListActivityProvider::class.java
        ),
        optional = setOf(
            ContactDataProvider::class.java
        )
    )

    override val installer: PluginSpec.Installer = object :  PluginSpec.Installer {

        override fun prepare(host: FeatureHost) {
            contactDataProvider = ProxyFeature(this@DiskPlugin, host, ContactDataProvider::class.java)
            contactListActivityProvider = host.feature(this@DiskPlugin, ContactListActivityProvider::class.java)
        }

        override fun setup() {
            if(configuration.pushSubscriptionEnabled) {
                // TODO "subscribe for push events"
            }
        }

    }

    override val installationTester: PluginSpec.InstallationTester = PluginSpec.InstallationTester {
        rootComponent.contactDataProvider.lookup()?.loadContactByUuid(UUID.randomUUID())
    }

}

internal interface DiskScope

@Suppress("unused")
internal fun DiskScope.rootComponent(context: Context): DiskRootComponent {
    return requireNotNull((context.applicationContext as PluginStorage).spec(DiskPlugin::class.java)).rootComponent
}