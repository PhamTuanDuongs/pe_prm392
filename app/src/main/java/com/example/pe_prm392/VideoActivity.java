package com.example.pe_prm392;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class VideoActivity extends AppCompatActivity {
    VideoView videoView;
    Button btnFind;

    boolean is_video_access_permitted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = (VideoView) findViewById(R.id.vv_video);
        btnFind = (Button) findViewById(R.id.btn_find);
        ActivityResultLauncher<String> fileChooser = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                videoView.setVideoURI(o);
                videoView.start();
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_video_access_permitted || ContextCompat.checkSelfPermission(VideoActivity.this, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED) {
                    fileChooser.launch("video/*");
                } else {
                    requestPermissionVideoAccess();
                }
            }
        });

        MediaController mediaController = new MediaController(this);

//        videoView.setVideoPath("https://web.law.duke.edu/cspd/contest/videos/Framed-Contest_Documentaries-and-You.mp4");
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }

    public void requestPermissionVideoAccess() {
        if (ContextCompat.checkSelfPermission(VideoActivity.this, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED) {
            is_video_access_permitted = true;
        } else {
            request_permission_launcher_video_access.launch(Manifest.permission.READ_MEDIA_VIDEO);
        }
    }

    private ActivityResultLauncher<String> request_permission_launcher_video_access = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            is_video_access_permitted = true;
        } else {
            is_video_access_permitted = false;
        }
    });
}