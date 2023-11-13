package com.annaginagili.easychess.activity

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.annaginagili.easychess.R
import com.annaginagili.easychess.databinding.ActivityBlackGameBinding
import com.annaginagili.easychess.databinding.WinLayoutBinding
import com.annaginagili.easychess.utils.Pieces
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.DecimalFormat

class BlackGameActivity : AppCompatActivity() {
    lateinit var binding: ActivityBlackGameBinding
    var selectedTag: Int? = null
    var selectedSquare: ImageView? = null
    lateinit var selectedSteps: List<Int>
    lateinit var firestore: FirebaseFirestore
    lateinit var user: String
    lateinit var turn: String
    lateinit var resign: ImageButton
    lateinit var draw: TextView
    lateinit var profile: ShapeableImageView
    lateinit var profile2: ShapeableImageView
    lateinit var username: TextView
    lateinit var username2: TextView
    lateinit var timer1: TextView
    lateinit var timer2: TextView
    var resigned = false
    var tim1: Long = 600000
    var tim2: Long = 600000
    lateinit var time1: CountDownTimer
    lateinit var time2: CountDownTimer
    var short = true
    var long = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlackGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = FirebaseFirestore.getInstance()
        user = FirebaseAuth.getInstance().currentUser!!.uid
        resign = binding.resign
        draw = binding.draw
        profile = binding.profile
        profile2 = binding.profile2
        username = binding.username
        username2 = binding.username2
        timer1 = binding.time1
        timer2 = binding.time2

        firestore.collection("Games").document(intent.getStringExtra("gameId")!!)
            .update("started", true)

        time1 = object: CountDownTimer(tim1, 1000) {
            override fun onTick(p0: Long) {
                tim1 = p0
                val f = DecimalFormat("00")
                val min = (p0 / 60000) % 60
                val sec = (p0 / 1000) % 60
                timer1.text = f.format(min) + ":" + f.format(sec)
            }

            override fun onFinish() {
                time2.cancel()
            }
        }

        time2 = object: CountDownTimer(tim2, 1000) {
            override fun onTick(p0: Long) {
                tim2 = p0
                val f = DecimalFormat("00")
                val min = (p0 / 60000) % 60
                val sec = (p0 / 1000) % 60
                timer2.text = f.format(min) + ":" + f.format(sec)
            }

            override fun onFinish() {
                time1.cancel()

                firestore.collection("Users").document(user).get().addOnSuccessListener {
                    val winData = HashMap<String, Any>()
                    winData["winner"] = 0
                    winData["winnerId"] = it.getString("opponent")!!
                    firestore.collection("Games").document(intent.getStringExtra("gameId")!!)
                        .update(winData).addOnSuccessListener {it1->
                            val data = HashMap<String, Any?>()
                            data["currentGame"] = null
                            data["opponent"] = null
                            data["side"] = null
                            firestore.collection("Users").document(it.getString("opponent")!!)
                                .update(data).addOnSuccessListener {
                                    firestore.collection("Users").document(user).update(data).addOnSuccessListener {
                                        resigned = true
                                    }
                                }
                        }
                }
            }
        }

        firestore.collection("Users").document(user).get().addOnSuccessListener {
            turn = if (it.getLong("side") == 0L) {
                ""
            } else {
                user
            }
        }

        resign.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure about resign?").setCancelable(true)
                .setPositiveButton("Yes") { _, _ ->
                    time1.cancel()

                    time2.cancel()

                    firestore.collection("Users").document(user).get().addOnSuccessListener {
                        val winData = HashMap<String, Any>()
                        winData["winner"] = 0
                        winData["winnerId"] = it.getString("opponent")!!
                        firestore.collection("Games").document(intent.getStringExtra("gameId")!!)
                            .update(winData).addOnSuccessListener { it1 ->
                                val data = HashMap<String, Any?>()
                                data["currentGame"] = null
                                data["opponent"] = null
                                data["side"] = null
                                firestore.collection("Users").document(it.getString("opponent")!!)
                                    .update(data).addOnSuccessListener {
                                        firestore.collection("Users").document(user).update(data)
                                    }
                            }
                    }
                }.setNegativeButton("No") { p0, _ ->
                    p0.cancel()
                }

            builder.create().show()
            resigned = true
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!resigned) {
                    val builder = AlertDialog.Builder(this@BlackGameActivity)
                    builder.setMessage("Are you sure about resign?").setCancelable(true)
                        .setPositiveButton("Yes") {_, _ ->
                            time1.cancel()
                            time2.cancel()

                            firestore.collection("Users").document(user).get().addOnSuccessListener {
                                val winData = HashMap<String, Any>()
                                winData["winner"] = 0
                                winData["winnerId"] = it.getString("opponent")!!
                                firestore.collection("Games").document(intent.getStringExtra("gameId")!!)
                                    .update(winData).addOnSuccessListener {it1->
                                        val data = HashMap<String, Any?>()
                                        data["currentGame"] = null
                                        data["opponent"] = null
                                        data["side"] = null
                                        firestore.collection("Users").document(it.getString("opponent")!!)
                                            .update(data).addOnSuccessListener {
                                                firestore.collection("Users").document(user).update(data)
                                            }
                                    }
                            }
                        } .setNegativeButton("No") {p0, _ ->
                            p0.cancel()
                        }

                    builder.create().show()
                    resigned = true
                } else {
                    Pieces.pieceList.clear()
                    Pieces.pieceList.addAll(Pieces.pieceListD)
                    Pieces.pieceListBlack.clear()
                    Pieces.pieceListBlack.addAll(Pieces.pieceListBlackD)
                    finish()
                }
            }
        })

        firestore.collection("Users").document(user).get().addOnSuccessListener {
            username2.text = it.getString("username")
            firestore.collection("Users").document(it.getString("opponent")!!).get()
                .addOnSuccessListener { it1 ->
                    username.text = it1.getString("username")
                }

            FirebaseStorage.getInstance().reference.child(user).child("profile").downloadUrl
                .addOnSuccessListener { uri ->
                    Glide.with(this).load(uri).into(profile2)
                }

            FirebaseStorage.getInstance().reference.child(it.getString("opponent")!!)
                .child("profile").downloadUrl.addOnSuccessListener { uri ->
                    Glide.with(this).load(uri).into(profile)
                }
        }

        firestore.collection("Games").document(intent.getStringExtra("gameId")!!).addSnapshotListener { snapshot, e ->
            if (snapshot != null) {
                if (snapshot.getLong("winner") == 1L) {
                    time1.cancel()

                    time2.cancel()

                    val builder = AlertDialog.Builder(this)
                    val view = layoutInflater.inflate(R.layout.win_layout, null)
                    builder.setView(view)
                    builder.setCancelable(true).setPositiveButton("cancel") { p0, _ ->
                        p0.cancel()
                    }
                    builder.create().show()
                    val data = HashMap<String, Any?>()
                    data["currentGame"] = null
                    data["opponent"] = null
                    data["side"] = null
                    firestore.collection("Users").document(snapshot.getString("id1")!!)
                        .update(data).addOnSuccessListener {
                            firestore.collection("Users").document(snapshot.getString("id2")!!)
                                .update(data).addOnSuccessListener {
                                    resigned = true
                                }
                        }
                } else if (snapshot.getLong("winner") == 0L) {
                    time1.cancel()

                    time2.cancel()

                    val builder = AlertDialog.Builder(this)
                    val view = layoutInflater.inflate(R.layout.win_layout, null)
                    WinLayoutBinding.bind(view).text.text = "You Lose"
                    builder.setView(view)
                    builder.setCancelable(true).setPositiveButton("cancel") { p0, _ ->
                        p0.cancel()
                    }
                    builder.create().show()
                    val data = HashMap<String, Any?>()
                    data["currentGame"] = null
                    data["opponent"] = null
                    data["side"] = null
                    firestore.collection("Users").document(snapshot.getString("id1")!!)
                        .update(data).addOnSuccessListener {
                            firestore.collection("Users").document(snapshot.getString("id2")!!)
                                .update(data).addOnSuccessListener {
                                    resigned = true
                                }
                        }
                }

                if (snapshot.getString("turn") != user) {
                    val whitePieceData = snapshot.get("whitePieces") as MutableList<*>
                    val whitePiecesInt = ArrayList<Int?>()
                    val blackPiecesInt = ArrayList<Int?>()
                    for (i in whitePieceData) {
                        if (i != null) {
                            whitePiecesInt.add((i as Long).toInt())
                        } else {
                            whitePiecesInt.add(i)
                        }
                    }

                    for (i in 63 downTo 0) {
                        if (whitePieceData[i] != null) {
                            blackPiecesInt.add((whitePieceData[i] as Long).toInt())
                        } else {
                            blackPiecesInt.add(null)
                        }
                    }

                    Pieces.pieceList = whitePiecesInt
                    Pieces.pieceListBlack = blackPiecesInt

                    for (i in 63 downTo 0) {
                        val pie = Pieces.setClick(binding)[63-i]
                        if (Pieces.pieceListBlack[63-i] != null) {
                            pie.setImageResource(Pieces.pieceListBlack[63-i]!!)
                        } else {
                            Pieces.setClick(binding)[63 - i].setImageResource(0)
                        }

                        val steps = if (Pieces.pieceListBlack[63-i] != null) {
                            Pieces.getSteps(Pieces.pieceListBlack[63-i]!!)
                        } else {
                            listOf()
                        }

                        pie.setOnClickListener {
                            click(pie, pie.tag.toString().toInt(), steps)
                        }
                    }

                    if (snapshot.getString("turn") != null) {
                        turn = snapshot.getString("turn")!!
                    }
                }

                if (snapshot.getString("turn") != null) {
                    if (snapshot.getString("turn") == user) {
                        time2.cancel()

                        time1 = object: CountDownTimer(tim1, 1000) {
                            override fun onTick(p0: Long) {
                                tim1 = p0
                                val f = DecimalFormat("00")
                                val min = (p0 / 60000) % 60
                                val sec = (p0 / 1000) % 60
                                timer1.text = f.format(min) + ":" + f.format(sec)
                            }

                            override fun onFinish() {
                                firestore.collection("Users").document(user).get().addOnSuccessListener {
                                    val winData = HashMap<String, Any>()
                                    winData["winner"] = 1
                                    winData["winnerId"] = it.getString("opponent")!!
                                    firestore.collection("Games").document(intent.getStringExtra("gameId")!!)
                                        .update(winData).addOnSuccessListener {it1->
                                            val data = HashMap<String, Any?>()
                                            data["currentGame"] = null
                                            data["opponent"] = null
                                            data["side"] = null
                                            firestore.collection("Users").document(it.getString("opponent")!!)
                                                .update(data).addOnSuccessListener {
                                                    firestore.collection("Users").document(user).update(data).addOnSuccessListener {
                                                        resigned = true
                                                    }
                                                }
                                        }

                                }
                            }
                        }.start()
                    } else {
                        time1.cancel()

                        time2 = object: CountDownTimer(tim2, 1000) {
                            override fun onTick(p0: Long) {
                                tim2 = p0
                                val f = DecimalFormat("00")
                                val min = (p0 / 60000) % 60
                                val sec = (p0 / 1000) % 60
                                timer2.text = f.format(min) + ":" + f.format(sec)
                            }

                            override fun onFinish() {
                                firestore.collection("Users").document(user).get().addOnSuccessListener {
                                    val winData = HashMap<String, Any>()
                                    winData["winner"] = 0
                                    winData["winnerId"] = it.getString("opponent")!!
                                    firestore.collection("Games").document(intent.getStringExtra("gameId")!!)
                                        .update(winData).addOnSuccessListener {it1->
                                            val data = HashMap<String, Any?>()
                                            data["currentGame"] = null
                                            data["opponent"] = null
                                            data["side"] = null
                                            firestore.collection("Users").document(it.getString("opponent")!!)
                                                .update(data).addOnSuccessListener {
                                                    firestore.collection("Users").document(user).update(data).addOnSuccessListener {
                                                        resigned = true
                                                    }
                                                }
                                        }

                                }
                            }
                        }.start()
                    }
                } else {
                    time2.cancel()

                    time1 = object: CountDownTimer(tim1, 1000) {
                        override fun onTick(p0: Long) {
                            tim1 = p0
                            val f = DecimalFormat("00")
                            val min = (p0 / 60000) % 60
                            val sec = (p0 / 1000) % 60
                            timer1.text = f.format(min) + ":" + f.format(sec)
                        }

                        override fun onFinish() {
                            firestore.collection("Games").document(intent.getStringExtra("gameId")!!)
                                .update("winner", 1).addOnSuccessListener {
                                    val data = HashMap<String, Any?>()
                                    data["currentGame"] = null
                                    data["opponent"] = null
                                    data["side"] = null
                                    firestore.collection("Users").document(snapshot.getString("id1")!!)
                                        .update(data).addOnSuccessListener {
                                            firestore.collection("Users").document(snapshot.getString("id2")!!)
                                                .update(data).addOnSuccessListener {
                                                resigned = true
                                            }
                                        }
                                }
                        }
                    }.start()
                }
            }
        }
    }

    fun click(square: ImageView, tag: Int, steps: List<Int>) {
        if (Pieces.pieceListBlack[tag - 1] in Pieces.blackPieces) {
            if (selectedTag != null) {
                selectedSquare!!.setBackgroundResource(Pieces.squareList[selectedTag!! - 1])
            }
            if (Pieces.squareList[tag - 1] == R.drawable.square_brown_dark_svg) {
                square.setBackgroundResource(R.drawable.square_selected_dark_svg)
            } else {
                square.setBackgroundResource(R.drawable.square_selected_light_svg)
            }
            selectedTag = tag
            selectedSquare = square
            selectedSteps = steps
            Log.e("hello", steps.toString())
        } else {
            if (selectedTag != null) {
                val distance = selectedTag!!.toInt() - tag

                if (Pieces.pieceListBlack[selectedTag!!-1] == R.drawable.b_king_svg_noshadow && (tag == 6 || tag == 2)) {
                    if (!kingCheck(selectedTag, selectedTag, R.drawable.b_king_svg_noshadow, Pieces.whitePieces)) {
                        if (tag == 2) {
                            castle(short, tag, arrayListOf(1, 2), square, steps, 2, 1)
                        } else {
                            castle(long, tag, arrayListOf(4, 5, 6), square, steps, 4, 8)
                        }
                    } else {
                        selectedSquare!!.setBackgroundResource(Pieces.squareList[selectedTag!!.toInt() - 1])
                    }
                }

                else if (checker(
                        Pieces.getDirection(tag, selectedTag!!, Pieces.pieceListBlack[selectedTag!! - 1]!!),
                        selectedTag!!, tag, distance
                    ) || Pieces.pieceListBlack[selectedTag!! - 1] ==
                    R.drawable.b_knight_svg_noshadow
                ) {
                    if (Pieces.pieceListBlack[selectedTag!! - 1] == R.drawable.b_pawn_svg_noshadow) {
                        if (distance == -7 || distance == -9) {
                            if (Pieces.pieceListBlack[tag - 1] in Pieces.whitePieces) {
                                move(
                                    tag,
                                    Pieces.pieceListBlack[selectedTag!!.toInt() - 1]!!,
                                    selectedSteps,
                                    square,
                                    distance
                                )
                            }
                        } else {
                            move(
                                tag,
                                Pieces.pieceListBlack[selectedTag!!.toInt() - 1]!!,
                                selectedSteps,
                                square,
                                distance
                            )
                        }
                    } else {
                        move(
                            tag, Pieces.pieceListBlack[selectedTag!!.toInt() - 1]!!, selectedSteps,
                            square, distance
                        )
                    }
                } else {
                    selectedSquare!!.setBackgroundResource(Pieces.squareList[selectedTag!!.toInt() - 1])
                }
            }
        }
    }

    fun move(tag: Int, piece: Int, steps: List<Int>, square: ImageView, distance: Int) {
        if (Pieces.pieceListBlack[tag - 1] !in Pieces.blackPieces) {
            if (distance in steps && turn != user && !kingCheck(tag, selectedTag!!, piece, Pieces.whitePieces)) {
                if (Pieces.pieceListBlack[selectedTag!!-1] == R.drawable.w_king_svg_noshadow) {
                    short = false
                    long = false
                }

                else if (Pieces.pieceListBlack[selectedTag!!-1] == R.drawable.b_rook_svg_noshadow) {
                    if (selectedTag == 1) {
                        short = false
                    } else {
                        long = false
                    }
                }

                if ((tag + 1) / 8 >= 7 && Pieces.pieceListBlack[selectedTag!! - 1] == R.drawable.b_pawn_svg_noshadow) {
                    val builder = AlertDialog.Builder(this)
                    val view = layoutInflater.inflate(R.layout.select_black_layout, null)
                    builder.setView(view)
                    builder.setCancelable(false)
                    val alert = builder.create()

                    view.findViewById<ImageView>(R.id.queen).setOnClickListener {
                        square.setImageResource(R.drawable.b_queen_svg_noshadow)
                        Pieces.pieceListBlack[tag - 1] = R.drawable.b_queen_svg_noshadow
                        foo(tag, steps, selectedTag!!)
                        alert.cancel()
                    }
                    view.findViewById<ImageView>(R.id.rook).setOnClickListener {
                        square.setImageResource(R.drawable.b_rook_svg_noshadow)
                        Pieces.pieceListBlack[tag - 1] = R.drawable.b_rook_svg_noshadow
                        foo(tag, steps, selectedTag!!)
                        alert.cancel()
                    }
                    view.findViewById<ImageView>(R.id.knight).setOnClickListener {
                        square.setImageResource(R.drawable.b_knight_svg_noshadow)
                        Pieces.pieceListBlack[tag - 1] = R.drawable.b_knight_svg_noshadow
                        foo(tag, steps, selectedTag!!)
                        alert.cancel()
                    }
                    view.findViewById<ImageView>(R.id.bishop).setOnClickListener {
                        square.setImageResource(R.drawable.b_bishop_svg_noshadow)
                        Pieces.pieceListBlack[tag - 1] = R.drawable.b_bishop_svg_noshadow
                        foo(tag, steps, selectedTag!!)
                        alert.cancel()
                    }

                    alert.show()
                } else {
                    square.setImageResource(piece)
                    Pieces.pieceListBlack[tag - 1] = piece
                    foo(tag, steps, selectedTag!!)
                }
            } else {
                selectedSquare!!.setBackgroundResource(Pieces.squareList[selectedTag!!.toInt() - 1])
                selectedTag = null

                mateCheck()
            }
        }
    }

    fun checker(steps: List<Int>, selectedTag: Int, tag: Int, distance: Int): Boolean {
        var result = true
        for (i in steps) {
            var squareInDistance = selectedTag - i
            if (squareInDistance < 0) {
                squareInDistance *= -1
            }

            if (squareInDistance in (distance)..63 && (squareInDistance != tag ||
                        Pieces.pieceListBlack[selectedTag - 1] == R.drawable.b_pawn_svg_noshadow) && squareInDistance != 0
            ) {

                if (Pieces.pieceListBlack[squareInDistance - 1] in Pieces.allPieces && steps != Pieces.pawnAttack) {
                    result = false
                } else if (Pieces.pieceListBlack[selectedTag - 1] == R.drawable.b_pawn_svg_noshadow &&
                    selectedTag.toDouble() / 8 > 2 && tag - selectedTag > 9
                ) {
                    result = false
                }
            } else {
                break
            }
        }
        return result
    }

    fun kingCheck(tag: Int?, selectedTag: Int?, piece: Int?, side: List<Int>): Boolean {
        var result = false
        val list = mutableListOf<Int?>()
        var king = R.drawable.b_king_svg_noshadow
        var knight = R.drawable.w_knight_svg_noshadow
        list.addAll(Pieces.pieceListBlack)
        if (side == Pieces.blackPieces) {
            king = R.drawable.w_king_svg_noshadow
            knight = R.drawable.b_knight_svg_noshadow
        }

        if (tag != null) {
            list[selectedTag!! - 1] = null
            list[tag - 1] = piece
        }

        for (i in 0 until list.size) {
            if (list[i] != null && list[i] in side) {
                for (j in Pieces.getMoves(list[i]!!)) {
                    for (t in j) {
                        val distance = i - t
                        if (distance in 1..63) {
                            if (list[distance] != null) {
//                                if (side == Squares.blackPieces) {
//                                    Log.e("hello", distance.toString()+"sa")
//                                    Log.e("hello", i.toString()+"da")
//                                }
                                if (list[distance] == king) {
                                    result = true
                                } else {
                                    if (list[i] != knight) {
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result
    }

    fun mateCheck() {
        var result = true

        for (i in 0 until Pieces.pieceListBlack.size) {
            if (Pieces.pieceListBlack[i] in Pieces.whitePieces) {
                for (j in Pieces.getMoves(Pieces.pieceListBlack[i]!!)) {
                    for (t in j) {
                        val distance = i - t
                        if (distance in 1..63 && Pieces.pieceListBlack[distance] !in Pieces.whitePieces
                            && checker(j, i + 1, distance + 1, i - distance)
                        ) {
                            if (!kingCheck(
                                    distance + 1,
                                    i + 1,
                                    Pieces.pieceListBlack[i]!!,
                                    Pieces.blackPieces
                                )
                            ) {
                                result = false
                            }
                        }
                    }
                }
            }
        }

        if (result) {
            firestore.collection("Games").document(intent.getStringExtra("gameId")!!).update("winner", 0)
                .addOnSuccessListener {
                    val data = HashMap<String, Any?>()
                    data["currentGame"] = null
                    data["opponent"] = null
                    data["side"] = null
                    firestore.collection("Users").document(user).update(data)
                }
        }
    }

    private fun foo(tag: Int, steps: List<Int>, selected: Int) {
        Pieces.setClick(binding)[selected-1].setImageResource(0)
        Pieces.pieceListBlack[selected-1] = null

        val data = HashMap<String, Any>()
        data["blackPieces"] = Pieces.pieceListBlack
        data["turn"] = user
        firestore.collection("Games").document(intent.getStringExtra("gameId")!!).update(data)

        turn = user

        val clickPrevious = Pieces.setClick(binding)[selected-1]
        val clickNext = Pieces.setClick(binding)[tag-1]

        clickNext.setOnClickListener {
            click(clickNext, clickNext.tag.toString().toInt(), steps)
        }

        clickPrevious.setOnClickListener {
            click(clickPrevious, clickPrevious.tag.toString().toInt(), listOf())
        }

        if (selected == selectedTag) {
            selectedTag = null
            selectedSquare!!.setBackgroundResource(Pieces.squareList[selected - 1])
        }

        mateCheck()
    }

    fun castle(type: Boolean, tag: Int, near: ArrayList<Int>, square: ImageView, steps: List<Int>,
               rookN: Int, rook: Int) {
        if (type && turn != user && !kingCheck(tag, selectedTag!!,
                R.drawable.b_king_svg_noshadow, Pieces.whitePieces)) {
            short = false
            long = false

            var nearCheck = true

            for (i in near) {
                if (Pieces.pieceListBlack[i] != null) {
                    nearCheck = false
                }
            }

            if (nearCheck) {
                square.setImageResource(R.drawable.b_king_svg_noshadow)
                Pieces.setClick(binding)[rookN].setImageResource(R.drawable.b_rook_svg_noshadow)
                Pieces.pieceListBlack[tag-1] = R.drawable.b_king_svg_noshadow
                Pieces.pieceListBlack[rookN] = R.drawable.b_rook_svg_noshadow
                foo(tag, steps, selectedTag!!)
                foo(rookN+1, Pieces.getSteps(R.drawable.b_rook_svg_noshadow), rook)
            } else {
                selectedSquare!!.setBackgroundResource(Pieces.squareList[selectedTag!!.toInt() - 1])
            }
        } else {
            selectedSquare!!.setBackgroundResource(Pieces.squareList[selectedTag!!.toInt() - 1])
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("hello", "Destroyyyy")
    }
}