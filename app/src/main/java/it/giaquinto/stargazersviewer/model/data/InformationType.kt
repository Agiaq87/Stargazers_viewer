package it.giaquinto.stargazersviewer.model.data

import android.graphics.Color

enum class InformationType(val color: Int) {
    INFO(Color.BLUE),
    WARNING(Color.MAGENTA),
    ERROR(Color.RED);
}