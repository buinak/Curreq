package com.buinak.curreq.entities.CurreqEntity

import android.graphics.Bitmap

/**
 * Created by Foreseer on 11-Oct-18.
 */

class Currency {

    var bitmap: Bitmap? = null

    val code: String
    val name: String

    constructor(code: String, name: String) {
        this.code = code
        this.name = name
    }

    constructor(currency: Currency, bitmap: Bitmap) {
        this.code = currency.code
        this.name = currency.name

        this.bitmap = bitmap
    }
}
