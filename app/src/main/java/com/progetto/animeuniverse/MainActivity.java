package com.progetto.animeuniverse;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import java.util.Objects;

import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.VideoView;


public class MainActivity extends AppCompatActivity {
    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        videoBG = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"
                + getPackageName()
                + "/"
                + R.raw.sfondo);

        videoBG.setVideoURI(uri);
        videoBG.start();

        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                mMediaPlayer.setLooping(true);
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        videoBG.start();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
}