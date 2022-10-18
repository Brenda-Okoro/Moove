package com.carbon.core.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable

@Entity(tableName = "movies")
data class Movies(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    @Json(name = "release_date") val release_date: String,
    val title: String,
    @Json(name = "vote_average") val vote_average: Double,
): Serializable {
    val posterImageUrl: String
        get() = "https://image.tmdb.org/t/p/w500/$poster_path"
}

