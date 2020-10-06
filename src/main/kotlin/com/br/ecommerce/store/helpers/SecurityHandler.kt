package com.br.ecommerce.store.helpers

import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object SecurityHandler {
    fun trustSelfSignedSSL() {
        try {
            val ctx: SSLContext = SSLContext.getInstance("TLS")
            val tm: X509TrustManager = object : X509TrustManager {
                override fun checkClientTrusted(xcs: Array<X509Certificate?>?, string: String?) {
                }

                override  fun checkServerTrusted(xcs: Array<X509Certificate?>?, string: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate>? {
                    return null
                }

                val acceptedIssuers: Array<Any?>?
                    get() = null
            }
            ctx.init(null, arrayOf<TrustManager>(tm), null)
            SSLContext.setDefault(ctx)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}