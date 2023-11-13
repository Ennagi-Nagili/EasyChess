package com.annaginagili.easychess.fragment

import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.annaginagili.easychess.SignUpFragmentArgs
import com.annaginagili.easychess.SignUpFragmentDirections
import com.annaginagili.easychess.databinding.FragmentSignUpBinding
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SignUpFragment : Fragment() {
    lateinit var binding: FragmentSignUpBinding
    lateinit var image: ImageView
    lateinit var username: TextInputEditText
    lateinit var signup: Button
    private val args by navArgs<SignUpFragmentArgs>()
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    lateinit var auth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    lateinit var preferences: SharedPreferences
    lateinit var reference: StorageReference
    private var uri: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        image = binding.image
        username = binding.username
        signup = binding.signup
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        preferences =
            requireActivity().getSharedPreferences("EasyChess", AppCompatActivity.MODE_PRIVATE)
        reference = FirebaseStorage.getInstance().reference
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { chosenUri ->
            if (chosenUri != null) {
                Glide.with(requireActivity()).load(chosenUri).into(image)
                uri = chosenUri
            }
        }

        username.setText(args.user.username)
        reference.child(args.user.uid).child("profile").downloadUrl.addOnSuccessListener {
            Glide.with(requireActivity()).load(it).into(image)
        }

        image.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        signup.setOnClickListener {
            if (username.text.toString() != "") {
                if (args.credential != "0") {
                    preferences.edit().putString("token", args.credential).apply()
                }
                val data = HashMap<String, String?>()
                data["currentGame"] = null
                data["username"] = username.text.toString()

                firestore.collection("Users").document(auth.currentUser!!.uid)
                    .set(data).addOnSuccessListener {
                        if (uri != null) {
                            reference.child(auth.currentUser!!.uid).child("profile")
                                .putFile(uri!!).addOnSuccessListener {
                                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())
                                }
                        } else {
                            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())
                        }
                    }
            }
        }

        return binding.root
    }
}