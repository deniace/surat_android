package com.deni.surat.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.deni.surat.R;
import com.deni.surat.adapter.BiodataAdapter;
import com.deni.surat.databinding.ActivityBiodataWargaBinding;
import com.deni.surat.model.BiodataRequestPaging;
import com.deni.surat.model.BiodataResponseOne;
import com.deni.surat.model.BiodataResponsePaging;
import com.deni.surat.model.BiodataSingle;
import com.deni.surat.model.PreferenceManager;
import com.deni.surat.network.RetrofitInstance;
import com.deni.surat.network.endpoint.Biodata;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BiodataWargaActivity extends AppCompatActivity {

    private ActivityBiodataWargaBinding binding;
    private Biodata biodata;
    private LinearLayoutManager linearLayoutManager;
    private int page = 0;
    private int page_size = 10;
    private int totalData = 0;
    private boolean isFistView = true;
    private BiodataAdapter adapter;
    private String TAG = "BiodataWargaActivity";
    private int itemIndex = -1;
    private int itemIndexFull = -1;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_biodata_warga);
        preferenceManager = new PreferenceManager(this);
        setTitle("Biodata");
        if (preferenceManager.getIdJabatan().equals("2")){
            binding.fabBiodataTambahBiodata.setVisibility(View.VISIBLE);
        }
        biodata = RetrofitInstance.getRetrofit(this).create(Biodata.class);
        linearLayoutManager = new LinearLayoutManager(this);
        binding.rvBiodataWargaAll.setLayoutManager(linearLayoutManager);
        binding.rvBiodataWargaAll.setHasFixedSize(true);
        binding.srlBiodataWargaAll.setOnRefreshListener(refreshListener);
        adapter = new BiodataAdapter(listener);
        binding.rvBiodataWargaAll.setAdapter(adapter);
        loadData(false);
    }

    private BiodataAdapter.OnItemClickListener listener = new BiodataAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BiodataSingle item) {
            itemIndex = adapter.getList().indexOf(item);
            itemIndexFull = adapter.getListFull().indexOf(item);
            Intent intent = new Intent(BiodataWargaActivity.this, BiodataDetailActivity.class);
            intent.putExtra("STATE", 1);
            intent.putExtra("ID_USER", item.getUserId());
            startActivityForResult(intent, 1);
        }
    };

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            binding.rvBiodataWargaAll.removeOnScrollListener(scrollListener);
            loadData(false);
        }
    };

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int totalItemCount = adapter.getList().size();
            int lastItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            boolean isLastItemVisible = lastItemPosition == totalItemCount -1;
            if (totalItemCount > 0 && isLastItemVisible){
                recyclerView.removeOnScrollListener(this);
                loadData(true);
                Log.d(TAG, "onScrolled: last item position = "+lastItemPosition);
            }
        }
    };

    private void loadData(boolean isNext){
        binding.srlBiodataWargaAll.setRefreshing(true);
        if (isNext){
            page++;
        }else{
            adapter = new BiodataAdapter(listener);
            binding.rvBiodataWargaAll.setAdapter(adapter);
            page = 1;
        }

        Call<BiodataResponsePaging> call = biodata.getProfilePaging(preferenceManager.getToken(), getPage());

        call.enqueue(new Callback<BiodataResponsePaging>() {
            @Override
            public void onResponse(Call<BiodataResponsePaging> call, Response<BiodataResponsePaging> response) {
                if(response.isSuccessful()){
                    if (response.body().isStatus()){
                        binding.srlBiodataWargaAll.setRefreshing(false);
                        Log.d(TAG, "onResponse: nama warga = "+response.body().getBiodataSingle().get(0).getNamaJabatan());
                        adapter.addItems(response.body().getBiodataSingle());
                        adapter.notifyItemRangeInserted(adapter.getItemCount(), response.body().getBiodataSingle().size());

                        if (adapter.getListFull().size() < response.body().getTotalData()){
                            binding.rvBiodataWargaAll.addOnScrollListener(scrollListener);
                        }
                    }else {
                        binding.srlBiodataWargaAll.setRefreshing(false);
                        Toast.makeText(BiodataWargaActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    binding.srlBiodataWargaAll.setRefreshing(false);
                    Toast.makeText(BiodataWargaActivity.this, R.string.error_ambil_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BiodataResponsePaging> call, Throwable t) {
                t.printStackTrace();
                binding.srlBiodataWargaAll.setRefreshing(false);
                Toast.makeText(BiodataWargaActivity.this, R.string.error_ambil_data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private BiodataRequestPaging getPage(){
        BiodataRequestPaging requestPaging = new BiodataRequestPaging();
        requestPaging.setPage(page);
        requestPaging.setPageSize(page_size);
        requestPaging.setIdJabatan(preferenceManager.getIdJabatan());
        requestPaging.setRt(preferenceManager.getRt());
        requestPaging.setRw(preferenceManager.getRw());
        return requestPaging;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: result code = "+resultCode+" requestCode = "+requestCode);
        if (resultCode == RESULT_OK){
            String idUser = data.getStringExtra("ID_USER");
            String action = data.getStringExtra("ACTION");
            Log.d(TAG, "onActivityResult: ID_USER ="+idUser);

            if(action.equals("DELETE")){
                adapter.getList().remove(itemIndex);
                adapter.getListFull().remove(itemIndexFull);
                adapter.notifyDataSetChanged();
            }else {
                binding.srlBiodataWargaAll.setRefreshing(true);
                Call<BiodataResponseOne> call = biodata.getProfileById(preferenceManager.getToken(), idUser);

                call.enqueue(new Callback<BiodataResponseOne>() {
                    @Override
                    public void onResponse(Call<BiodataResponseOne> call, Response<BiodataResponseOne> response) {
                        if(response.isSuccessful()){
                            if (response.body().isStatus()){
                                binding.srlBiodataWargaAll.setRefreshing(false);
                                BiodataSingle data = response.body().getBiodataSingle();

                                if(action.equals("NEW")){
                                    adapter.getList().add(data);
                                    adapter.getListFull().add(data);
                                }else{
                                    adapter.getList().set(itemIndex, data);
                                    adapter.getListFull().set(itemIndexFull,data);
                                }
                                adapter.notifyDataSetChanged();
                            }else {
                                binding.srlBiodataWargaAll.setRefreshing(false);
                                Toast.makeText(BiodataWargaActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            binding.srlBiodataWargaAll.setRefreshing(false);
                            Toast.makeText(BiodataWargaActivity.this, R.string.error_ambil_data, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BiodataResponseOne> call, Throwable t) {
                        binding.srlBiodataWargaAll.setRefreshing(false);
                        t.printStackTrace();
                        Toast.makeText(BiodataWargaActivity.this, R.string.error_ambil_data, Toast.LENGTH_SHORT).show();
                        if(t instanceof IOException){
                            Toast.makeText(BiodataWargaActivity.this, "Network Failure", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(BiodataWargaActivity.this, "Big Problem", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    /**
     * untuk pindah ke tambah warga
     * @param view = floating action button
     */
    public void goToTambahBiodata(View view){
        itemIndex = -1;
        itemIndexFull = -1;
        Intent intent = new Intent(BiodataWargaActivity.this, RegisterActivity.class);
        intent.putExtra("STATE", 0);
        intent.putExtra("ID_USER", "");
        intent.putExtra("TITLE", "Tambah Data Warga");
        startActivityForResult(intent, 1);
    }
}