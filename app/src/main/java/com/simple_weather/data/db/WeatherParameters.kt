package com.simple_weather.data.db


interface WeatherParameters {
    val name: String
    val sys_sunrise:Long
    val sys_sunset:Long
    val dt: Long
    val main_feelsLike:Double
    val main_humidity:Double
    val main_pressure:Double
    val main_temp:Double
    val wind_deg: Int
    val wind_speed:Double
    val weather: String
    val visibility: Int

}











//    @ColumnInfo(name = "base")
//    val base: String,
//    //    @ColumnInfo(name ="clouds_")
////    val clouds: Clouds,
//    @ColumnInfo(name ="cod")
//    val cod: Int,
//
//    //    @ColumnInfo(name ="coord")
////    val coord: Coord,
//    @ColumnInfo(name ="dt")
//    val dt: Int,
//    @ColumnInfo(name ="id")
//    val id: Int,
//
//    //    @ColumnInfo(name ="main")
////    val main: Main,
//    @ColumnInfo(name ="name")
//    val name: String,
//
//    //    @ColumnInfo(name ="sys")
////    val sys: Sys,
//    @ColumnInfo(name ="timezone")
//    val timezone: Int,
//    @ColumnInfo(name ="visibility")
//    val visibility: Int,
//    @ColumnInfo(name ="weather")
//    val weather: List<Weather>,
////
////    @ColumnInfo(name ="wind")
////    val wind: Wind
//

