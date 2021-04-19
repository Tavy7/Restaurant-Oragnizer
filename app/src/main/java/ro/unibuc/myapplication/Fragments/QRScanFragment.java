package ro.unibuc.myapplication.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import ro.unibuc.myapplication.R;

public class QRScanFragment extends Fragment {
    CodeScanner codeScanner;

    private static final int PERMISSION_REQUEST_CAMERA = 0;

    public QRScanFragment() {
        super(R.layout.fragmnet_camera_qr_scan);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestCamera();

        CodeScannerView codeScannerView = view.findViewById(R.id.qr_scanner_view);
        codeScanner = new CodeScanner(view.getContext(), codeScannerView);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            int QRValue = Integer.parseInt(result.getText());
                            // Set qr value to button
                            Button button = requireActivity().findViewById(R.id.qrcamera);
                            button.setText(result.getText());

                            codeScanner.releaseResources();
                            // Close fragment
                            requireActivity().getFragmentManager().popBackStack();
                        }
                        catch (NumberFormatException e){
                            Toast.makeText(requireContext(), "Result is not a valid table QR.", Toast.LENGTH_SHORT).show();
                            codeScanner.startPreview();
                        }
                    }
                });
            }
        });

        codeScannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    public void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

    private void requestCamera() {
        boolean cameraPermissionGiven =
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        if (!cameraPermissionGiven) {
            // If we do not have camera permission, then we request it again
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[] { Manifest.permission.CAMERA }, PERMISSION_REQUEST_CAMERA);
        }
    }

}
