package com.itzik.business_logic_layer.networkRequest

interface ResponseEvent {
    fun <T> onResponse(response: T, codeStatus: Int)
}