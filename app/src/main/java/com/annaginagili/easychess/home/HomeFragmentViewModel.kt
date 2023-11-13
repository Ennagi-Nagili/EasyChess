package com.annaginagili.easychess.home

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.annaginagili.easychess.model.History
import com.annaginagili.easychess.utils.Pieces
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class HomeFragmentViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val historyData = MutableLiveData<ArrayList<History>>()
    private val inviteData = MutableLiveData<HashMap<String, Any>>()
    private val profileData = MutableLiveData<Uri>()
    private val acceptData = MutableLiveData<HashMap<String, Any>>()
    private var sender = false

    fun sendGameRequest(user: String, searchText: String, context: Context) {
        firestore.collection("Users").get().addOnSuccessListener {
            var uid = ""
            for (i in it) {
                if (i.getString("username") == searchText) {
                    uid = i.id
                }
            }

            if (uid != "") {
                firestore.collection("Users").document(uid).get().addOnSuccessListener { it1 ->
                    if (it1.getString("currentGame") == null) {
                        val data1 = HashMap<String, Any>()
                        val gameId = UUID.randomUUID().toString()
                        data1["currentGame"] = gameId
                        data1["opponent"] = user
                        val side = kotlin.random.Random.nextInt(2)
                        data1["side"] = side
                        val data2 = HashMap<String, Any>()
                        data2["opponent"] = uid
                        data2["side"] = if (side == 0) {
                            1
                        } else {
                            0
                        }

                        firestore.collection("Users").document(uid)
                            .update(data1)
                        firestore.collection("Users").document(user)
                            .update(data2)

                        sender = true

                        val listener = firestore.collection("Games").document(gameId)
                            .addSnapshotListener { snapshot, e ->
                                if (e == null && snapshot != null) {
                                    if (snapshot.exists()) {
                                        if (snapshot.getString("turn") == null &&
                                            snapshot.getLong("winner") == null
                                        ) {
                                            val data = HashMap<String, Any>()
                                            data["gameId"] = gameId
                                            data["side"] = if (side == 0) {
                                                1
                                            } else {
                                                0
                                            }
                                            data["started"] = snapshot.getBoolean("started") != null
                                            acceptData.postValue(data)
                                        }
                                    }
                                }
                            }
                    } else {
                        Toast.makeText(
                            context, "User is in game",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    context, "User can't found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun observeUsers(user: String): LiveData<ArrayList<History>> {
        firestore.collection("Games").get().addOnSuccessListener {
            val historyList = ArrayList<History>()

            for (i in it) {
                if (i.getString("id1") == user || i.getString("id2") == user) {
                    val win = i.getString("winnerId") == user
                    if (i.getString("id1") == user) {
                        historyList.add(History(i.getString("user2")!!, win))
                    } else {
                        historyList.add(History(i.getString("user1")!!, win))
                    }
                }
            }

            Log.e("hello", historyList.toString())
            historyData.postValue(historyList)
        }

        return historyData
    }

    fun observeGame(user: String, context: Context): LiveData<HashMap<String, Any>> {
        val listener =
            firestore.collection("Users").document(user).addSnapshotListener { snapshot, e ->
                if (e == null && snapshot != null) {
                    if (snapshot.getString("currentGame") != null) {
                        val gameId = snapshot.getString("currentGame")!!

                        firestore.collection("Games").document(gameId).get().addOnSuccessListener {
                            if (it.getBoolean("started") != true) {
                                val builder = AlertDialog.Builder(context)
                                firestore.collection("Users").document(
                                    snapshot.getString("opponent")!!).get().addOnSuccessListener { it1 ->
                                    if (!sender) {
                                        builder.setMessage(
                                            "You have game request from " +
                                                    it1.getString("username")
                                        ).setCancelable(false)
                                            .setPositiveButton("Accept") { _, _ ->
                                                val piecesData = HashMap<String, Any>()
                                                val whitePieceArray = ArrayList<Int?>()
                                                val blackPieceArray = ArrayList<Int?>()

                                                for (i in Pieces.pieceList) {
                                                    whitePieceArray.add(i)
                                                }

                                                for (i in Pieces.pieceListBlack) {
                                                    blackPieceArray.add(i)
                                                }

                                                piecesData["whitePieces"] = whitePieceArray
                                                piecesData["blackPieces"] = blackPieceArray
                                                piecesData["user1"] = snapshot.getString("username")!!
                                                piecesData["user2"] = it1.getString("username")!!
                                                piecesData["id1"] = user
                                                piecesData["id2"] = it1.id

                                                if (it1.getString("currentGame") == null) {
                                                    firestore.collection("Games").document(gameId).set(piecesData)
                                                        .addOnSuccessListener {
                                                            val data = HashMap<String, Any>()
                                                            data["side"] = snapshot.getLong("side")!!
                                                            data["gameId"] = gameId
                                                            data["started"] = false
                                                            inviteData.postValue(data)
                                                        }
                                                } else {
                                                    val data = HashMap<String, Any>()
                                                    data["side"] = snapshot.getLong("side")!!
                                                    data["gameId"] = gameId
                                                    data["started"] = true
                                                    inviteData.postValue(data)
                                                }
                                            }
                                            .setNegativeButton("Reject") { p0, _ ->
                                                p0.cancel()
                                                if (it1.getString("currentGame") == null) {
                                                    val data = HashMap<String, Any?>()
                                                    data["currentGame"] = null
                                                    data["opponent"] = null
                                                    data["side"] = null
                                                    firestore.collection("Users").document(
                                                        it1.id).update(data).addOnSuccessListener {
                                                        firestore.collection("Users").document(user).update(data)
                                                    }
                                                }
                                            }

                                        builder.create().show()
                                    }

                                    sender = false
                                }
                            }
                        }
                    }
                }
            }

        return inviteData
    }

    fun observeProfile(user: String): LiveData<Uri> {
        FirebaseStorage.getInstance().reference.child(user).child("profile").downloadUrl
            .addOnSuccessListener {
                profileData.postValue(it)
            }
        return profileData
    }

    fun observeAccept(): LiveData<HashMap<String, Any>> {
        return acceptData
    }
}