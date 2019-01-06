package com.buinak.curreq.entities.CurreqEntity

class CurrencyExchangeRate(var id: String? = null,
                           var currency: Currency? = null,
                           var baseCurrency: Currency? = null,
                           var rate: Double? = null,
                           var previousRate: Double? = null) {
}

