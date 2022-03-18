package com.ak.employeeapp.tokentask

data class IdentityToken(
    val username: String,
    val userGuid: String,
    val accountId: Long,
    val operator: String,
    val operatorGrp: String,
    var expiryMs: Long,
    val bu: String? = null,
    val activeProfileName: String? = null,
    val activeProfileGuid: String? = null,
    val isSecondaryProduct: Boolean?,
    val productKey: String? = null,
)