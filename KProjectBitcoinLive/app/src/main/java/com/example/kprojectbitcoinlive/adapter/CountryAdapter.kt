package com.example.kprojectbitcoinlive.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kprojectbitcoinlive.databinding.SingleCountryItemBinding
import com.example.kprojectbitcoinlive.helpers.CountryListener
import com.example.kprojectbitcoinlive.model.CountryModel

class CountryAdapter(private val countryListener: CountryListener) : RecyclerView.Adapter<CountryAdapter.ItemViewHolder>() {

    private val countries = ArrayList<CountryModel>()

    companion object {
        const val URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleCountryItemBinding.inflate(inflater)
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = getCountryCodesAndCurrencies().size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(countries[position])

    inner class ItemViewHolder(private val binding: SingleCountryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CountryModel) {
            binding.item = item
            binding.listener = countryListener
            binding.executePendingBindings()
        }
    }

    private fun getCountryCodesAndCurrencies(): ArrayList<CountryModel> {
        countries.clear()
        countries.add(CountryModel("AUD", "$"))
        countries.add(CountryModel("BRL", "R$"))
        countries.add(CountryModel("CAD", "$"))
        countries.add(CountryModel("CNY", "¥"))
        countries.add(CountryModel("EUR", "€"))
        countries.add(CountryModel("GBP", "£"))
        countries.add(CountryModel("HKD", "$"))
        countries.add(CountryModel("IDR", "Rp"))
        countries.add(CountryModel("ILS", "₪"))
        countries.add(CountryModel("INR", "₹"))
        countries.add(CountryModel("JPY", "¥"))
        countries.add(CountryModel("MXN", "$"))
        countries.add(CountryModel("NOK", "kr"))
        countries.add(CountryModel("NZD", "$"))
        countries.add(CountryModel("PLN", "zł"))
        countries.add(CountryModel("RON", "lei"))
        countries.add(CountryModel("RUB", "\u20BD"))
        countries.add(CountryModel("SEK", "kr"))
        countries.add(CountryModel("SGD", "$"))
        countries.add(CountryModel("USD", "$"))
        countries.add(CountryModel("ZAR", "R"))
        return countries
    }


}