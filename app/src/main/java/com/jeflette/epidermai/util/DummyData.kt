package com.jeflette.epidermai.util

import com.jeflette.epidermai.R
import com.jeflette.epidermai.model.CommonItem
import com.jeflette.epidermai.model.Disease

fun provideDummyDisease(): List<Disease> {
    return listOf<Disease>(
        Disease(
            CommonItem(
                1, R.drawable.acne, "Acne"
            ), "Jerawat", listOf(
                CommonItem(
                    11, R.drawable.benzasil, "Benzasil"
                ), CommonItem(
                    12, R.drawable.lumiquin, "Lumiquin"
                ), CommonItem(
                    13, R.drawable.erysanbe, "Erysanbe"
                ), CommonItem(
                    14, R.drawable.clindamycin, "Clindamycin"
                )
            )
        ), Disease(
            CommonItem(
                1, R.drawable.acne, "Acne"
            ), "Jerawat", listOf(
                CommonItem(
                    11, R.drawable.benzasil, "Benzasil"
                ), CommonItem(
                    12, R.drawable.lumiquin, "Lumiquin"
                ), CommonItem(
                    13, R.drawable.erysanbe, "Erysanbe"
                ), CommonItem(
                    14, R.drawable.clindamycin, "Clindamycin"
                )
            )
        ), Disease(
            CommonItem(
                1, R.drawable.acne, "Acne"
            ), "Jerawat", listOf(
                CommonItem(
                    11, R.drawable.benzasil, "Benzasil"
                ), CommonItem(
                    12, R.drawable.lumiquin, "Lumiquin"
                ), CommonItem(
                    13, R.drawable.erysanbe, "Erysanbe"
                ), CommonItem(
                    14, R.drawable.clindamycin, "Clindamycin"
                )
            )
        ), Disease(
            CommonItem(
                1, R.drawable.acne, "Acne"
            ), "Jerawat", listOf(
                CommonItem(
                    11, R.drawable.benzasil, "Benzasil"
                ), CommonItem(
                    12, R.drawable.lumiquin, "Lumiquin"
                ), CommonItem(
                    13, R.drawable.erysanbe, "Erysanbe"
                ), CommonItem(
                    14, R.drawable.clindamycin, "Clindamycin"
                )
            )
        )
    )
}


fun provideDummyMedicine(): List<CommonItem> {
    return listOf(
        CommonItem(
            11, R.drawable.benzasil, "Benzasil"
        ), CommonItem(
            12, R.drawable.lumiquin, "Lumiquin"
        ), CommonItem(
            13, R.drawable.erysanbe, "Erysanbe"
        ), CommonItem(
            14, R.drawable.clindamycin, "Clindamycin"
        )
    )
}