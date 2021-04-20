package ro.unibuc.myapplication.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.util.ArrayList;

import ro.unibuc.myapplication.Fragments.CRUDs.OrdersViewFragment;
import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.Models.Order;
import ro.unibuc.myapplication.R;

public class QRScanFragment extends Fragment {
    CodeScanner codeScanner;

    private static final int PERMISSION_REQUEST_CAMERA = 0;
    public static final String QR_CODE_BUNDLE = "qrcodebundle";

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

                            Order order = null;
                            Bundle bundle = getArguments();
                            if (bundle != null) {
                                order = bundle.getParcelable(OrdersViewFragment.getBundleKey());

                                if (order != null) {
                                    order.setTableQRValue(QRValue);
                                }
                            }
                            else{
                                order = new Order(new ArrayList<Item>(), QRValue, 0, null);
                            }

                            Bundle newBundle = new Bundle();
                            newBundle.putParcelable(OrdersViewFragment.getBundleKey(), order);
                            Navigation.findNavController(view).navigate(R.id.CRUD_Order, newBundle);

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
