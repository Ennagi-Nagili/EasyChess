package com.annaginagili.easychess

import android.util.Log
import android.widget.ImageView
import com.annaginagili.easychess.databinding.ActivityBlackGameBinding
import com.annaginagili.easychess.databinding.ActivityWhiteGameBinding
import kotlin.math.ceil

object Pieces {
    var pieceList = WhitePieces.pieceList

    var pieceListBlack = BlackPieces.pieceList

    val pieceListD = WhitePieces.pieceListD

    val pieceListBlackD = BlackPieces.pieceListD

    val squareList = Squares.squareList

    fun setClick(binding: ActivityWhiteGameBinding): List<ImageView> {
        return WhiteClick.setClick(binding)
    }

    fun setClick(binding: ActivityBlackGameBinding): List<ImageView> {
        return BlackClick.setClick(binding)
    }

    val whitePieces = listOf(R.drawable.w_rook_svg_noshadow, R.drawable.w_knight_svg_noshadow,
        R.drawable.w_bishop_svg_noshadow, R.drawable.w_queen_svg_noshadow,
        R.drawable.w_king_svg_noshadow, R.drawable.w_pawn_svg_noshadow)
    val blackPieces = listOf(R.drawable.b_rook_svg_noshadow, R.drawable.b_knight_svg_noshadow,
        R.drawable.b_bishop_svg_noshadow, R.drawable.b_queen_svg_noshadow,
        R.drawable.b_king_svg_noshadow, R.drawable.b_pawn_svg_noshadow)
    val allPieces = listOf(R.drawable.w_rook_svg_noshadow, R.drawable.w_knight_svg_noshadow,
        R.drawable.w_bishop_svg_noshadow, R.drawable.w_queen_svg_noshadow,
        R.drawable.w_king_svg_noshadow, R.drawable.w_pawn_svg_noshadow, R.drawable.b_rook_svg_noshadow,
        R.drawable.b_knight_svg_noshadow, R.drawable.b_bishop_svg_noshadow,
        R.drawable.b_queen_svg_noshadow, R.drawable.b_king_svg_noshadow, R.drawable.b_pawn_svg_noshadow)

    private val bishopTopRight = listOf(-9, -18, -27, -36, -45, -54, -63)
    private val bishopTopLeft = listOf(-7, -14, -21, -28, -35, -42, -49)
    private val bishopBottomLeft = listOf(9, 18, 27, 36, 45, 54, 63)
    private val bishopBottomRight = listOf(7, 14, 21, 28, 35, 42, 49)
    private val rookTop = listOf(-8, -16, -24, -32, -40, -48, -56)
    private val rookBottom = listOf(8, 16, 24, 32, 40, 48, 56)
    private val rookLeft = listOf(1, 2, 3, 4, 5, 6, 7)
    private val rookRight = listOf(-1, -2, -3, -4, -5, -6, -7)
    private val kingLeft = listOf(1)
    private val kingRight = listOf(-1)
    private val kingTop = listOf(-8)
    private val kingBottom = listOf(8)
    private val kingTopLeft = listOf(-7)
    private val kingTopRight = listOf(-9)
    private val kingBottomLeft = listOf(9)
    private val kingBottomRight = listOf(7)
    private val pawn = listOf(-8, -7, -9)
    val pawnAttack = listOf(-7, -9)
    val rookAll = listOf(-8, -16, -24, -32, -40, -48, -56, 8, 16,
        24, 32, 40, 48, 56, -1, -2, -3, -4, -5, -6, -7, 1, 2, 3, 4, 5, 6, 7)
    val knightAll = listOf(-15, -17, -6, -10, 15, 17, 6, 10)
    val pawnAll = listOf(-8, -16, -7, -9)
    val bishopAll = listOf(-9, -18, -27, -36, -45, -54, -63, 9, 18,
        27, 36, 45, 54, 63, -7, -14, -21, -28, -35, -42, -49, 7, 14, 21, 28, 35, 42, 49)
    val queenAll = listOf(-9, -18, -27, -36, -45, -54, -63, 9, 18,
        27, 36, 45, 54, 63, -7, -14, -21, -28, -35, -42, -49, 7, 14, 21, 28, 35, 42, 49, -8,
        -16, -24, -32, -40, -48, -56, 8, 16, 24, 32, 40, 48, 56, -1, -2, -3, -4, -5, -6, -7,
        1, 2, 3, 4, 5, 6, 7)
    val kingAll = listOf(-7, -8, -9, -1, 7, 8, 9, 1)

    fun getDirection(tag: Int, selectedTag: Int, piece: Int): List<Int> {
        when (piece) {
            R.drawable.w_bishop_svg_noshadow, R.drawable.b_bishop_svg_noshadow -> {
                return if ((tag - selectedTag) % 9 == 0) {
                    if (tag - selectedTag > 0) {
                        bishopTopRight
                    }
                    else {
                        bishopBottomLeft
                    }
                } else {
                    if (tag - selectedTag > 0) {
                        bishopTopLeft
                    } else {
                        bishopBottomRight
                    }
                }
            }
            R.drawable.w_rook_svg_noshadow, R.drawable.b_rook_svg_noshadow -> {
                return if ((tag - selectedTag) % 8 == 0) {
                    if (tag - selectedTag > 0) {
                        rookTop
                    } else {
                        rookBottom
                    }
                } else {
                    if (tag - selectedTag > 0) {
                        rookRight
                    } else {
                        rookLeft
                    }
                }
            }

            R.drawable.w_queen_svg_noshadow, R.drawable.b_queen_svg_noshadow -> {
                return if (-(tag - selectedTag) in rookTop) {
                    rookTop
                } else if (-(tag - selectedTag) in rookBottom) {
                    rookBottom
                } else if (-(tag - selectedTag) in rookRight && ceil(tag.toDouble() / 8) == ceil(selectedTag.toDouble() /8)) {
                    rookRight
                } else if (-(tag - selectedTag) in rookLeft) {
                    rookLeft
                } else if (-(tag - selectedTag) in bishopTopRight) {
                    bishopTopRight
                } else if (-(tag - selectedTag) in bishopTopLeft) {
                    bishopTopLeft
                } else if (-(tag - selectedTag) in bishopBottomRight) {
                    bishopBottomRight
                } else if (-(tag - selectedTag) in bishopBottomLeft) {
                    bishopBottomLeft
                }
                else {
                    bishopBottomLeft
                }
            }

            R.drawable.b_king_svg_noshadow, R.drawable.w_king_svg_noshadow ->
                return if (-(tag - selectedTag) in kingTop) {
                    kingTop
                } else if (-(tag - selectedTag) in kingBottom) {
                    kingBottom
                } else if (-(tag - selectedTag) in kingLeft) {
                    kingLeft
                } else if (-(tag - selectedTag) in kingRight) {
                    kingRight
                } else if (-(tag - selectedTag) in kingTopLeft) {
                    kingTopLeft
                } else if (-(tag - selectedTag) in kingTopRight) {
                    kingTopRight
                } else if (-(tag - selectedTag) in kingBottomLeft) {
                    kingBottomLeft
                } else {
                    kingBottomRight
                }

            R.drawable.b_pawn_svg_noshadow, R.drawable.w_pawn_svg_noshadow ->
                return if (-(tag - selectedTag) in pawnAttack) {
                    Log.e("hello", "byyyyy $tag $selectedTag")
                    pawnAttack
                } else {
                    if ((piece == R.drawable.b_pawn_svg_noshadow || piece == R.drawable.b_pawn_svg_noshadow)
                        && selectedTag.toDouble() / 8 <= 2) {
                        pawnAll
                    } else {
                        pawn
                    }
                }

            else -> {
                return  pawn
            }
        }
    }

    fun getMoves(piece: Int): List<List<Int>> {
        val rookSteps = listOf(rookTop, rookLeft, rookRight, rookBottom)
        val bishopSteps = listOf(bishopBottomLeft, bishopBottomRight, bishopTopLeft, bishopTopRight)
        val queenSteps = listOf(rookTop, rookLeft, rookRight, rookBottom, bishopBottomLeft,
            bishopBottomRight, bishopTopLeft, bishopTopRight)
        val kingSteps = listOf(kingBottom, kingTop, kingTopLeft, kingRight, kingTopLeft,
            kingBottomLeft, kingTopRight, kingBottomRight)
        val pawnSteps = listOf(pawnAttack)
        val knightSteps = listOf(knightAll)

        return when(piece) {
            R.drawable.w_rook_svg_noshadow, R.drawable.b_rook_svg_noshadow -> rookSteps
            R.drawable.w_knight_svg_noshadow, R.drawable.b_knight_svg_noshadow -> knightSteps
            R.drawable.w_bishop_svg_noshadow, R.drawable.b_bishop_svg_noshadow -> bishopSteps
            R.drawable.w_queen_svg_noshadow, R.drawable.b_queen_svg_noshadow -> queenSteps
            R.drawable.w_king_svg_noshadow, R.drawable.b_king_svg_noshadow -> kingSteps
            else -> pawnSteps
        }
    }

    fun getSteps(piece: Int): List<Int> {
        return when(piece) {
            R.drawable.w_rook_svg_noshadow, R.drawable.b_rook_svg_noshadow -> rookAll
            R.drawable.w_knight_svg_noshadow, R.drawable.b_knight_svg_noshadow -> knightAll
            R.drawable.w_bishop_svg_noshadow, R.drawable.b_bishop_svg_noshadow -> bishopAll
            R.drawable.w_queen_svg_noshadow, R.drawable.b_queen_svg_noshadow -> queenAll
            R.drawable.w_king_svg_noshadow, R.drawable.b_king_svg_noshadow -> kingAll
            else -> pawnAll
        }
    }
}