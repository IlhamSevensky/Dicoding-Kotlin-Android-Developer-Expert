package com.ilham.made.footballclub.repository

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.URL

class ApiRepository {
    fun doRequest(url: String): Deferred<String> = GlobalScope.async(IO) {
        URL(url).readText()
    }
}