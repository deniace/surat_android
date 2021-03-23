package com.deni.surat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.deni.surat.R;
import com.deni.surat.databinding.ActivityBiodataDetailBinding;
import com.deni.surat.model.BiodataResponseOne;
import com.deni.surat.model.BiodataSingle;
import com.deni.surat.model.PreferenceManager;
import com.deni.surat.model.RegisterResponse;
import com.deni.surat.network.RetrofitInstance;
import com.deni.surat.network.endpoint.Biodata;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BiodataDetailActivity extends AppCompatActivity {

    private String TAG = "BiodataDetailActivity";
    private ActivityBiodataDetailBinding binding;
    private PreferenceManager preference;
    private BiodataSingle biodataSingle;
    private String idUser;
    private Retrofit retrofit;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;
    private ArrayAdapter<CharSequence> adapterJabatan;
    private ArrayAdapter<CharSequence> adapterJenisKelamin;
    private ArrayAdapter<CharSequence> adapterAgama;
    private ArrayAdapter<CharSequence> adapterStatusPerkawinan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_biodata_detail);
        binding.setActivity(this);
        setTitle("Biodata Warga");
        biodataSingle = new BiodataSingle();
        binding.setProfile(biodataSingle);
        preference = new PreferenceManager(this);
            retrofit = RetrofitInstance.getRetrofit(this);

            idUser = getIntent().getStringExtra("ID_USER");

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        adapterJabatan = ArrayAdapter.createFromResource(this, R.array.jabatan_array, android.R.layout.simple_spinner_item);
        adapterJabatan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinJabatan.setAdapter(adapterJabatan);
        binding.spinJabatan.setOnItemSelectedListener(selectSpiner);

        adapterJenisKelamin = ArrayAdapter.createFromResource(this, R.array.jeniskelamin_array, android.R.layout.simple_spinner_item);
        adapterJenisKelamin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinJenisKelamin.setAdapter(adapterJenisKelamin);
        binding.spinJenisKelamin.setOnItemSelectedListener(selectSpinerJenisKelamin);

        adapterAgama = ArrayAdapter.createFromResource(this, R.array.agama_array, android.R.layout.simple_spinner_item);
        adapterAgama.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinAgama.setAdapter(adapterAgama);
        binding.spinAgama.setOnItemSelectedListener(selectSpinerAgama);

        adapterStatusPerkawinan = ArrayAdapter.createFromResource(this, R.array.status_perkawinan_array, android.R.layout.simple_spinner_item);
        adapterStatusPerkawinan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinStatusPerkawinan.setAdapter(adapterStatusPerkawinan);
        binding.spinStatusPerkawinan.setOnItemSelectedListener(selectStatusPerkawinan);

        binding.tietProfileTglLahir.setOnClickListener(pilihTanggal);

        if (preference.getIdJabatan().equals("1") || preference.getIdJabatan().equals("2")){
            binding.buttonDelete.setVisibility(View.VISIBLE);
        }else {
            binding.buttonDelete.setVisibility(View.GONE);
        }
        populateData(idUser);
    }

    /**
     * fungsi untuk mengambil data dari spinner
     */
    Spinner.OnItemSelectedListener selectSpiner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String pilihan = parent.getItemAtPosition(position).toString();
            switch (pilihan) {
                case "Warga":
                    biodataSingle.setIdJabatan("4");
                    break;
                case "Ketua RT":
                    biodataSingle.setIdJabatan("2");
                    break;
                case "Ketua RW":
                    biodataSingle.setIdJabatan("3");
                    break;
                default:
                    biodataSingle.setIdJabatan("4");
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /**
     * fungsi untuk mengambil data dari spinner
     */
    Spinner.OnItemSelectedListener selectSpinerJenisKelamin = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String pilihan = parent.getItemAtPosition(position).toString();
            biodataSingle.setJenisKelamin(pilihan);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /**
     * fungsi untuk mengambil data dari spinner
     */
    Spinner.OnItemSelectedListener selectSpinerAgama = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String pilihan = parent.getItemAtPosition(position).toString();
            biodataSingle.setAgama(pilihan);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /**
     * fungsi untuk mengambil data dari spinner
     */
    Spinner.OnItemSelectedListener selectStatusPerkawinan = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String pilihan = parent.getItemAtPosition(position).toString();
            biodataSingle.setStatusPerkawinan(pilihan);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /**
     * fungsi untuk menampilkan pemilihan tanggal
     */
    public void showDate() {
        // calendar untuk mendapatkan tanggal sekarang
        final Calendar calendar = Calendar.getInstance();

        // inisialisasi datepicker dialog
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);

                // update textview untuk menampilkan tanggal yang dipilih
                binding.tietProfileTglLahir.setText(simpleDateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * fungsi untuk merespon klik pada tanggal
     */
    View.OnClickListener pilihTanggal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDate();
        }
    };


    /**
     * fungsi untuk mengambil data dari server dan menampiklan ke text edit
     *
     * @param idUser = id user
     */
    private void populateData(String idUser) {
        binding.pbBiodata.setVisibility(View.VISIBLE);

        Biodata biodata = retrofit.create(Biodata.class);
        Call<BiodataResponseOne> call = biodata.getProfileById(preference.getToken(), idUser);

        call.enqueue(new Callback<BiodataResponseOne>() {
            @Override
            public void onResponse(Call<BiodataResponseOne> call, Response<BiodataResponseOne> response) {
                    if (response.isSuccessful()) {
                        if (response.body().isStatus()) {
                            binding.pbBiodata.setVisibility(View.GONE);
                            biodataSingle = response.body().getBiodataSingle();
                            binding.setProfile(biodataSingle);
                            binding.spinAgama.setSelection(adapterAgama.getPosition(biodataSingle.getAgama()));
                            binding.spinJabatan.setSelection(adapterJabatan.getPosition(biodataSingle.getNamaJabatan()));
                            binding.spinJenisKelamin.setSelection(adapterJenisKelamin.getPosition(biodataSingle.getJenisKelamin()));
                            binding.spinStatusPerkawinan.setSelection(adapterStatusPerkawinan.getPosition(biodataSingle.getStatusPerkawinan()));
                        } else {
                            binding.pbBiodata.setVisibility(View.GONE);
                            Toast.makeText(BiodataDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                } else {
                    binding.pbBiodata.setVisibility(View.GONE);
                    Toast.makeText(BiodataDetailActivity.this, R.string.error_ambil_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BiodataResponseOne> call, Throwable t) {
                t.printStackTrace();
                binding.pbBiodata.setVisibility(View.GONE);
                Toast.makeText(BiodataDetailActivity.this, R.string.error_ambil_data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * fungsi untuk update profile / biodata user
     *
     * @param view button ubah
     */
    public void updateProfile(View view) {
        binding.pbBiodata.setVisibility(View.VISIBLE);

        Biodata biodata = retrofit.create(Biodata.class);
        Call<RegisterResponse> call = biodata.updateProfileById(preference.getToken(), biodataSingle, idUser);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        binding.pbBiodata.setVisibility(View.GONE);
                        Toast.makeText(BiodataDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("ID_USER", idUser);
                        intent.putExtra("ACTION", "UPDATE");
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else {
                        binding.pbBiodata.setVisibility(View.GONE);
                        Toast.makeText(BiodataDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbBiodata.setVisibility(View.GONE);
                    Toast.makeText(BiodataDetailActivity.this, R.string.error_kirim_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                t.printStackTrace();
                binding.pbBiodata.setVisibility(View.GONE);
                Toast.makeText(BiodataDetailActivity.this, R.string.error_kirim_data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * menampilkan dialog konfirmasi hapus
     * @param view : btn hapus
     */
    public void delete(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi Penghapusan")
                .setMessage("Apakah anda yakin?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        processDelete();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * fungsi untuk melakukan proses delete
     */
    private void processDelete(){
        binding.pbBiodata.setVisibility(View.VISIBLE);
        Biodata biodata = retrofit.create(Biodata.class);

        Call<RegisterResponse> call = biodata.deleteProfileById(preference.getToken(),idUser);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        binding.pbBiodata.setVisibility(View.GONE);
                        Toast.makeText(BiodataDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("ID_USER", idUser);
                        intent.putExtra("ACTION", "DELETE");
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else {
                        binding.pbBiodata.setVisibility(View.GONE);
                        Toast.makeText(BiodataDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.pbBiodata.setVisibility(View.GONE);
                    Toast.makeText(BiodataDetailActivity.this, R.string.error_kirim_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                t.printStackTrace();
                binding.pbBiodata.setVisibility(View.GONE);
                Toast.makeText(BiodataDetailActivity.this, R.string.error_hapus_data, Toast.LENGTH_SHORT).show();
            }
        });
    }
}