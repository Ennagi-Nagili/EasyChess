package com.annaginagili.easychess

import android.R.attr.label
import android.R.attr.text
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.annaginagili.easychess.databinding.FragmentHomeBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var firestore: FirebaseFirestore
    lateinit var preferences: SharedPreferences
    lateinit var searchBar: TextInputEditText
    lateinit var searchButton: ImageButton
    lateinit var user: String
    lateinit var id: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater)
        button1 = binding.button1
        button2 = binding.button2
        firestore = FirebaseFirestore.getInstance()
        preferences = requireActivity().getSharedPreferences("EasyChess", Context.MODE_PRIVATE)
        searchBar = binding.searchBar
        searchButton = binding.searchButton
        id = binding.id

        if (preferences.getString("token", null) != null) {
            user = FirebaseAuth.getInstance().currentUser!!.uid

            id.text = "User id: $user"

            id.setOnClickListener {
                val clipboard: ClipboardManager =
                    requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText(user, user)
                clipboard.setPrimaryClip(clip)
            }

            val listener = firestore.collection("Users").document(user).addSnapshotListener { snapshot, e ->
                if (e == null && snapshot != null) {
                    if (snapshot.getString("currentGame") != null) {
                        val gameId = snapshot.getString("currentGame")!!
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage("You have game request from " +
                                snapshot.getString("opponent")).setCancelable(false)
                            .setPositiveButton("Accept") { _, _ ->
                                val piecesData = HashMap<String, ArrayList<Int?>>()
                                val whitePieceArray = ArrayList<Int?>()
                                val blackPieceArray = ArrayList<Int?>()

                                for (i in Squares.pieceList) {
                                    whitePieceArray.add(i)
                                }

                                for (i in Squares.pieceListBlack) {
                                    blackPieceArray.add(i)
                                }

                                piecesData["whitePieces"] = whitePieceArray
                                piecesData["blackPieces"] = blackPieceArray

                                firestore.collection("Games").document(gameId).set(piecesData)
                                    .addOnSuccessListener {
                                    if (snapshot.getLong("side") == 0L) {
                                        findNavController().navigate(HomeFragmentDirections
                                            .actionHomeFragmentToWhiteGameFragment(gameId))
                                    } else {
                                        findNavController().navigate(HomeFragmentDirections
                                            .actionHomeFragmentToBlackGameFragment(gameId))
                                    }
                                }
                            }
                            .setNegativeButton("Reject") { p0, _ ->
                                p0.cancel()
                                val data = HashMap<String, Any?>()
                                data["currentGame"] = null
                                data["opponent"] = null
                                data["side"] = null
                                firestore.collection("Users").document(user).update(data)
                            }

                        builder.create().show()
                    }
                }
            }

            searchButton.setOnClickListener {
                if (searchBar.text!!.isNotEmpty()) {
                    firestore.collection("Users").document(searchBar.text.toString())
                        .get().addOnSuccessListener {
                            if (it.exists()) {
                                if (it.getString("currentGame") == null) {
                                    val data1 = HashMap<String, Any>()
                                    val gameId = UUID.randomUUID()
                                    data1["currentGame"] = gameId.toString()
                                    data1["opponent"] = user
                                    val side = kotlin.random.Random.nextInt(2)
                                    data1["side"] = side
                                    val data2 = HashMap<String, Any>()
                                    data2["opponent"] = searchBar.text.toString()
                                    data2["side"] = if (side == 0) {
                                        1
                                    } else {
                                        0
                                    }

                                    firestore.collection("Users").document(searchBar.text.toString())
                                        .update(data1)
                                    firestore.collection("Users").document(user)
                                        .update(data2)

                                    firestore.collection("Games").document(gameId.toString())
                                        .addSnapshotListener {snapshot, e ->
                                            if (e == null && snapshot != null ) {
                                                if (snapshot.exists()) {
                                                    if (snapshot.getString("turn") == null &&
                                                            snapshot.getString("winner") == null) {
                                                        listener.remove()

                                                        if (side == 0) {
                                                            findNavController().navigate(HomeFragmentDirections
                                                                .actionHomeFragmentToBlackGameFragment(gameId.toString()))
                                                        } else {
                                                            findNavController().navigate(HomeFragmentDirections
                                                                .actionHomeFragmentToWhiteGameFragment(gameId.toString()))
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                }
                                else {
                                    Toast.makeText(requireContext(), "User is in game",
                                        Toast.LENGTH_SHORT).show()
                                }
                            }

                            else {
                                Toast.makeText(requireContext(), "User can't found",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        } else {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragmentFragment())
        }

        return binding.root
    }
}