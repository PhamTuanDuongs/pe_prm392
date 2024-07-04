package com.example.pe_prm392;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VideoActivity extends AppCompatActivity {
    VideoView videoView;
    Button btnFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = (VideoView) findViewById(R.id.vv_video);
        btnFind = (Button) findViewById(R.id.btn_find);
        ActivityResultLauncher<String> fileChooser = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri o) {
                        videoView.setVideoURI(o);
                        videoView.start();
                    }
                }
        );

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileChooser.launch("video/*");
            }
        });

        MediaController mediaController = new MediaController(this);

//        videoView.setVideoPath("https://web.law.duke.edu/cspd/contest/videos/Framed-Contest_Documentaries-and-You.mp4");
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);


    }
}