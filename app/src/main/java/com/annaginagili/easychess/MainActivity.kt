package com.annaginagili.easychess

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import com.annaginagili.easychess.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = FirebaseFirestore.getInstance()

//        val whiteGameFragment = supportFragmentManager.findFragmentById(R.id.whiteGameFragment)
//        val blackGameFragment = supportFragmentManager.findFragmentById(R.id.blackGameFragment)
//
//        if (FirebaseAuth.getInstance().currentUser != null) {
//            val user = FirebaseAuth.getInstance().currentUser!!.uid
//
//            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    if ((whiteGameFragment != null && whiteGameFragment.isVisible) ||
//                        (blackGameFragment != null && blackGameFragment.isVisible)) {
//                        val builder = AlertDialog.Builder(this@MainActivity)
//                        builder.setMessage("Are you sure about resign?").setCancelable(true)
//                            .setPositiveButton("Yes") {_, _ ->
//                                firestore.collection("Users").document(user).get().addOnSuccessListener {
//                                    firestore.collection("Games").document(it.getString("currentGame")!!)
//                                        .update("winner", 0)
//                                        .addOnSuccessListener {
//                                            val data = HashMap<String, Any?>()
//                                            data["currentGame"] = null
//                                            data["opponent"] = null
//                                            data["side"] = null
//                                            firestore.collection("Users").document(user).update(data)
//                                        }
//                                }
//                            } .setNegativeButton("No") {p0, _ ->
//                                p0.cancel()
//                            }
//
//                        builder.create().show()
//                    } else {
//                        finish()
//                    }
//                }
//            })
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.e("hello", "back")
    }
}