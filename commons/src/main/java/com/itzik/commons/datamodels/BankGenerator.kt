package com.itzik.commons.datamodels


object BankGenerator {
   private val  bankList = mutableListOf<BankData>()

    @JvmStatic
    fun makeBankList(): List<BankData>{
        bankList.clear()
       var bank = BankData("JPMorgan", "JPM",
           "https://www.interbrand.com/assets/00000001535.png", 100)
        bankList.add(bank)
        bank = BankData("Bank of America", "BAC",
            "https://www.charlotteobserver.com/latest-news/uiy86c/picture6131838/alternates/FREE_1140/E8VhA.So.138.jpg", 99)
        bankList.add(bank)
        bank = BankData("Citigroup", "C",
                  "https://pentagram-production.imgix.net/wp/592ca89f19a1d/ps-citibank-01.jpg", 80)
        bankList.add(bank)
        bank = BankData("Wells Fargo", "IIS",
            "https://motorsportsnewswire.com/wp-content/uploads/2019/08/Wells-Fargo-Company-logo-678.jpg", 15)
        bankList.add(bank)
        bank = BankData("Morgan Stanley", "MS",
                   "https://www.spglobal.com/_assets/images/leveragedloan/2012/07/morgan-stanley-logo.jpg", 15)
        bankList.add(bank)
        return bankList
    }


    fun makeApiUrlRequest(stk: String, interval: String): String {
       return "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=$stk&interval=${interval}min&apikey=587ATESNS3Z018MJ"
    }
}