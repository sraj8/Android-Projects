package com.uic.cs478.sylvesterraj.project3_a3;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebDisplayFragment extends Fragment{

    private WebView mWebView = null;
    private int mCurrIdx = -1;
    private int mWebDisplayArrLen;

    int getShownIndex() {
        return mCurrIdx;
    }

    // Show the URL string at position newIndex
    void showURLAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= mWebDisplayArrLen)
            return;
        mCurrIdx = newIndex;
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

        });
        mWebView.loadUrl(SanFranMainActivity.mPlacesURL[mCurrIdx]);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//Retaining the state of WebDisplayFragment.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout defined in web_fragment.xml
        // The last parameter is false because the returned view does not need to be attached to the container ViewGroup
        return inflater.inflate(R.layout.web_fragment,
                container, false);
    }

    // Set up some information about the  WebView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get the WebView in mWebView based on its id attactionURL.
        mWebView = (WebView)getView().findViewById(R.id.attactionURL);
        mWebDisplayArrLen = SanFranMainActivity.mPlacesURL.length;
    }
}
