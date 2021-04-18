package ro.unibuc.myapplication.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import ro.unibuc.myapplication.R;

public class QRScanFragment extends Fragment {
    TextView scanResult;
    CodeScanner codeScanner;

    public QRScanFragment() {
        super(R.layout.fragmnet_camera_qr_scan);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scanResult = view.findViewById(R.id.qr_scanner_result);
        CodeScannerView codeScannerView = view.findViewById(R.id.qr_scanner_view);
        codeScanner = new CodeScanner(view.getContext(), codeScannerView);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                Toast.makeText(requireContext(), result.getText(), Toast.LENGTH_SHORT).show();
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
}
