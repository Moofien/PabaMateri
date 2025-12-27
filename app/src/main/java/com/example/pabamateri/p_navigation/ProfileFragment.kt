package com.example.pabamateri.p_navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pabamateri.R
import com.example.pabamateri.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // PERBAIKAN: Inisialisasi binding di sini (Langkah 42 di modul)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol lama (Navigasi biasa)
        binding?.btnDetailProfile?.setOnClickListener{
            findNavController().navigate(
                R.id.action_profileFragment_to_detailProfileFragment
            )
        }

        // Tombol baru (Safe Args - Langkah 70)
        binding?.buttonOpenSafe?.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToDetailProfileFragment(
                dataSatu = "a@gmail.com"
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}