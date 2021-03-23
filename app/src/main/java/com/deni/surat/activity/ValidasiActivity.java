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
import android.widget.Toast;

import com.deni.surat.R;
import com.deni.surat.adapter.SuratValidasiAdapter;
import com.deni.surat.databinding.ActivityValidasiBinding;
import com.deni.surat.model.SuratResponseOne;
import com.deni.surat.model.SuratRequestPaging;
import com.deni.surat.model.SuratResponsePaging;
import com.deni.surat.model.PreferenceManager;
import com.deni.surat.model.SuratSingle;
import com.deni.surat.network.RetrofitInstance;
import com.deni.surat.network.endpoint.SuratInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidasiActivity extends AppCompatActivity {

    private ActivityValidasiBinding binding;
    private SuratInterface suratInterface;
    private LinearLayoutManager linearLayoutManager;
    private int page = 0;
    private int page_size = 10;
    private int totalData = 0;
    private boolean isFistView = true;
    private SuratValidasiAdapter adapter;
    private String TAG = "ValidasiActivity";
    private int itemIndex = -1;
    private int itemIndexFull = -1;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_validasi);
        setTitle("Validasi Surat");
        preferenceManager = new PreferenceManager(this);

        suratInterface = RetrofitInstance.getRetrofit(this).create(SuratInterface.class);
        linearLayoutManager = new LinearLayoutManager(this);
        binding.rvValidasiAll.setLayoutManager(linearLayoutManager);
        binding.rvValidasiAll.setHasFixedSize(true);
        binding.srlValidasiAll.setOnRefreshListener(refreshListener);
        adapter = new SuratValidasiAdapter(listener);
        binding.rvValidasiAll.setAdapter(adapter);
        loadData(false);
    }

    private SuratValidasiAdapter.OnItemClickListener listener = new SuratValidasiAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(SuratSingle item) {
            itemIndex = adapter.getList().indexOf(item);
            itemIndexFull = adapter.getListFull().indexOf(item);
            Intent intent = new Intent(ValidasiActivity.this, ValidasiSuratDetailActivity.class);
            intent.putExtra("STATE", 1);
            intent.putExtra("ID_SURAT", item.getIdSurat());
            startActivityForResult(intent, 1);
        }
    };

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            binding.rvValidasiAll.removeOnScrollListener(scrollListener);
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
        binding.srlValidasiAll.setRefreshing(true);
        if (isNext){
            page++;
        }else{
            adapter = new SuratValidasiAdapter(listener);
            binding.rvValidasiAll.setAdapter(adapter);
            page = 1;
        }

        Call<SuratResponsePaging> call = suratInterface.getSuratPaging(preferenceManager.getToken(), getPage());

        call.enqueue(new Callback<SuratResponsePaging>() {
            @Override
            public void onResponse(Call<SuratResponsePaging> call, Response<SuratResponsePaging> response) {
                if(response.isSuccessful()){
                    if (response.body().getStatus()){
                        binding.srlValidasiAll.setRefreshing(false);
                        adapter.addItems(response.body().getSuratSingle());
                        adapter.notifyItemRangeInserted(adapter.getItemCount(), response.body().getSuratSingle().size());

                        if (adapter.getListFull().size() < response.body().getTotalData()){
                            binding.rvValidasiAll.addOnScrollListener(scrollListener);
                        }
                    }else {
                        binding.srlValidasiAll.setRefreshing(false);
                        Toast.makeText(ValidasiActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    binding.srlValidasiAll.setRefreshing(false);
                    Toast.makeText(ValidasiActivity.this, R.string.error_ambil_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuratResponsePaging> call, Throwable t) {
                t.printStackTrace();
                binding.srlValidasiAll.setRefreshing(false);
                Toast.makeText(ValidasiActivity.this, R.string.error_ambil_data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private SuratRequestPaging getPage(){
        SuratRequestPaging requestPaging = new SuratRequestPaging();
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
            String idSurat = data.getStringExtra("ID_SURAT");
            String action = data.getStringExtra("ACTION");
            Log.d(TAG, "onActivityResult: ID_SURAT ="+idSurat);

            if(action.equals("DELETE")){
                adapter.getList().remove(itemIndex);
                adapter.getListFull().remove(itemIndexFull);
                adapter.notifyDataSetChanged();
            }else {
                binding.srlValidasiAll.setRefreshing(true);
                Call<SuratResponseOne> call = suratInterface.getSuratById(preferenceManager.getToken(), idSurat);

                call.enqueue(new Callback<SuratResponseOne>() {
                    @Override
                    public void onResponse(Call<SuratResponseOne> call, Response<SuratResponseOne> response) {
                        if(response.isSuccessful()){
                            if (response.body().getStatus()){
                                binding.srlValidasiAll.setRefreshing(false);
                                SuratSingle data = response.body().getSuratSingle();

                                if(action.equals("NEW")){
                                    adapter.getList().add(data);
                                    adapter.getListFull().add(data);
                                }else{
                                    adapter.getList().set(itemIndex, data);
                                    adapter.getListFull().set(itemIndexFull,data);
                                }
                                adapter.notifyDataSetChanged();
                            }else {
                                binding.srlValidasiAll.setRefreshing(false);
                                Toast.makeText(ValidasiActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            binding.srlValidasiAll.setRefreshing(false);
                            Toast.makeText(ValidasiActivity.this, R.string.error_ambil_data, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SuratResponseOne> call, Throwable t) {
                        binding.srlValidasiAll.setRefreshing(false);
                        t.printStackTrace();
                        Toast.makeText(ValidasiActivity.this, R.string.error_ambil_data, Toast.LENGTH_SHORT).show();
                        if(t instanceof IOException){
                            Toast.makeText(ValidasiActivity.this, "Network Failure", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ValidasiActivity.this, "Big Problem", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}