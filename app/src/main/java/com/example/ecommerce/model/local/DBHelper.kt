package com.samplenote.clinicappusingsqlite.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.samplenote.clinicappusingsqlite.dao.Constants.CREATE_CART_TABLE
import com.samplenote.clinicappusingsqlite.dao.Constants.DB_NAME
import com.samplenote.clinicappusingsqlite.dao.Constants.DB_VERSION

class DBHelper(private val context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_CART_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}