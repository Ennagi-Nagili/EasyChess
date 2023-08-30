package com.annaginagili.easychess

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.annaginagili.easychess.databinding.FragmentWhiteGameBinding
import com.annaginagili.easychess.databinding.WinLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import kotlin.reflect.typeOf

class WhiteGameFragment : Fragment() {
    lateinit var binding: FragmentWhiteGameBinding
    lateinit var a1: ImageView
    lateinit var a2: ImageView
    lateinit var a3: ImageView
    lateinit var a4: ImageView
    lateinit var a5: ImageView
    lateinit var a6: ImageView
    lateinit var a7: ImageView
    lateinit var a8: ImageView
    lateinit var b1: ImageView
    lateinit var b2: ImageView
    lateinit var b3: ImageView
    lateinit var b4: ImageView
    lateinit var b5: ImageView
    lateinit var b6: ImageView
    lateinit var b7: ImageView
    lateinit var b8: ImageView
    lateinit var c1: ImageView
    lateinit var c2: ImageView
    lateinit var c3: ImageView
    lateinit var c4: ImageView
    lateinit var c5: ImageView
    lateinit var c6: ImageView
    lateinit var c7: ImageView
    lateinit var c8: ImageView
    lateinit var d1: ImageView
    lateinit var d2: ImageView
    lateinit var d3: ImageView
    lateinit var d4: ImageView
    lateinit var d5: ImageView
    lateinit var d6: ImageView
    lateinit var d7: ImageView
    lateinit var d8: ImageView
    lateinit var e1: ImageView
    lateinit var e2: ImageView
    lateinit var e3: ImageView
    lateinit var e4: ImageView
    lateinit var e5: ImageView
    lateinit var e6: ImageView
    lateinit var e7: ImageView
    lateinit var e8: ImageView
    lateinit var f1: ImageView
    lateinit var f2: ImageView
    lateinit var f3: ImageView
    lateinit var f4: ImageView
    lateinit var f5: ImageView
    lateinit var f6: ImageView
    lateinit var f7: ImageView
    lateinit var f8: ImageView
    lateinit var g1: ImageView
    lateinit var g2: ImageView
    lateinit var g3: ImageView
    lateinit var g4: ImageView
    lateinit var g5: ImageView
    lateinit var g6: ImageView
    lateinit var g7: ImageView
    lateinit var g8: ImageView
    lateinit var h1: ImageView
    lateinit var h2: ImageView
    lateinit var h3: ImageView
    lateinit var h4: ImageView
    lateinit var h5: ImageView
    lateinit var h6: ImageView
    lateinit var h7: ImageView
    lateinit var h8: ImageView
    var selectedTag: Int? = null
    var selectedSquare: ImageView? = null
    lateinit var selectedSteps: List<Int>
    private val args by navArgs<WhiteGameFragmentArgs>()
    lateinit var firestore: FirebaseFirestore
    lateinit var user: String
    lateinit var turn: String
    lateinit var resign: ImageButton
    lateinit var draw: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentWhiteGameBinding.inflate(inflater)
        a1 = binding.a1
        a2 = binding.a2
        a3 = binding.a3
        a4 = binding.a4
        a5 = binding.a5
        a6 = binding.a6
        a7 = binding.a7
        a8 = binding.a8
        b1 = binding.b1
        b2 = binding.b2
        b3 = binding.b3
        b4 = binding.b4
        b5 = binding.b5
        b6 = binding.b6
        b7 = binding.b7
        b8 = binding.b8
        c1 = binding.c1
        c2 = binding.c2
        c3 = binding.c3
        c4 = binding.c4
        c5 = binding.c5
        c6 = binding.c6
        c7 = binding.c7
        c8 = binding.c8
        d1 = binding.d1
        d2 = binding.d2
        d3 = binding.d3
        d4 = binding.d4
        d5 = binding.d5
        d6 = binding.d6
        d7 = binding.d7
        d8 = binding.d8
        e1 = binding.e1
        e2 = binding.e2
        e3 = binding.e3
        e4 = binding.e4
        e5 = binding.e5
        e6 = binding.e6
        e7 = binding.e7
        e8 = binding.e8
        f1 = binding.f1
        f2 = binding.f2
        f3 = binding.f3
        f4 = binding.f4
        f5 = binding.f5
        f6 = binding.f6
        f7 = binding.f7
        f8 = binding.f8
        g1 = binding.g1
        g2 = binding.g2
        g3 = binding.g3
        g4 = binding.g4
        g5 = binding.g5
        g6 = binding.g6
        g7 = binding.g7
        g8 = binding.g8
        h1 = binding.h1
        h2 = binding.h2
        h3 = binding.h3
        h4 = binding.h4
        h5 = binding.h5
        h6 = binding.h6
        h7 = binding.h7
        h8 = binding.h8
        firestore = FirebaseFirestore.getInstance()
        user = FirebaseAuth.getInstance().currentUser!!.uid
        resign = binding.resign
        draw = binding.draw

        firestore.collection("Users").document(user).update("currentGame", args.gameId)

        firestore.collection("Users").document(user).get().addOnSuccessListener {
            turn = if (it.getLong("side") == 0L) {
                ""
            } else {
                user
            }
        }

        resign.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Are you sure about resign?").setCancelable(true)
                .setPositiveButton("Yes") {_, _ ->
                    firestore.collection("Games").document(args.gameId).update("winner", 1)
                        .addOnSuccessListener {
                            val data = HashMap<String, Any?>()
                            data["currentGame"] = null
                            data["opponent"] = null
                            data["side"] = null
                            firestore.collection("Users").document(user).update(data)
                        }
                } .setNegativeButton("No") {p0, _ ->
                    p0.cancel()
                }

            builder.create().show()
        }

        a1.setOnClickListener {
            click(a1, a1.tag.toString().toInt(), Squares.rookAll)
        }

        a2.setOnClickListener {
            click(a2, a2.tag.toString().toInt(), Squares.pawnAll)
        }

        a3.setOnClickListener {
            click(a3, a3.tag.toString().toInt(), listOf())
        }

        a4.setOnClickListener {
            click(a4, a4.tag.toString().toInt(), listOf())
        }

        a5.setOnClickListener {
            click(a5, a5.tag.toString().toInt(), listOf())
        }

        a6.setOnClickListener {
            click(
                a6, a6.tag.toString().toInt(), listOf())
        }

        a7.setOnClickListener {
            click(a7, a7.tag.toString().toInt(), listOf())
        }

        a8.setOnClickListener {
            click(a8, a8.tag.toString().toInt(), listOf())
        }

        b1.setOnClickListener {
            click(b1, b1.tag.toString().toInt(), Squares.knightAll)
        }

        b2.setOnClickListener {
            click(b2, b2.tag.toString().toInt(), Squares.pawnAll)
        }

        b3.setOnClickListener {
            click(b3, b3.tag.toString().toInt(), listOf())
        }

        b4.setOnClickListener {
            click(b4, b4.tag.toString().toInt(), listOf())
        }

        b5.setOnClickListener {
            click(b5, b5.tag.toString().toInt(), listOf())
        }

        b6.setOnClickListener {
            click(b6, b6.tag.toString().toInt(), listOf())
        }

        b7.setOnClickListener {
            click(b7, b7.tag.toString().toInt(), listOf())
        }

        b8.setOnClickListener {
            click(
                b8, b8.tag.toString().toInt(), listOf())
        }

        c1.setOnClickListener {
            click(c1, c1.tag.toString().toInt(), Squares.bishopAll)
        }

        c2.setOnClickListener {
            click(c2, c2.tag.toString().toInt(), Squares.pawnAll)
        }

        c3.setOnClickListener {
            click(c3, c3.tag.toString().toInt(), listOf())
        }

        c4.setOnClickListener {
            click(c4, c4.tag.toString().toInt(), listOf())
        }

        c5.setOnClickListener {
            click(c5, c5.tag.toString().toInt(), listOf())
        }

        c6.setOnClickListener {
            click(c6, c6.tag.toString().toInt(), listOf())
        }

        c7.setOnClickListener {
            click(c7, c7.tag.toString().toInt(), listOf())
        }

        c8.setOnClickListener {
            click(c8, c8.tag.toString().toInt(), listOf())
        }

        d1.setOnClickListener {
            click(d1, d1.tag.toString().toInt(), Squares.queenAll)
        }

        d2.setOnClickListener {
            click(d2, d2.tag.toString().toInt(), Squares.pawnAll)
        }

        d3.setOnClickListener {
            click(d3, d3.tag.toString().toInt(), listOf())
        }

        d4.setOnClickListener {
            click(d4, d4.tag.toString().toInt(), listOf())
        }

        d5.setOnClickListener {
            click(d5, d5.tag.toString().toInt(), listOf())
        }

        d6.setOnClickListener {
            click(d6, d6.tag.toString().toInt(), listOf())
        }

        d7.setOnClickListener {
            click(d7, d7.tag.toString().toInt(), listOf())
        }

        d8.setOnClickListener {
            click(d8, d8.tag.toString().toInt(), listOf())
        }

        e1.setOnClickListener {
            click(e1, e1.tag.toString().toInt(), Squares.kingAll)
        }

        e2.setOnClickListener {
            click(e2, e2.tag.toString().toInt(), Squares.pawnAll)
        }

        e3.setOnClickListener {
            click(e3, e3.tag.toString().toInt(), listOf())
        }

        e4.setOnClickListener {
            click(e4, e4.tag.toString().toInt(), listOf())
        }

        e5.setOnClickListener {
            click(e5, e5.tag.toString().toInt(), listOf())
        }

        e6.setOnClickListener {
            click(e6, e6.tag.toString().toInt(), listOf())
        }

        e7.setOnClickListener {
            click(e7, e7.tag.toString().toInt(), listOf())
        }

        e8.setOnClickListener {
            click(e8, e8.tag.toString().toInt(), listOf())
        }

        f1.setOnClickListener {
            click(f1, f1.tag.toString().toInt(), Squares.bishopAll)
        }

        f2.setOnClickListener {
            click(f2, f2.tag.toString().toInt(), Squares.pawnAll)
        }

        f3.setOnClickListener {
            click(f3, f3.tag.toString().toInt(), listOf())
        }

        f4.setOnClickListener {
            click(f4, f4.tag.toString().toInt(), listOf())
        }

        f5.setOnClickListener {
            click(f5, f5.tag.toString().toInt(), listOf())
        }

        f6.setOnClickListener {
            click(f6, f6.tag.toString().toInt(), listOf())
        }

        f7.setOnClickListener {
            click(f7, f7.tag.toString().toInt(), listOf())
        }

        f8.setOnClickListener {
            click(f8, f8.tag.toString().toInt(), listOf())
        }

        g1.setOnClickListener {
            click(g1, g1.tag.toString().toInt(), Squares.knightAll)
        }

        g2.setOnClickListener {
            click(g2, g2.tag.toString().toInt(), Squares.pawnAll)
        }

        g3.setOnClickListener {
            click(g3, g3.tag.toString().toInt(), listOf())
        }

        g4.setOnClickListener {
            click(g4, g4.tag.toString().toInt(), listOf())
        }

        g5.setOnClickListener {
            click(g5, g5.tag.toString().toInt(), listOf())
        }

        g6.setOnClickListener {
            click(g6, g6.tag.toString().toInt(), listOf())
        }

        g7.setOnClickListener {
            click(g7, g7.tag.toString().toInt(), listOf())
        }

        g8.setOnClickListener {
            click(g8, g8.tag.toString().toInt(), listOf())
        }

        h1.setOnClickListener {
            click(h1, h1.tag.toString().toInt(), Squares.rookAll)
        }

        h2.setOnClickListener {
            click(h2, h2.tag.toString().toInt(), Squares.pawnAll)
        }

        h3.setOnClickListener {
            click(h3, h3.tag.toString().toInt(), listOf())
        }

        h4.setOnClickListener {
            click(h4, h4.tag.toString().toInt(), listOf())
        }

        h5.setOnClickListener {
            click(h5, h5.tag.toString().toInt(), listOf())
        }

        h6.setOnClickListener {
            click(h6, h6.tag.toString().toInt(), listOf())
        }

        h7.setOnClickListener {
            click(h7, h7.tag.toString().toInt(), listOf())
        }

        h8.setOnClickListener {
            click(h8, h8.tag.toString().toInt(), listOf())
        }

        firestore.collection("Games").document(args.gameId).addSnapshotListener {snapshot, e ->
            if (snapshot!!.getString("turn") != user) {
                val blackPieceData = snapshot.get("blackPieces") as MutableList<*>
                val blackPieceInt = ArrayList<Int?>()
                val whitePiecesInt = ArrayList<Int?>()
                for (i in blackPieceData) {
                    if (i != null) {
                        blackPieceInt.add((i as Long).toInt())
                    } else {
                        blackPieceInt.add(i)
                    }
                }

                for (i in 63 downTo 0) {
                    if (blackPieceData[i] != null) {
                        whitePiecesInt.add((blackPieceData[i] as Long).toInt())
                    } else {
                        whitePiecesInt.add(null)
                    }
                }

                Squares.pieceListBlack = blackPieceInt
                Squares.pieceList = whitePiecesInt

                for (i in 0 until  Squares.pieceListBlack.size) {
                    if (Squares.pieceListBlack[i] != null) {
                        Squares.setClick(binding)[63-i].setImageResource(Squares.pieceListBlack[i]!!)
                    } else {
                        Squares.setClick(binding)[63-i].setImageResource(0)
                    }
                }

                if (snapshot.getString("turn") != null) {
                    turn = snapshot.getString("turn")!!
                }

                if (snapshot.getLong("winner") == 0L) {
                    val builder = AlertDialog.Builder(requireContext())
                    val view = layoutInflater.inflate(R.layout.win_layout, null)
                    builder.setView(view)
                    builder.setCancelable(true).setPositiveButton("cancel") {p0, _ ->
                        p0.cancel()
                    }
                    builder.create().show()
                    val data = HashMap<String, Any?>()
                    data["currentGame"] = null
                    data["opponent"] = null
                    data["side"] = null
                    firestore.collection("Users").document(user).update(data)
                } else if (snapshot.getLong("winner") == 1L) {
                    val builder = AlertDialog.Builder(requireContext())
                    val view = layoutInflater.inflate(R.layout.win_layout, null)
                    WinLayoutBinding.bind(view).text.text = "You Lose"
                    builder.setView(view)
                    builder.setCancelable(true).setPositiveButton("cancel") {p0, _ ->
                        p0.cancel()
                    }
                    builder.create().show()
                    val data = HashMap<String, Any?>()
                    data["currentGame"] = null
                    data["opponent"] = null
                    data["side"] = null
                    firestore.collection("Users").document(user).update(data)
                }
            }
        }

        return binding.root
    }

    fun click(square: ImageView, tag: Int, steps: List<Int>) {
        if (Squares.pieceList[tag - 1] in Squares.whitePieces) {
            if (selectedTag != null) {
                selectedSquare!!.setBackgroundResource(Squares.squareList[selectedTag!!-1])
            }
            if (Squares.squareList[tag - 1] == R.drawable.square_brown_dark_svg) {
                square.setBackgroundResource(R.drawable.square_selected_dark_svg)
            } else {
                square.setBackgroundResource(R.drawable.square_selected_light_svg)
            }
            selectedTag = tag
            selectedSquare = square
            selectedSteps = steps
        } else {
            if (selectedTag != null) {
                val distance = selectedTag!!.toInt() - tag
                if (checker(Squares.getDirection(tag, selectedTag!!, Squares.pieceList[selectedTag!!-1]!!),
                        selectedTag!!, tag, distance) || Squares.pieceList[selectedTag!!-1] ==
                    R.drawable.w_knight_svg_noshadow) {
                    if (Squares.pieceList[selectedTag!!-1] == R.drawable.w_pawn_svg_noshadow) {
                        if (distance == -7 || distance == -9) {
                            if (Squares.pieceList[tag-1] in Squares.blackPieces) {
                                move(tag, Squares.pieceList[selectedTag!!.toInt()-1]!!, selectedSteps,
                                    square, distance)
                            }
                        }
                        else {
                            move(tag, Squares.pieceList[selectedTag!!.toInt()-1]!!, selectedSteps,
                                square, distance)
                        }
                    }

                    else {
                        move(tag, Squares.pieceList[selectedTag!!.toInt()-1]!!, selectedSteps,
                            square, distance)
                    }
                }
                else {
                    selectedSquare!!.setBackgroundResource(Squares.squareList[selectedTag!!.toInt() - 1])
                }
            }
        }
    }

    fun move(tag: Int, piece: Int, steps: List<Int>, square: ImageView, distance: Int) {
        if (Squares.pieceList[tag-1] !in Squares.whitePieces) {
            if (distance in steps && turn != user && !kingCheck(tag, selectedTag!!, piece)) {
                selectedSquare!!.setImageResource(0)
                square.setImageResource(piece)
                Squares.pieceList[selectedTag!!-1] = null
                Squares.pieceList[tag - 1] = piece

                val data = HashMap<String, Any>()
                data["whitePieces"] = Squares.pieceList
                data["turn"] = user
                firestore.collection("Games").document(args.gameId).update(data)

                turn = user

                val clickPrevious = Squares.setClick(binding)[selectedTag!!-1]
                val clickNext = Squares.setClick(binding)[tag-1]

                clickNext.setOnClickListener {
                    click(clickNext, clickNext.tag.toString().toInt(), steps)
                }

                clickPrevious.setOnClickListener {
                    click(clickPrevious, clickPrevious.tag.toString().toInt(), listOf())
                }
            }
            selectedSquare!!.setBackgroundResource(Squares.squareList[selectedTag!!.toInt() - 1])
            selectedTag = null
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
                        Squares.pieceListBlack[selectedTag-1] == R.drawable.b_pawn_svg_noshadow) && squareInDistance != 0) {
                val pawnAttack = tag - selectedTag == 7 || tag - selectedTag == 9

                if (Squares.pieceList[squareInDistance-1] in Squares.allPieces) {
                    result = false
                }

                else if (Squares.pieceList[selectedTag-1] == R.drawable.w_pawn_svg_noshadow &&
                    selectedTag.toDouble() / 8 > 2 && Squares.pieceList[tag-1] != null) {
                    result = false
                }

                else if (Squares.pieceList[selectedTag-1] == R.drawable.w_pawn_svg_noshadow && pawnAttack) {
                    result = false
                }
            }
            else {
                break
            }
        }
        return result
    }

    fun kingCheck(tag: Int, selectedTag: Int, piece: Int): Boolean {
        var result = false
        val list = mutableListOf<Int?>()
        list.addAll(Squares.pieceList)

        list[selectedTag-1] = null
        list[tag-1] = piece

        for (i in 0 until list.size) {
            if (list[i] != null && list[i] in Squares.blackPieces) {
                for (j in Squares.getMoves(list[i]!!)) {
                    for (t in j) {
                        val distance = i - t
                        if (distance in 1..63) {
                            if (list[distance] != null) {
                                if (list[distance] == R.drawable.w_king_svg_noshadow) {
                                    result = true
                                }
                                else {
                                    if (list[i] != R.drawable.b_knight_svg_noshadow) {
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Log.e("hello", result.toString())
        return result
    }
}