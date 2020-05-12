package ru.komar.disk.root

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.komar.contacts_api.ContactDataProvider
import ru.komar.contacts_api.ContactListActivityProvider
import ru.komar.disk.DiskConfiguration
import ru.komar.featureowner.DynamicFeature
import javax.inject.Singleton

@Singleton
@Component
internal interface DiskRootComponent {

    val contactDataProvider: DynamicFeature<ContactDataProvider>

    val contactListActivityProvider: ContactListActivityProvider?

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context,
            @BindsInstance config: DiskConfiguration,
            @BindsInstance contactDataProvider: DynamicFeature<ContactDataProvider>,
            @BindsInstance contactListActivityProvider: ContactListActivityProvider?
        ): DiskRootComponent

    }

}