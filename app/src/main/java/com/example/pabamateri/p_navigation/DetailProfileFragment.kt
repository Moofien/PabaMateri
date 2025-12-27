package com.example.pabamateri.p_navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
// Pastikan import ini ada (sesuaikan dengan nama paketmu jika beda)
import com.example.pabamateri.databinding.FragmentDetailProfileBinding

class DetailProfileFragment : Fragment() {

    // PERBAIKAN: Menambahkan variabel binding (Langkah 56)
    private var binding: FragmentDetailProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // PERBAIKAN: Menggunakan binding untuk layout (Langkah 57)
        binding = FragmentDetailProfileBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // PERBAIKAN: Menangkap data Safe Args (Langkah 71)
        if (arguments != null) {
            val dataTerima = DetailProfileFragmentArgs.fromBundle(arguments as Bundle).dataSatu
            // Menampilkan ke TextView yang baru kita buat di XML
            binding?.tvParamSafeArgs?.text = dataTerima
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}