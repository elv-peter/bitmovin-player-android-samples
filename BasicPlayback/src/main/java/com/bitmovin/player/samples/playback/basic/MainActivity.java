/*
 * Bitmovin Player Android SDK
 * Copyright (C) 2017, Bitmovin GmbH, All Rights Reserved
 *
 * This source code and its use and distribution, is subject to the terms
 * and conditions of the applicable license agreement.
 */

package com.bitmovin.player.samples.playback.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bitmovin.player.BitmovinPlayer;
import com.bitmovin.player.BitmovinPlayerView;
import com.bitmovin.player.config.PlayerConfiguration;
import com.bitmovin.player.config.drm.DRMConfiguration;
import com.bitmovin.player.config.drm.WidevineConfiguration;
import com.bitmovin.player.config.media.SourceConfiguration;
import com.bitmovin.player.config.media.SourceItem;
import com.bitmovin.player.config.network.NetworkConfiguration;
import com.bitmovin.player.config.network.PreprocessHttpRequestCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private BitmovinPlayerView bitmovinPlayerView;
    private BitmovinPlayer bitmovinPlayer;
    private ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("ELV onCreate");

        setContentView(R.layout.activity_main);

        this.bitmovinPlayerView = (BitmovinPlayerView) this.findViewById(R.id.bitmovinPlayerView);
        this.bitmovinPlayer = this.bitmovinPlayerView.getPlayer();

        try {
            executor = Executors.newSingleThreadExecutor();
            this.initializePlayer();
        } catch (UnsupportedDrmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        System.out.println("ELV onStart");
        this.bitmovinPlayerView.onStart();
        super.onStart();
    }

    @Override
    protected void onResume() {
        System.out.println("ELV onResume");
        super.onResume();
        this.bitmovinPlayerView.onResume();
    }

    @Override
    protected void onPause() {
        System.out.println("ELV onPause");
        this.bitmovinPlayerView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        System.out.println("ELV onStop");
        this.bitmovinPlayerView.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        System.out.println("ELV onDestroy");
        this.bitmovinPlayerView.onDestroy();
        super.onDestroy();
    }

    protected void initializePlayer() throws UnsupportedDrmException {
        System.out.println("ELV initializePlayer");
        PlayerConfiguration playerConf = new PlayerConfiguration();

        // Only need NetworkConfiguration to inspect header
        NetworkConfiguration networkConf = new NetworkConfiguration();
        PreprocessHttpRequestCallback callback = (httpRequestType, httpRequest) -> {
            System.out.println("ELV preprocessHttpRequest");
            System.out.println("ELV Authorization: " + httpRequest.getHeaders().get("Authorization"));
            // Header seems to be set correctly already
            //return executor.submit(() -> {
            //    Map<String, String> headers = httpRequest.getHeaders();
            //    headers.put("Authorization", "Bearer eyJxc3BhY2VfaWQiOiJpc3BjMzZzM3V3WTl2b1R4NmdYY1hFTm40S2ZZMjlmQyIsInFsaWJfaWQiOiJpbGliNFU4NE5DVnZvVkg1MTI0OWRGRG9iajJaZlpySyIsImFkZHIiOiIweGMzNjBlNDhlZjQ0ODRjNjg3ZWFBNGVhNDg5MDFhQUQ0MWREQzA3RTAiLCJxaWQiOiJpcV9fY2hYZVVKbmt4eVI4NGZHdDlmbXIyakZueE1HIiwiZ3JhbnQiOiJyZWFkIiwidHhfcmVxdWlyZWQiOmZhbHNlLCJpYXQiOjE1NTk2ODMyOTksImV4cCI6MTU2MjI3NTI5OSwiYXV0aF9zaWciOiJFUzI1NktfTGZwbVpCZUZNUmlSZml4UGs3eDdIRTUya1FOUVdKcERRZkNDR0pleTZFelR5aGhZdmdKWjE0YW5uc3hBbmR3eVdQb01CUDlNS3l0NzdaUmU1WkFkQ0UzbkciLCJhZmdoX3BrIjoiIn0=.RVMyNTZLX0tERE1wQUc4ZFBudmFpTGU5QWRKRHVYdFdGc1ZNb1cxZll1REFTclREWm14dHdXU2k4OUx1Zkw0all5YWVOZnk4WXBzYldQaGloY2g1c25lRHlmTGhkYnRq");
            //    httpRequest.setHeaders(headers);
            //    return httpRequest;
            //});
            return null;
        };
        networkConf.setPreprocessHttpRequestCallback(callback);
        playerConf.setNetworkConfiguration(networkConf);

        this.bitmovinPlayer.setup(playerConf);

        WidevineConfiguration wvConf = (WidevineConfiguration) new DRMConfiguration.Builder()
                .uuid(WidevineConfiguration.UUID)
                .licenseUrl("http://10.0.2.2:6545/wv?qhash=hq__HuYk7dGGV5H5SkB7wUyBbKHnRTa7yev57hEy4SbaGtWyoMVjuiPBesQimCHt9RYEwpSkudDK4e")
                .putHttpHeader("Authorization", "Bearer eyJxc3BhY2VfaWQiOiJpc3BjMzZzM3V3WTl2b1R4NmdYY1hFTm40S2ZZMjlmQyIsInFsaWJfaWQiOiJpbGliNFU4NE5DVnZvVkg1MTI0OWRGRG9iajJaZlpySyIsImFkZHIiOiIweGMzNjBlNDhlZjQ0ODRjNjg3ZWFBNGVhNDg5MDFhQUQ0MWREQzA3RTAiLCJxaWQiOiJpcV9fY2hYZVVKbmt4eVI4NGZHdDlmbXIyakZueE1HIiwiZ3JhbnQiOiJyZWFkIiwidHhfcmVxdWlyZWQiOmZhbHNlLCJpYXQiOjE1NTk2ODMyOTksImV4cCI6MTU2MjI3NTI5OSwiYXV0aF9zaWciOiJFUzI1NktfTGZwbVpCZUZNUmlSZml4UGs3eDdIRTUya1FOUVdKcERRZkNDR0pleTZFelR5aGhZdmdKWjE0YW5uc3hBbmR3eVdQb01CUDlNS3l0NzdaUmU1WkFkQ0UzbkciLCJhZmdoX3BrIjoiIn0=.RVMyNTZLX0tERE1wQUc4ZFBudmFpTGU5QWRKRHVYdFdGc1ZNb1cxZll1REFTclREWm14dHdXU2k4OUx1Zkw0all5YWVOZnk4WXBzYldQaGloY2g1c25lRHlmTGhkYnRq")
                .build();
        SourceItem sourceItem = new SourceItem("http://10.0.2.2:8008/test/q/hq__HuYk7dGGV5H5SkB7wUyBbKHnRTa7yev57hEy4SbaGtWyoMVjuiPBesQimCHt9RYEwpSkudDK4e/avpipe/main/dash-widevine/dash.mpd");
        sourceItem.addDRMConfiguration(wvConf);
        SourceConfiguration sourceConfiguration = new SourceConfiguration();
        sourceConfiguration.addSourceItem(sourceItem);
        bitmovinPlayer.load(sourceConfiguration);
    }
}
