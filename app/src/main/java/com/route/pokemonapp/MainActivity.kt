package com.route.pokemonapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val pokemons = readPokemonData(this)
            PokemonCardList(pokemons)
            }
        }
    }
@Composable
fun PokemonCardList(pokemonList: List<PokemonCard>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pokemonList.size) { index ->
            PokemonCard(pokemon = pokemonList[index])
        }
    }
}

@Composable
fun PokemonCard(pokemon: PokemonCard) {
    val backgroundColor = when (pokemon.type.lowercase()) {
        "fire" -> Color(0xffe47678)
        "water" -> Color(0xff5aaad4)
        "grass" -> Color(0xff64caba)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp)
        ) {
            Column {
                Text(
                    text = pokemon.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))
               Row {
                   Surface(
                       modifier = Modifier
                           .padding(5.dp),
                       shape = RoundedCornerShape(10.dp),
                       color = when (pokemon.type.lowercase()) {
                           "fire" -> Color(0xffe78d2d)
                           "water" -> Color(0xff456dbb)
                           "grass" -> Color(0xff8dce6e)
                           else -> Color.LightGray
                       }
                   ) {
                       Column (modifier = Modifier
                           .padding(3.dp)){


                           Text(
                               text = pokemon.type,
                               fontSize = 20.sp,
                               color = Color.White,

                               )
                       }
                   }
                   Spacer(modifier = Modifier.width(10.dp))
                   Column {

                       Row {
                           Text(
                               text = "Attack: ",
                               fontSize = 16.sp,
                               color = Color.White
                           )
                           Text(
                               text = pokemon.attack.toString(),
                               fontSize = 16.sp,
                               color = Color.Black
                           )
                       }
                       Spacer(modifier = Modifier.height(10.dp))
                       Row {
                           Text(
                               text = "Defense: ",
                               fontSize = 16.sp,
                               color = Color.White
                           )
                           Text(
                               text = pokemon.defense.toString(),
                               fontSize = 16.sp,
                               color = Color.Black
                           )
                       }
                   }
               }

            }
            Spacer(modifier = Modifier.width(80.dp))
            Image(
                painter = painterResource(id = pokemon.imageResId),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}


fun readPokemonData(context: Context): List<PokemonCard> {
    val pokemons = mutableListOf<PokemonCard>()

    context.assets.open("pokemon_data.csv").bufferedReader().use { reader ->
        val headerLine = reader.readLine()
        val headers = headerLine.split(",")

        reader.forEachLine { line ->
            val values = line.split(",")
            if (values.isNotEmpty()) {
                val name = values[0].trim()
                val type = values[1].trim()
                val attack = values[2].toIntOrNull() ?: 0
                val defense = values[3].toIntOrNull() ?: 0
                val imageResId = getPokemonImageResId(name, context)

                pokemons.add(PokemonCard(name, type, attack, defense, imageResId))
            }
        }
    }

    return pokemons
}

private fun getPokemonImageResId(name: String, context: Context): Int {
    val imageResIdMap = mapOf(
        "Blastoise" to R.drawable.blastoise,
        "Bulbasaur" to R.drawable.bulbasaur,
        "Charizard" to R.drawable.charizard,
        "Charmander" to R.drawable.charmander,
        "Charmeleon" to R.drawable.charmeleon,
        "Ivysaur" to R.drawable.ivysaur,
        "Squirtle" to R.drawable.squirtle,
        "Venusaur" to R.drawable.venusaur,
        "Wartortle" to R.drawable.wartortle,

    )

    return imageResIdMap[name] ?: R.drawable.default_image
}


