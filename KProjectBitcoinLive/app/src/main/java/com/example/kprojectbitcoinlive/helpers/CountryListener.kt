package com.example.kprojectbitcoinlive.helpers

import com.example.kprojectbitcoinlive.model.CountryModel

interface CountryListener {
    fun onCountrySelected(country: CountryModel)
}