package com.uce.edu.logic.list

import com.uce.edu.data.entity.LoginUser
import com.uce.edu.logic.data.MarvelChars

class ListItems {
fun returnItems():List<LoginUser>{
    var items= listOf<LoginUser>(
        LoginUser("1","1"),
        LoginUser("2","2"),
        LoginUser("3","1"),
        LoginUser("4","1"),
        LoginUser("5","1")
    )
    return items
}

    fun returnMarvelChars():List<MarvelChars>{
        val items = listOf(
            MarvelChars(
                1,
                "Wolverine",
                "Wolverine",
                "https://comicvine.gamespot.com/a/uploads/scale_small/5/57023/7469590-wolverinerb.jpg"
            ),
            MarvelChars(
                2,
                "Spiderman",
                "Marvel Tales",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8126579-amazing_spider-man_vol_5_54_stormbreakers_variant_textless.jpg"
            ),
            MarvelChars(
                3,"Ironman","Iron Man","https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8654427-ezgif-1-2f113089e4.jpg"
            ),
            MarvelChars(
                4,"The Rock","Fantastic Four","https://comicvine.gamespot.com/a/uploads/scale_small/11141/111413247/7267709-e47c719d-a99f-46ef-9379-269760c8b548_rw_1200.jpg"
            ),
            MarvelChars(
                5,"Invisible woman","Fantastic Four","https://comicvine.gamespot.com/a/uploads/scale_small/11141/111413247/7267710-4df69cb3-7b54-480e-89e5-2c7e68c52b1b_rw_1200.jpg"
            )
        )
        return items
    }
}