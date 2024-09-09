package com.route.pokemonapp

import androidx.compose.ui.graphics.Color

data class PokemonCard(
    val name: String,
    val type: String,
    val attack: Int,
    val defense: Int,
    val imageResId: Int
)
