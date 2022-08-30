package com.ej.culturalfestival.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.DownloadListener
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.ej.culturalfestival.R
import com.ej.culturalfestival.databinding.FragmentSearchBinding
import com.ej.culturalfestival.databinding.FragmentWebviewBinding


class WebviewFragment : Fragment() {

    lateinit var webviewFragmentBinding : FragmentWebviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        webviewFragmentBinding = FragmentWebviewBinding.inflate(LayoutInflater.from(container!!.context),container,false)
        val webview = webviewFragmentBinding.webview

        webview.webViewClient = WebViewClient()

        webview.webChromeClient = WebChromeClient()

        // 다운로드 설정
        //webview.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->  })

        webview.settings.loadWithOverviewMode = true
        webview.settings.useWideViewPort = true

        webview.settings.setSupportZoom(false)
        webview.settings.builtInZoomControls = false
        webview.settings.javaScriptEnabled = true
        webview.settings.javaScriptCanOpenWindowsAutomatically = true
        webview.settings.setSupportMultipleWindows(true)
        webview.settings.domStorageEnabled = true
        webview.loadUrl("https://www.youtube.com/")

        return webviewFragmentBinding.root
    }

    companion object {
        fun newInstance() : WebviewFragment{
          return WebviewFragment()
        }

    }
}