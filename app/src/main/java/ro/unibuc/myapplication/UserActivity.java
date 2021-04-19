package ro.unibuc.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import ro.unibuc.myapplication.Fragments.QRScanFragment;

public class UserActivity extends AppCompatActivity {
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
    }

    private void startCamera() {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.UserFragment, QRScanFragment.class, null)
                .addToBackStack(null);

        fragmentTransaction.commit();
    }

}
