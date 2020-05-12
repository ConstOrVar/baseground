package ru.komar.baseground

import android.app.Application
import ru.komar.contacts.contactsFeature
import ru.komar.contacts_api.ContactDataProvider
import ru.komar.contacts_api.ContactListActivityProvider
import ru.komar.contacts_api.ContactListFragmentProvider
import ru.komar.contacts_api.model.Contact
import ru.komar.disk.diskFeature
import ru.komar.disk.isDiskFeature
import ru.komar.feature.Feature
import ru.komar.featureowner.PluginManager
import ru.komar.featureowner.FeatureHost
import ru.komar.featureowner.PluginSpec
import java.util.*

class MyApp : Application(),
    PluginManager by PluginManager.default() {

    override fun onCreate() {
        super.onCreate()

        init()
    }

    private fun init() {
        addPlugin(diskFeature())
        addPlugin(contactsFeature()) { it.klass != ContactDataProvider::class.java }
        addPlugin(AppPlugin(this))

        prepare(this)

        setup()
    }

    private class FeatureInterceptor(private val parentFeatureHost: FeatureHost) : FeatureHost {

        @Suppress("UNCHECKED_CAST")
        override fun <F : Feature> feature(caller: PluginSpec, kclass: Class<out F>): F? {
            return when {
                caller.isDiskFeature() && kclass == ContactDataProvider::class.java -> {
                    object : ContactDataProvider {
                        override fun loadContactByUuid(uuid: UUID): Contact? {
                            TODO("Intercepted implementation")
                        }

                    }
                }
                else -> parentFeatureHost.feature(caller, kclass)
            } as? F
        }

    }

}