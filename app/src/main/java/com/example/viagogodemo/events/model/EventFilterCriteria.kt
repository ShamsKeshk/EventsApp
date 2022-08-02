package com.example.viagogodemo.events.model

class EventFilterCriteria(var cityQuery: String? = null,
                          var price: Double = 0.0) {


    fun getPriceAsString(): String{
        return price.toString()
    }

    fun isContainsCityFilterCriteria(): Boolean{
        return !cityQuery.isNullOrEmpty()
    }

    fun isContainsPriceFilterCriteria(): Boolean{
        return price > 0
    }

    fun isContainsFilterCriteria(): Boolean{
        return isContainsCityFilterCriteria() || isContainsPriceFilterCriteria()
    }

    fun getFilterCriteriaCount(): String{
        var count = 0

        if (isContainsCityFilterCriteria())
            count += 1

        if (isContainsPriceFilterCriteria())
            count += 1

        return count.toString()
    }

}