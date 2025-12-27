package com.example.pabamateri.p_LatFragmen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.pabamateri.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OperasiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OperasiFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_operasi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnKali = view.findViewById<Button>(R.id.btnKali)
        val btnBagi = view.findViewById<Button>(R.id.btnBagi)

        btnKali.setOnClickListener {
            val latFragmenActivity = requireActivity() as LatFragmenActivity
            val angka1 = latFragmenActivity.etAngka1.text.toString().toDoubleOrNull() ?: 0.0
            val angka2 = latFragmenActivity.etAngka2.text.toString().toDoubleOrNull() ?: 0.0
            val hasil = angka1 * angka2

            val fragment = HasilOperasiFragment().apply {
                arguments = Bundle().apply {
                    putString("OPERASI", "*")
                    putDouble("ANGKA1", angka1)
                    putDouble("ANGKA2", angka2)
                    putDouble("HASIL", hasil)
                }
            }
            latFragmenActivity.replaceFragment(fragment)
        }

        btnBagi.setOnClickListener {
            val latFragmenActivity = requireActivity() as LatFragmenActivity
            val angka1 = latFragmenActivity.etAngka1.text.toString().toDoubleOrNull() ?: 0.0
            val angka2 = latFragmenActivity.etAngka2.text.toString().toDoubleOrNull() ?: 0.0
            val hasil = if (angka2 != 0.0) angka1 / angka2 else 0.0

            val fragment = HasilOperasiFragment().apply {
                arguments = Bundle().apply {
                    putString("OPERASI", "/")
                    putDouble("ANGKA1", angka1)
                    putDouble("ANGKA2", angka2)
                    putDouble("HASIL", hasil)
                }
            }
            latFragmenActivity.replaceFragment(fragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OperasiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OperasiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}