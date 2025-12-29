package com.example.pabamateri.p_navigation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pabamateri.databinding.FragmentKeranjangBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class KeranjangFragment : Fragment() {

    private var _binding: FragmentKeranjangBinding? = null
    private val binding get() = _binding!!

    lateinit var sp: SharedPreferences
    val gson = Gson()
    var arCart = arrayListOf<Bahan>()
    lateinit var adapter: AdapterBahan

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKeranjangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sp = requireActivity().getSharedPreferences("my_sp_cooking", Context.MODE_PRIVATE)

        loadCart()

        // Setup Adapter (isCart = true)
        adapter = AdapterBahan(arCart, isCart = true) { item, aksi ->
            when (aksi) {
                "DELETE" -> {
                    arCart.remove(item)
                    saveData("dt_cart", arCart)
                    adapter.notifyDataSetChanged()
                }
                "CART_OR_CHECK" -> {
                    // Logic Checkout: Pindah ke dt_bought, Hapus dari dt_cart
                    moveToBought(item)
                }
            }
        }
        binding.rvKeranjang.layoutManager = LinearLayoutManager(context)
        binding.rvKeranjang.adapter = adapter
    }

    private fun moveToBought(item: Bahan) {
        // 1. Simpan ke dt_bought
        val jsonBought = sp.getString("dt_bought", null)
        val listBought = if (jsonBought != null) {
            gson.fromJson<ArrayList<Bahan>>(jsonBought, object : TypeToken<ArrayList<Bahan>>() {}.type)
        } else {
            arrayListOf()
        }
        listBought.add(item)
        val editor = sp.edit()
        editor.putString("dt_bought", gson.toJson(listBought))

        // 2. Hapus dari Cart
        arCart.remove(item)
        editor.putString("dt_cart", gson.toJson(arCart)) // Simpan perubahan cart
        editor.apply()

        adapter.notifyDataSetChanged()
        Toast.makeText(context, "Barang terbeli!", Toast.LENGTH_SHORT).show()
    }

    private fun saveData(key: String, list: ArrayList<Bahan>) {
        sp.edit().putString(key, gson.toJson(list)).apply()
    }

    private fun loadCart() {
        val json = sp.getString("dt_cart", null)
        if (json != null) {
            arCart = gson.fromJson(json, object : TypeToken<ArrayList<Bahan>>() {}.type)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}