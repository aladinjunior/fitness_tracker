package co.aladinjunior.fitnesstracker

import android.app.Application
import co.aladinjunior.fitnesstracker.model.AppDataBase

class App : Application() {

    lateinit var db: AppDataBase

    override fun onCreate() {
        super.onCreate()
        db = AppDataBase.getAppDataBase(this)
    }

}