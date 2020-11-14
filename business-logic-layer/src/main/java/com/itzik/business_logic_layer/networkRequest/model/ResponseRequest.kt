package com.itzik.business_logic_layer.networkRequest.model

import com.itzik.commons.datamodels.ResponseData


data class ResponseRequest(var data: ResponseData, var codeResponse: Int = -1)