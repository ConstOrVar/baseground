package ru.komar.disk.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.komar.disk.DiskScope
import ru.komar.disk.rootComponent

internal class DiskFragment : Fragment(), DiskScope {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rootComponent(requireContext())
            .contactListActivityProvider
            ?.contactListActivityIntent()
    }

}