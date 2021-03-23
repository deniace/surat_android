package com.deni.surat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.deni.surat.R;
import com.deni.surat.adapter.SuratValidasiAdapter;
import com.deni.surat.databinding.ActivityDownloadSuratBinding;
import com.deni.surat.konstanta.Const;
import com.deni.surat.model.PreferenceManager;
import com.deni.surat.model.SuratResponsePaging;
import com.deni.surat.model.SuratSingle;
import com.deni.surat.network.RetrofitInstance;
import com.deni.surat.network.endpoint.SuratInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadSuratActivity extends AppCompatActivity {

    private ActivityDownloadSuratBinding binding;
    private SuratInterface suratInterface;
    private LinearLayoutManager linearLayoutManager;
    private SuratValidasiAdapter adapter;
    private String TAG = "DownloadSuratActivity";
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_download_surat);
        setTitle("Download Surat");
        preferenceManager = new PreferenceManager(this);

        suratInterface = RetrofitInstance.getRetrofit(this).create(SuratInterface.class);
        linearLayoutManager = new LinearLayoutManager(this);
        binding.rvDownloadSurat.setLayoutManager(linearLayoutManager);
        binding.rvDownloadSurat.setHasFixedSize(true);
        binding.srlDownloadSurat.setOnRefreshListener(refreshListener);
        adapter = new SuratValidasiAdapter(listener);
        binding.rvDownloadSurat.setAdapter(adapter);
        loadData();
    }

    private SuratValidasiAdapter.OnItemClickListener listener = new SuratValidasiAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(SuratSingle item) {
            if (item.getIdStatusRt().equals("1")){
                // surat sudah di acc rt
                if (item.getIdStatusRw().equals("1")){
                    // surat sudah di acc rw
                    Uri webpage = Uri.parse(Const.url()+item.getIdSurat());
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(intent);
//                    Toast.makeText(DownloadSuratActivity.this, "downloading...", Toast.LENGTH_SHORT).show();
                }else {
                    // surat belum di acc rw
                    Toast.makeText(DownloadSuratActivity.this, "Menunggu Persetujuan RW", Toast.LENGTH_SHORT).show();
                }
            }else {
                /// surat belum di acc rt
                Toast.makeText(DownloadSuratActivity.this, "Menunggu Persetujuan RT", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadData();
        }
    };

    private void loadData() {
        binding.srlDownloadSurat.setRefreshing(true);

        adapter = new SuratValidasiAdapter(listener);
        binding.rvDownloadSurat.setAdapter(adapter);

        Call<SuratResponsePaging> call = suratInterface.getSuratByUser(preferenceManager.getToken(), preferenceManager.getIdUser());

        call.enqueue(new Callback<SuratResponsePaging>() {
            @Override
            public void onResponse(Call<SuratResponsePaging> call, Response<SuratResponsePaging> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        binding.srlDownloadSurat.setRefreshing(false);
                        adapter.addItems(response.body().getSuratSingle());
                        adapter.notifyItemInserted(response.body().getSuratSingle().size());
                    } else {
                        binding.srlDownloadSurat.setRefreshing(false);
                        Toast.makeText(DownloadSuratActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.srlDownloadSurat.setRefreshing(false);
                    Toast.makeText(DownloadSuratActivity.this, R.string.error_ambil_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuratResponsePaging> call, Throwable t) {
                t.printStackTrace();
                binding.srlDownloadSurat.setRefreshing(false);
                Toast.makeText(DownloadSuratActivity.this, R.string.error_ambil_data, Toast.LENGTH_SHORT).show();
            }
        });
    }
}