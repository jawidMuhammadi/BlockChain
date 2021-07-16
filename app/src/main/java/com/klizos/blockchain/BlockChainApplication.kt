package com.klizos.blockchain

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Ahmad Jawid Muhammadi
 * on 15-07-2021.
 */

@HiltAndroidApp
class BlockChainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}