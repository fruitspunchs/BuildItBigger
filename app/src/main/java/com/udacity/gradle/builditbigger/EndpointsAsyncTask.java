package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.user.jokedisplay.JokeDisplayActivity;
import com.example.user.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi mMyApiService = null;
    private Context mContext;
    private ProgressBar mProgressBar;

    public EndpointsAsyncTask(ProgressBar progressBar) {
        mProgressBar = progressBar;
    }

    public EndpointsAsyncTask() {
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mProgressBar != null) mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Context... params) {
        if (mMyApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://joketeller-1104.appspot.com/_ah/api/");
            // end options for devappserver

            mMyApiService = builder.build();
        }

        mContext = params[0];

        try {
            return mMyApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (mProgressBar != null) mProgressBar.setVisibility(View.GONE);
        Intent intent = new Intent(mContext, JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.JOKE_KEY, result);
        mContext.startActivity(intent);
    }
}