package com.buinak.curreq.entities.CurreqEntity

class CurrencyExchangeRate {

    var id: String? = null

    var currency: Currency? = null
    var baseCurrency: Currency? = null
    var rate: Double? = null

    constructor(currency: Currency, baseCurrency: Currency, rate: Double?) {
        this.currency = currency
        this.baseCurrency = baseCurrency
        this.rate = rate
    }

    constructor(id: String, baseCurrencyRecord: Currency, currencyRecord: Currency, rate: Double) {
        this.id = id
        this.baseCurrency = baseCurrencyRecord
        this.currency = currencyRecord
        this.rate = rate
    }
}

