package com.deni.surat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.deni.surat.R;
import com.deni.surat.databinding.ActivityBiodataDetailBinding;
import com.deni.surat.databinding.ActivityValidasiSuratDetailBinding;
import com.deni.surat.konstanta.Const;
import com.deni.surat.model.PreferenceManager;
import com.deni.surat.model.RegisterResponse;
import com.deni.surat.model.SuratResponseOne;
import com.deni.surat.model.SuratSingle;
import com.deni.surat.model.SuratValidasiRequest;
import com.deni.surat.network.RetrofitInstance;
import com.deni.surat.network.endpoint.SuratInterface;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ValidasiSuratDetailActivity extends AppCompatActivity {

    private String idSurat;
    private ActivityValidasiSuratDetailBinding binding;
    private PreferenceManager preferenceManager;
    private String TAG = "ValidasiSuratDetailActivity";
    private SuratSingle suratSingle;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_validasi_surat_detail);
        binding.setActivity(this);
        setTitle("Validasi Surat");
        suratSingle = new SuratSingle();
        binding.setSurat(suratSingle);
        preferenceManager = new PreferenceManager(this);
        retrofit = RetrofitInstance.getRetrofit(this);

        idSurat = getIntent().getStringExtra("ID_SURAT");
        populateData(idSurat);
    }

    /**
     * fungsu yang dijalankan ketika tombol acc di tekan dan mengirimkan data ke server bahwa surat di acc
     *
     * @param view = button validasi
     */
    public void acc(View view) {
        sendValidasi("1");
    }

    /**
     * fungsu yang dijalankan ketika tombol reject di tekan dan mengirimkan data ke server bahwa surat di reject
     *
     * @param view = button reject
     */
    public void reject(View view) {
        sendValidasi("2");
    }

    /**
     * fungsi untuk mengambil data dari server dan menampiklan ke text edit
     *
     * @param idSurat = id surat
     */
    private void populateData(String idSurat) {
        binding.pbValidasiSuratDetail.setVisibility(View.VISIBLE);

        SuratInterface suratInterface = retrofit.create(SuratInterface.class);
        Call<SuratResponseOne> call = suratInterface.getSuratById(preferenceManager.getToken(), idSurat);

        call.enqueue(new Callback<SuratResponseOne>() {
            @Override
            public void onResponse(Call<SuratResponseOne> call, Response<SuratResponseOne> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        binding.pbValidasiSuratDetail.setVisibility(View.GONE);
                        suratSingle = response.body().getSuratSingle();
                        if (response.body().getSuratSingle().getNamaFile() != null && !response.body().getSuratSingle().getNamaFile().equals("")){
                            Picasso.get().load(Const.urlFoto()+response.body().getSuratSingle().getNamaFile())
                                    .placeholder(R.drawable.noimage)
                                    .into(binding.ivValidasiSurat);
                        }
                        binding.setSurat(suratSingle);
                    } else {
                        binding.pbValidasiSuratDetail.setVisibility(View.GONE);
                        Toast.makeText(ValidasiSuratDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbValidasiSuratDetail.setVisibility(View.GONE);
                    Toast.makeText(ValidasiSuratDetailActivity.this, R.string.error_ambil_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuratResponseOne> call, Throwable t) {
                t.printStackTrace();
                binding.pbValidasiSuratDetail.setVisibility(View.GONE);
                Toast.makeText(ValidasiSuratDetailActivity.this, R.string.error_ambil_data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private SuratValidasiRequest suratValidasiRequest(String idStatus) {
        SuratValidasiRequest validasiRequest = new SuratValidasiRequest();
        if (preferenceManager.getIdJabatan().equals("2")) {
            // ketua rt
            validasiRequest.setIdJabatan(preferenceManager.getIdJabatan());
            validasiRequest.setIdRt(preferenceManager.getIdUser());
            validasiRequest.setIdStatusRt(idStatus);
        } else if (preferenceManager.getIdJabatan().equals("3")) {
            // ketua rw
            validasiRequest.setIdJabatan(preferenceManager.getIdJabatan());
            validasiRequest.setIdRw(preferenceManager.getIdUser());
            validasiRequest.setIdStatusRw(idStatus);
        }else {
            validasiRequest.setIdJabatan("4");
        }
        return validasiRequest;
    }

    /**
     * fungsi untuk mengirim validasi
     */
    private void sendValidasi(String idStatus){
        binding.pbValidasiSuratDetail.setVisibility(View.VISIBLE);
        SuratInterface suratInterface = retrofit.create(SuratInterface.class);
        Call<RegisterResponse> call = suratInterface.validasiSuratById(preferenceManager.getToken(), idSurat, suratValidasiRequest(idStatus));

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        binding.pbValidasiSuratDetail.setVisibility(View.GONE);
                        Toast.makeText(ValidasiSuratDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("ID_SURAT", idSurat);
                        intent.putExtra("ACTION", "UPDATE");
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else {
                        binding.pbValidasiSuratDetail.setVisibility(View.GONE);
                        Toast.makeText(ValidasiSuratDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbValidasiSuratDetail.setVisibility(View.GONE);
                    Toast.makeText(ValidasiSuratDetailActivity.this, R.string.error_kirim_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                t.printStackTrace();
                binding.pbValidasiSuratDetail.setVisibility(View.GONE);
                Toast.makeText(ValidasiSuratDetailActivity.this, R.string.error_kirim_data, Toast.LENGTH_SHORT).show();
            }
        });
    }
}