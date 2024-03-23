package com.example.notehub.data.repository

import com.example.notehub.data.data_source.GoogleDataSource
import javax.inject.Inject

class MainRepository @Inject constructor(
    val googleDataSource: GoogleDataSource
) {

}