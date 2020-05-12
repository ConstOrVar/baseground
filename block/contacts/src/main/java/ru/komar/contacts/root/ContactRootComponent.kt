package ru.komar.contacts.root

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.komar.disk_api.DiskFragmentProvider
import javax.inject.Singleton

@Singleton
@Component
internal interface ContactRootComponent {

    val diskFragmentProvider: DiskFragmentProvider

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context,
            @BindsInstance diskFragmentProvider: DiskFragmentProvider
        ): ContactRootComponent

    }
}