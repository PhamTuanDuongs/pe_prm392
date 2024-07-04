package com.example.pe_prm392;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class CaptureActivity extends AppCompatActivity {
    private Button btn;
    String[] required_permission = new String[]{
            android.Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.CAMERA
    };
    boolean is_storage_image_permitted = false;
    boolean is_camera_access_permitted = false;

    String TAG = "Permission";
    ImageView imageView;

    Uri uri_for_camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_capture);
        Init();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "This is my Toast message!",
//                        Toast.LENGTH_LONG).show();
                if(is_camera_access_permitted) {
                    openCamera();
                }else {
                    requestPermissionCameraAccess();
                }
            }
        });
    }

    private ActivityResultLauncher<Intent> launcher_for_camera =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        imageView.setImageURI(uri_for_camera);
                    }
                }
            });


    public void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "photo");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Captured");
        uri_for_camera = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri_for_camera);
        launcher_for_camera.launch(cameraIntent);
    }

    private void Init() {
        btn = findViewById(R.id.button);
        imageView = findViewById(R.id.photo);
    }

    public void requestPermissionStorageImages() {
        if (ContextCompat.checkSelfPermission(CaptureActivity.this, required_permission[0]) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, required_permission[0] + "Granted");
            is_storage_image_permitted = true;
            requestPermissionCameraAccess();
        } else {
            request_permission_launcher_storage_images.launch(required_permission[0]);
        }
    }


    public void requestPermissionCameraAccess() {
        if (ContextCompat.checkSelfPermission(CaptureActivity.this, required_permission[1]) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, required_permission[1] + "Granted");
            is_camera_access_permitted = true;
        } else {
            request_permission_launcher_camera_access.launch(required_permission[1]);

        }
    }

    private ActivityResultLauncher<String> request_permission_launcher_camera_access =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    isCranted -> {
                        if (isCranted) {
                            Log.d(TAG, required_permission[1] + " Granted");
                            is_camera_access_permitted = true;
                        } else {
                            Log.d(TAG, required_permission[1] + " Not Granted");
                            is_camera_access_permitted = false;
                        }

                    });

    private ActivityResultLauncher<String> request_permission_launcher_storage_images = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    Log.d(TAG, required_permission[0] + " Granted");
                    is_storage_image_permitted = true;
                } else {
                    Log.d(TAG, required_permission[0] + " Not Granted");
                    is_storage_image_permitted = false;
                }
                requestPermissionCameraAccess();
            });
}