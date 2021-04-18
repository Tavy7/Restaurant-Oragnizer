package ro.unibuc.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import ro.unibuc.myapplication.Fragments.QRScanFragment;

public class UserActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    Button cameraQR;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        cameraQR = findViewById(R.id.qrcamera);
        cameraQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCamera();
            }
        });

        // Check if camera permision
        requestCamera();
    }

    private void requestCamera() {
        boolean cameraPermissionGiven =
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        if (cameraPermissionGiven) {
            startCamera();
        }

        else {
            // If we do not have camera permission, then we request it again
            ActivityCompat.requestPermissions(UserActivity.this,
                    new String[] { Manifest.permission.CAMERA }, PERMISSION_REQUEST_CAMERA);
        }
    }

    private void startCamera() {
        Toast.makeText(this, "started", Toast.LENGTH_SHORT).show();
        this.getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.UserFragment, QRScanFragment.class, null)
                .commit();

    }
}
