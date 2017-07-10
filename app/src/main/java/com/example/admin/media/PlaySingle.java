package com.example.admin.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

public class PlaySingle extends Activity implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    private TextView songTitleLabel, songCurrentDurationLabel, songTotalDurationLabel;

    public static String[] listContent = {"Artist: Lebo Sekgobela \n  Title: lion of judah", "Artist: SINACH \n  Title:WAY MAKER)", "Artist: SINACH  \n  Title: I KNOW WHO I AM", "Artist: Hezekiah Walker \n  Title: Every Praise\"",
            "Artist: Tye Tribbett   \n  Title: What Can I Do", " Artist: Tye Tribbett  \n  Title: I Love You Forever ", "Artist: Christian. K \n  Title: I Win ", "Artist: CSO \n  Title: I'm Excellent",
            "Artist: Okmalumkoolkat \n  Title: Gqi", "Artist: SANDS \n  Title: TIGI "};
    public static MediaPlayer mp;
    SeekBar seekBar;

    ImageButton previous, backward, next, forward, play,btnRepeat,btnShuffle;
    ImageView disc, btnPlaylist;


    public static int[] resID = {R.raw.lebu, R.raw.way_maker, R.raw.sinach_iknoww, R.raw.hezekiah, R.raw.ican, R.raw.tye_i_love_u, R.raw.win, R.raw.cso, R.raw.gqi, R.raw.sands};
    private Handler mHandler = new Handler();

    private AudioManager audioManager;

    private Utilities utils;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds

    private boolean isShuffle = false;
    private boolean isRepeat = false;



    AudioManager.OnAudioFocusChangeListener mOnAudioFocusListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        //pause playback
                        mp.start();
                        mp.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        //resume playback
                        mp.pause();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }

                }
            };


    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {


            releaseMediaPlayer();


        }
    };

    private int p;
    int angle;
    //stop music
    public boolean isPlay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        Intent intent = getIntent();
        p = intent.getIntExtra("POSITION", 0);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //functionality imag
        backward = (ImageButton) findViewById(R.id.btnBackward);
        play = (ImageButton) findViewById(R.id.btnPlay);
        forward = (ImageButton) findViewById(R.id.btnForward);
        next = (ImageButton) findViewById(R.id.btnNext);
        previous = (ImageButton) findViewById(R.id.btnPrevious);
        btnPlaylist = (ImageView) findViewById(R.id.btnPlaylist);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        //moving disc
        disc = (ImageView) findViewById(R.id.imageView);

        seekBar = (SeekBar) findViewById(R.id.seekbar);

        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);

        songTitleLabel = (TextView) findViewById(R.id.songTitle);

        utils = new Utilities();


        mp = MediaPlayer.create(getApplicationContext(), R.raw.vusi);

        playSong(p);
         play.setImageDrawable(getResources().getDrawable(R.drawable.btn_pause));



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mp.isPlaying()) {
                    mp.pause();
                    play.setImageDrawable(getResources().getDrawable(R.drawable.btn_play));
                    mp.setOnCompletionListener(mCompletionListener);

                    Toast.makeText(PlaySingle.this, "pause", Toast.LENGTH_LONG).show();


                } else if (mp.isPlaying() != true) {
                    play.setImageDrawable(getResources().getDrawable(R.drawable.btn_pause));
                    playSong(p);
                    Toast.makeText(PlaySingle.this, "play", Toast.LENGTH_LONG).show();


                }


            }
        });
//

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (p != 0) {
                    play.setImageDrawable(getResources().getDrawable(R.drawable.btn_pause));
                    playSong(p--);
                } else {
                    play.setImageDrawable(getResources().getDrawable(R.drawable.btn_pause));
                    playSong(p);
                    Toast.makeText(PlaySingle.this, " Song", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(PlaySingle.this, "pre", Toast.LENGTH_LONG).show();

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (p < 10) {
                    play.setImageDrawable(getResources().getDrawable(R.drawable.btn_pause));

                    playSong(p++);


                } else {
                    play.setImageDrawable(getResources().getDrawable(R.drawable.btn_pause));
                    Toast.makeText(PlaySingle.this, "Last Song", Toast.LENGTH_LONG).show();

                    playSong(p);

                }



            }
        });
        btnPlaylist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
               startActivity(i);
            }
        });
//
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = mp.getCurrentPosition();
                // check if seekForward time is lesser than song duration
                if (currentPosition + seekForwardTime <= mp.getDuration()) {
                    // forward song
                    mp.seekTo(currentPosition + seekForwardTime);
                } else {
                    // forward to end position
                    mp.seekTo(mp.getDuration());
                }
            }
        });
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = mp.getCurrentPosition();
                // check if seekBackward time is greater than 0 sec
                if (currentPosition - seekBackwardTime >= 0) {
                    // forward song
                    mp.seekTo(currentPosition - seekBackwardTime);
                } else {
                    // backward to starting position
                    mp.seekTo(0);
                }
            }
        });
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRepeat){
                    isRepeat = false;

                    Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                }else{
                    // make repeat to true
                    isRepeat = true;
                    Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isShuffle = false;
                    btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                }
            }
        });
        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShuffle){
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                }else{
                    // make repeat to true
                    isShuffle= true;
                    Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isRepeat = false;
                    btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                }
            }
        });

    }

    public void playSong(int songIndex) {
//
//

        // mp.reset();// stops any current playing song
        try {

            int results = audioManager.requestAudioFocus(mOnAudioFocusListener,
                    //use the music stream
                    AudioManager.STREAM_MUSIC,
                    //request permanent focus
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            releaseMediaPlayer();
            if (results == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mp = MediaPlayer.create(getApplicationContext(), resID[songIndex]);// create's

                songTitleLabel.setText(listContent[songIndex]);
                mp.start(); // starting mediaplayer
               mp.setOnCompletionListener(mCompletionListener);


                seekBar.setProgress(0);
                seekBar.setMax(100);

                updateProgressBar();
                angle = 3600;
                RotateAnimation ra = new RotateAnimation(0, angle, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                ra.setFillAfter(true);
                ra.setDuration(100000);
                ra.setInterpolator(new AccelerateDecelerateInterpolator());
                disc.startAnimation(ra);
            }

        } catch (Exception e) {
            e.printStackTrace();
//
        }
    }


    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }


    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            try {
                long totalDuration = mp.getDuration();
                long currentDuration = mp.getCurrentPosition();

                // Displaying Total Duration time
                songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
                // Displaying time completed playing
                songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));

                // Updating progress bar
                int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
                Log.d("Progress", "" + progress);
                mHandler.removeCallbacks(mUpdateTimeTask);
                seekBar.setProgress(progress);


                // Running this thread after 100 milliseconds
                mHandler.postDelayed(this, 100);

            }catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    };


//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
//
    @Override
    public void onStopTrackingTouch(SeekBar sbar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mp.getDuration();
        int currentPosition = utils.progressToTimer(sbar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mp.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }
//
    @Override
    public void onCompletion(MediaPlayer arg0) {

        // check for repeat is ON or OFF
        if(isRepeat){
            // repeat is on play same song again
            playSong(p);
        } else if(isShuffle){
            // shuffle is on - play a random song
            Random rand = new Random();
            p = rand.nextInt((10 - 1) - 0 + 1);
            playSong(p);
        } else{
            // no repeat or shuffle ON - play next song
            if(p < (10 - 1)){
                playSong(p + 1);
                p = p + 1;
            }else{
                // play first song
                playSong(0);
                p = 0;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
        mp.release();

    }
//
    private void releaseMediaPlayer() {

        if (mp != null) {

            mp.release();


            mp = null;
        }
    }
}
