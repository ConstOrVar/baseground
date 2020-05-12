package ru.komar.disk

import androidx.fragment.app.Fragment
import ru.komar.disk.presentation.DiskFragment
import ru.komar.disk_api.DiskFragmentProvider

internal class DefaultDiskFragmentProvider : DiskFragmentProvider {

    override fun diskFragment(): Fragment {
        return DiskFragment()
    }

}