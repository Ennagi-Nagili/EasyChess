package com.annaginagili.easychess.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annaginagili.easychess.activity.BlackGameActivity
import com.annaginagili.easychess.utils.HistoryAdapter
import com.annaginagili.easychess.HomeFragmentDirections
import com.annaginagili.easychess.R
import com.annaginagili.easychess.model.User
import com.annaginagili.easychess.activity.WhiteGameActivity
import com.annaginagili.easychess.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeFragmentViewModel
    lateinit var firestore: FirebaseFirestore
    lateinit var preferences: SharedPreferences
    lateinit var searchBar: TextInputEditText
    lateinit var searchButton: ImageButton
    lateinit var user: String
    lateinit var logout: ImageButton
    lateinit var users: RecyclerView
    lateinit var signInClient: GoogleSignInClient
    lateinit var gso: GoogleSignInOptions
    lateinit var profile: ShapeableImageView
    private var imageUri: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]
        firestore = FirebaseFirestore.getInstance()
        preferences = requireActivity().getSharedPreferences("EasyChess", Context.MODE_PRIVATE)
        searchBar = binding.searchBar
        searchButton = binding.searchButton
        logout = binding.logout
        users = binding.users
        profile = binding.profile

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.api_key)).requestEmail().build()
        signInClient = GoogleSignIn.getClient(requireActivity(), gso)

        logout.setOnClickListener {
            preferences.edit().putString("token", null).apply()
            signInClient.signOut()
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragmentFragment())
        }

        profile.setOnClickListener {
            firestore.collection("Users").document(user).get().addOnSuccessListener {
                val us = User(imageUri, it.getString("username")!!, user)
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSignUpFragment(
                        "0",
                        us
                    )
                )
            }
        }

        if (preferences.getString("token", null) != null) {
            user = FirebaseAuth.getInstance().currentUser!!.uid

            setUpObservers()

            users.setHasFixedSize(true)
            users.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            searchButton.setOnClickListener {
                if (searchBar.text!!.isNotEmpty()) {
                    viewModel.sendGameRequest(user, searchBar.text.toString(), requireContext())
                }
            }
        } else {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragmentFragment())
        }

        return binding.root
    }

    private fun setUpObservers(){
        viewModel.observeGame(user, requireContext()).observe(viewLifecycleOwner) {result ->
            if (result["started"] == false) {
                if (result["side"] == 0L) {
                    val intent1 = Intent(activity, WhiteGameActivity::class.java)
                    intent1.putExtra("gameId", result["gameId"].toString())
                    startActivity(intent1)
                } else if (result["side"] == 1L) {
                    val intent1 = Intent(activity, BlackGameActivity::class.java)
                    intent1.putExtra("gameId", result["gameId"].toString())
                    startActivity(intent1)
                }
            }
        }

        viewModel.observeProfile(user).observe(viewLifecycleOwner) {uri ->
            Glide.with(requireActivity()).load(uri).into(profile)
            imageUri = uri
        }

        viewModel.observeAccept().observe(viewLifecycleOwner) {
            if (it["started"] == false) {
                if (it["side"] == 0) {
                    val intent1 = Intent(activity, WhiteGameActivity::class.java)
                    intent1.putExtra("gameId", it["gameId"].toString())
                    startActivity(intent1)
                    firestore.collection("Users").document(user).update("currentGame", it["gameId"].toString())
                } else if (it["side"] == 1) {
                    val intent1 = Intent(activity, BlackGameActivity::class.java)
                    intent1.putExtra("gameId", it["gameId"].toString())
                    startActivity(intent1)
                    firestore.collection("Users").document(user).update("currentGame", it["gameId"].toString())
                }
            }
        }

        viewModel.observeUsers(user).observe(viewLifecycleOwner) { result ->
            users.adapter = HistoryAdapter(requireContext(), result)
        }
    }
}