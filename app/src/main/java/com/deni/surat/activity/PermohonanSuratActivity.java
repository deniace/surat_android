package com.deni.surat.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.deni.surat.R;
import com.deni.surat.databinding.ActivityPermohonanSuratBinding;
import com.deni.surat.model.PreferenceManager;
import com.deni.surat.model.RegisterResponse;
import com.deni.surat.model.SuratRequest;
import com.deni.surat.network.RetrofitInstance;
import com.deni.surat.network.endpoint.SuratInterface;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PermohonanSuratActivity extends AppCompatActivity {

    private String TAG = "PermohonanSuratActivity";
    Context context = PermohonanSuratActivity.this;
    private SuratRequest suratRequest;
    private ActivityPermohonanSuratBinding binding;
    private SuratInterface suratInterface;
    private Retrofit retrofit;
    private int requestCode = 100;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_permohonan_surat);
        binding.setActivity(this);
        setTitle("Permohonan Surat");

        preferenceManager = new PreferenceManager(this);
        retrofit = RetrofitInstance.getRetrofit(this);
        suratRequest = new SuratRequest();
        binding.setSurat(suratRequest);
        setTitle("Permohonan Surat");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.keperluan_surat_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinKeperluanSurat.setAdapter(adapter);
        binding.spinKeperluanSurat.setOnItemSelectedListener(selectSpiner);
    }

    /**
     * fungsi untuk mengambil data dari spinner
     */
    Spinner.OnItemSelectedListener selectSpiner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String pilihan = parent.getItemAtPosition(position).toString();
            suratRequest.setKeperluan(pilihan);
            switch (pilihan) {
                case "Membuat KTP":
                    binding.btnKamera.setText("Foto Kartu Keluarga");
                    break;
                case "Pembuatan Kartu Keluarga (KK)":
                    binding.btnKamera.setText("Foto Buku Nikah");
                    break;
                case "SKTM (Surat Keterangan Tidak Mampu)":
                    binding.btnKamera.setText("Foto KTP");
                    break;
                case "Keterangan Miskin":
                    binding.btnKamera.setText("Foto KTP");
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    /**
     * fungsi untuk melakukan pengiriman permohonan surat ke server
     *
     * @param view = button permohonan surat
     */
    public void kirimPermohonan(View view) {
        binding.pbPermohonanSurat.setVisibility(View.VISIBLE);
        SuratInterface suratInterface = retrofit.create(SuratInterface.class);
        suratRequest.setIdUser(preferenceManager.getIdUser());
        Call<RegisterResponse> call = suratInterface.postSurat(preferenceManager.getToken(), suratRequest);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        binding.pbPermohonanSurat.setVisibility(View.GONE);
                        binding.tvTextErorMohonSurat.setText(response.body().getMessage());
                        Toast.makeText(PermohonanSuratActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        binding.pbPermohonanSurat.setVisibility(View.GONE);
                        binding.tvTextErorMohonSurat.setText(response.body().getMessage());
                        Toast.makeText(PermohonanSuratActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbPermohonanSurat.setVisibility(View.GONE);
                    binding.tvTextErorMohonSurat.setText(R.string.error_kirim_data);
                    Toast.makeText(PermohonanSuratActivity.this, R.string.error_kirim_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                binding.pbPermohonanSurat.setVisibility(View.GONE);
                binding.tvTextErorMohonSurat.setText(R.string.error_kirim_data);
                Toast.makeText(PermohonanSuratActivity.this, R.string.error_kirim_data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToCamera(View view){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context, "Membuka Kamera", Toast.LENGTH_SHORT).show();
            openCamera();
        }else {
            ActivityCompat.requestPermissions(PermohonanSuratActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        }
    }

    /**
     * fungsi untuk membuka kamera untuk foto
     */
    public void openCamera (){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, requestCode);
    }

    /**
     * fungsi yang dipanggil setelah memfoto
     * @param requestCode = cameraRequestCode = 100
     * @param resultCode = result code
     * @param data = intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode){
            if (resultCode == Activity.RESULT_OK){
                // jika result sukses
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                binding.ivPermohonanSurat.setImageBitmap(imageBitmap);
                bitmapTo64(imageBitmap);

            }
            if (resultCode == Activity.RESULT_CANCELED){
                // jika result gagal
            }
        }
    }

    /**
     * fungsi untuk mengubah hasil foto menjadi byte array
     * @param bitmap hasil foto dari kamera
     */
    private void bitmapTo64(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        String encodeFoto = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        Log.d(TAG, "bitmapTo64: "+encodeFoto);
        suratRequest.setFoto(encodeFoto);
    }
}