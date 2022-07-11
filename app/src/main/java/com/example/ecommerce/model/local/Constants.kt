package com.samplenote.clinicappusingsqlite.dao

import com.example.ecommerce.model.local.CartDao.Companion.COLUMN_CATEGORY_ID
import com.example.ecommerce.model.local.CartDao.Companion.COLUMN_COUNT
import com.example.ecommerce.model.local.CartDao.Companion.COLUMN_DESCRIPTION
import com.example.ecommerce.model.local.CartDao.Companion.COLUMN_ID
import com.example.ecommerce.model.local.CartDao.Companion.COLUMN_PRICE
import com.example.ecommerce.model.local.CartDao.Companion.COLUMN_PRODUCT_ID
import com.example.ecommerce.model.local.CartDao.Companion.COLUMN_PRODUCT_IMAGE_URL
import com.example.ecommerce.model.local.CartDao.Companion.COLUMN_PRODUCT_NAME
import com.example.ecommerce.model.local.CartDao.Companion.COLUMN_SUB_CATEGORY_ID
import com.example.ecommerce.model.local.CartDao.Companion.TABLE_NAME

object Constants {
    const val DB_NAME = "ECommerce DB"
    const val DB_VERSION = 1

    val CREATE_CART_TABLE = """CREATE TABLE $TABLE_NAME (
        $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_PRODUCT_NAME TEXT,
        $COLUMN_PRODUCT_ID TEXT,
        $COLUMN_DESCRIPTION TEXT,
        $COLUMN_PRICE REAL,
        $COLUMN_CATEGORY_ID TEXT,
        $COLUMN_SUB_CATEGORY_ID TEXT,
        $COLUMN_PRODUCT_IMAGE_URL TEXT,
        $COLUMN_COUNT INTEGER
        )""".trimIndent()

}