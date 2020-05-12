package ru.komar.disk_api

import androidx.fragment.app.Fragment
import ru.komar.feature.Feature

interface DiskFragmentProvider : Feature {
    fun diskFragment(): Fragment
}