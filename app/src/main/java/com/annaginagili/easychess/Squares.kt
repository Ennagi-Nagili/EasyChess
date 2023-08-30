package com.annaginagili.easychess

import android.util.Log
import android.widget.ImageView
import com.annaginagili.easychess.databinding.FragmentBlackGameBinding
import com.annaginagili.easychess.databinding.FragmentWhiteGameBinding
import kotlin.math.ceil

object Squares {
    var pieceList = mutableListOf(
        R.drawable.w_rook_svg_noshadow,
        R.drawable.w_knight_svg_noshadow,
        R.drawable.w_bishop_svg_noshadow,
        R.drawable.w_queen_svg_noshadow,
        R.drawable.w_king_svg_noshadow,
        R.drawable.w_bishop_svg_noshadow,
        R.drawable.w_knight_svg_noshadow,
        R.drawable.w_rook_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_rook_svg_noshadow,
        R.drawable.b_knight_svg_noshadow,
        R.drawable.b_bishop_svg_noshadow,
        R.drawable.b_queen_svg_noshadow,
        R.drawable.b_king_svg_noshadow,
        R.drawable.b_bishop_svg_noshadow,
        R.drawable.b_knight_svg_noshadow,
        R.drawable.b_rook_svg_noshadow,
    )

    var pieceListBlack = mutableListOf(
        R.drawable.b_rook_svg_noshadow,
        R.drawable.b_knight_svg_noshadow,
        R.drawable.b_bishop_svg_noshadow,
        R.drawable.b_king_svg_noshadow,
        R.drawable.b_queen_svg_noshadow,
        R.drawable.b_bishop_svg_noshadow,
        R.drawable.b_knight_svg_noshadow,
        R.drawable.b_rook_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        R.drawable.b_pawn_svg_noshadow,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_pawn_svg_noshadow,
        R.drawable.w_rook_svg_noshadow,
        R.drawable.w_knight_svg_noshadow,
        R.drawable.w_bishop_svg_noshadow,
        R.drawable.w_king_svg_noshadow,
        R.drawable.w_queen_svg_noshadow,
        R.drawable.w_bishop_svg_noshadow,
        R.drawable.w_knight_svg_noshadow,
        R.drawable.w_rook_svg_noshadow,
    )

    val squareList = listOf(
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg,
        R.drawable.square_brown_light_svg,
        R.drawable.square_brown_dark_svg
    )

    fun setClick(binding: FragmentWhiteGameBinding): List<ImageView> {
        return listOf(
            binding.a1,
            binding.b1,
            binding.c1,
            binding.d1,
            binding.e1,
            binding.f1,
            binding.g1,
            binding.h1,
            binding.a2,
            binding.b2,
            binding.c2,
            binding.d2,
            binding.e2,
            binding.f2,
            binding.g2,
            binding.h2,
            binding.a3,
            binding.b3,
            binding.c3,
            binding.d3,
            binding.e3,
            binding.f3,
            binding.g3,
            binding.h3,
            binding.a4,
            binding.b4,
            binding.c4,
            binding.d4,
            binding.e4,
            binding.f4,
            binding.g4,
            binding.h4,
            binding.a5,
            binding.b5,
            binding.c5,
            binding.d5,
            binding.e5,
            binding.f5,
            binding.g5,
            binding.h5,
            binding.a6,
            binding.b6,
            binding.c6,
            binding.d6,
            binding.e6,
            binding.f6,
            binding.g6,
            binding.h6,
            binding.a7,
            binding.b7,
            binding.c7,
            binding.d7,
            binding.e7,
            binding.f7,
            binding.g7,
            binding.h7,
            binding.a8,
            binding.b8,
            binding.c8,
            binding.d8,
            binding.e8,
            binding.f8,
            binding.g8,
            binding.h8,
        )
    }

    fun setClick(binding: FragmentBlackGameBinding): List<ImageView> {
        return listOf(
            binding.a1,
            binding.b1,
            binding.c1,
            binding.d1,
            binding.e1,
            binding.f1,
            binding.g1,
            binding.h1,
            binding.a2,
            binding.b2,
            binding.c2,
            binding.d2,
            binding.e2,
            binding.f2,
            binding.g2,
            binding.h2,
            binding.a3,
            binding.b3,
            binding.c3,
            binding.d3,
            binding.e3,
            binding.f3,
            binding.g3,
            binding.h3,
            binding.a4,
            binding.b4,
            binding.c4,
            binding.d4,
            binding.e4,
            binding.f4,
            binding.g4,
            binding.h4,
            binding.a5,
            binding.b5,
            binding.c5,
            binding.d5,
            binding.e5,
            binding.f5,
            binding.g5,
            binding.h5,
            binding.a6,
            binding.b6,
            binding.c6,
            binding.d6,
            binding.e6,
            binding.f6,
            binding.g6,
            binding.h6,
            binding.a7,
            binding.b7,
            binding.c7,
            binding.d7,
            binding.e7,
            binding.f7,
            binding.g7,
            binding.h7,
            binding.a8,
            binding.b8,
            binding.c8,
            binding.d8,
            binding.e8,
            binding.f8,
            binding.g8,
            binding.h8,
        )
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
}