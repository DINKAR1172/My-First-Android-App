package com.example.datashop.model

import android.app.Application

class ShoppingListApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}