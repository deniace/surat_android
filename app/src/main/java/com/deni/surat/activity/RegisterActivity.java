package com.deni.surat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.deni.surat.R;
import com.deni.surat.databinding.ActivityRegisterBinding;
import com.deni.surat.model.PreferenceManager;
import com.deni.surat.model.RegisterRequest;
import com.deni.surat.model.RegisterResponse;
import com.deni.surat.network.RetrofitInstance;
import com.deni.surat.network.endpoint.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private String TAG = "RegisterActivity";
    private ActivityRegisterBinding binding;
    private PreferenceManager preferenceManager;
    private Retrofit retrofit;
    private RegisterRequest registerRequest;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setActivity(this);
        String title = getIntent().getStringExtra("TITLE");
        setTitle(title);
        binding.buttonRegister.setText(title);
        preferenceManager = new PreferenceManager(this);
        retrofit = RetrofitInstance.getRetrofit(this);
        registerRequest = new RegisterRequest();
        binding.setRegister(registerRequest);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jabatan_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinJabatan.setAdapter(adapter);
        binding.spinJabatan.setOnItemSelectedListener(selectSpiner);

        ArrayAdapter<CharSequence> adapterJenisKelamin = ArrayAdapter.createFromResource(this, R.array.jeniskelamin_array, android.R.layout.simple_spinner_item);
        adapterJenisKelamin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinJenisKelamin.setAdapter(adapterJenisKelamin);
        binding.spinJenisKelamin.setOnItemSelectedListener(selectSpinerJenisKelamin);

        ArrayAdapter<CharSequence> adapterAgama = ArrayAdapter.createFromResource(this, R.array.agama_array, android.R.layout.simple_spinner_item);
        adapterAgama.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinAgama.setAdapter(adapterAgama);
        binding.spinAgama.setOnItemSelectedListener(selectSpinerAgama);

        ArrayAdapter<CharSequence> adapterStatusPerkawinan = ArrayAdapter.createFromResource(this, R.array.status_perkawinan_array, android.R.layout.simple_spinner_item);
        adapterStatusPerkawinan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinStatusPerkawinan.setAdapter(adapterStatusPerkawinan);
        binding.spinStatusPerkawinan.setOnItemSelectedListener(selectStatusPerkawinan);

        binding.tietRegisterTglLahir.setOnClickListener(pilihTanggal);
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
                    registerRequest.setIdJabatan("4");
                    break;
                case "Ketua RT":
                    registerRequest.setIdJabatan("2");
                    break;
                case "Ketua RW":
                    registerRequest.setIdJabatan("3");
                    break;
                default:
                    registerRequest.setIdJabatan("4");
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
            registerRequest.setJenisKelamin(pilihan);
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
            registerRequest.setAgama(pilihan);
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
            registerRequest.setStatusPerkawinan(pilihan);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    /**
     * fungsi untuk mengembalikan error menjadi tidak error
     */
    private void nullError() {
        binding.tilRegisterUsernama.setErrorEnabled(false);
        binding.tilRegisterUsernama.setError(null);
        binding.tilRegisterPassword.setErrorEnabled(false);
        binding.tilRegisterPassword.setError(null);
        binding.tilRegisterPassconf.setErrorEnabled(false);
        binding.tilRegisterPassconf.setError(null);
        binding.tilRegisterAlamat.setErrorEnabled(false);
        binding.tilRegisterAlamat.setError(null);
        binding.tilRegisterNohp.setErrorEnabled(false);
        binding.tilRegisterNohp.setError(null);
        binding.tilRegisterRt.setErrorEnabled(false);
        binding.tilRegisterRt.setError(null);
        binding.tilRegisterRw.setErrorEnabled(false);
        binding.tilRegisterRw.setError(null);
        binding.tilRegisterTmptLahir.setErrorEnabled(false);
        binding.tilRegisterTmptLahir.setError(null);
        binding.tilRegisterTglLahir.setErrorEnabled(false);
        binding.tilRegisterTglLahir.setError(null);
    }

    /**
     * mengecek apakah data yang di input oleh user sudah valid atau belum
     * @return true jika valid, false jika tidak valid
     */
    private boolean isDataValid() {
        String error = "";
        boolean r = false;

        if (binding.tietRegisterUsernama.getText().toString().length() < 1 || binding.tietRegisterUsernama.getText().toString().equals("")) {
            error = "User name harus di isi";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.textErrorRegister.setText(error);
            binding.tilRegisterUsernama.setErrorEnabled(true);
            binding.tilRegisterUsernama.setError(error);
            r = false;
        } else if (binding.tietRegisterUseremail.getText().toString().length() < 1 || binding.tietRegisterUseremail.getText().toString().equals("")) {
            error = "Email Tidak boleh kosong";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.textErrorRegister.setText(error);
            binding.tilRegisterUseremail.setErrorEnabled(true);
            binding.tilRegisterUseremail.setError(error);
            r = false;
        } else if (binding.tietRegisterPassword.getText().toString().length() < 1 || binding.tietRegisterPassword.getText().toString().equals("")) {
            error = "Password Tidak boleh kosong";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.textErrorRegister.setText(error);
            binding.tilRegisterPassword.setErrorEnabled(true);
            binding.tilRegisterPassword.setError(error);
            r = false;
        } else if (binding.tietRegisterPassconf.getText().toString().length() < 1 || binding.tietRegisterPassconf.getText().toString().equals("")) {
            error = "Password konfirmasi Tidak boleh kosong";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.textErrorRegister.setText(error);
            binding.tilRegisterPassconf.setErrorEnabled(true);
            binding.tilRegisterPassconf.setError(error);
            r = false;
        } else if (!binding.tietRegisterPassconf.getText().toString().equals(binding.tietRegisterPassword.getText().toString())) {
            error = "Password Tidak sama";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.textErrorRegister.setText(error);
            binding.tilRegisterPassconf.setErrorEnabled(true);
            binding.tilRegisterPassconf.setError(error);
            r = false;
        } else if (binding.tietRegisterNohp.getText().toString().length() < 1 || binding.tietRegisterNohp.getText().toString().equals("")) {
            error = "Nomor HP Tidak boleh kosong";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.textErrorRegister.setText(error);
            binding.tilRegisterNohp.setErrorEnabled(true);
            binding.tilRegisterNohp.setError(error);
            r = false;
        } else if (binding.tietRegisterAlamat.getText().toString().length() < 1 || binding.tietRegisterAlamat.getText().toString().equals("")) {
            error = "Alamat Tidak boleh kosong";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.textErrorRegister.setText(error);
            binding.tilRegisterAlamat.setErrorEnabled(true);
            binding.tilRegisterAlamat.setError(error);
            r = false;
        } else if (binding.tietRegisterRt.getText().toString().length() < 1 || binding.tietRegisterRt.getText().toString().equals("")) {
            error = "RT Tidak boleh kosong";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.textErrorRegister.setText(error);
            binding.tilRegisterRt.setErrorEnabled(true);
            binding.tilRegisterRt.setError(error);
            r = false;
        } else if (binding.tietRegisterRw.getText().toString().length() < 1 || binding.tietRegisterRw.getText().toString().equals("")) {
            error = "RW Tidak boleh kosong";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.textErrorRegister.setText(error);
            binding.tilRegisterRw.setErrorEnabled(true);
            binding.tilRegisterRw.setError(error);
            r = false;
        } else if (binding.tietRegisterTmptLahir.getText().toString().length() < 1 || binding.tietRegisterTmptLahir.getText().toString().equals("")) {
            error = "Tempat lahir Tidak boleh kosong";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.textErrorRegister.setText(error);
            binding.tilRegisterTmptLahir.setErrorEnabled(true);
            binding.tilRegisterTmptLahir.setError(error);
            r = false;
        } else if (binding.tietRegisterPekerjaan.getText().toString().length() < 1 || binding.tietRegisterPekerjaan.getText().toString().equals("")) {
            error = "Pekerjaan Tidak boleh kosong";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.textErrorRegister.setText(error);
            binding.tilRegisterTmptLahir.setErrorEnabled(true);
            binding.tilRegisterTmptLahir.setError(error);
            r = false;
        } else if (binding.tietRegisterTglLahir.getText().toString().length() < 1 || binding.tietRegisterTglLahir.getText().toString().equals("")) {
            error = "Tanggal lahir Tidak boleh kosong";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.textErrorRegister.setText(error);
            binding.tilRegisterTglLahir.setErrorEnabled(true);
            binding.tilRegisterTglLahir.setError(error);
            r = false;
        } else {
            r = true;
        }
        return r;
    }

    /**
     * fungsi untuk melakukan register ke server
     * @param view = button register
     */
    public void register(View view) {
        nullError();
        binding.textErrorRegister.setText(null);
        if (isDataValid()) {
            binding.pbRegister.setVisibility(View.VISIBLE);
            User user = retrofit.create(User.class);
            Call<RegisterResponse> call = user.register(registerRequest);

            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().isStatus()) {
                            binding.pbRegister.setVisibility(View.GONE);
                            binding.textErrorRegister.setText(response.body().getMessage());
                            Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("ID_USER", response.body().getIdUser());
                            intent.putExtra("ACTION", "NEW");
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        } else {
                            binding.pbRegister.setVisibility(View.GONE);
                            binding.textErrorRegister.setText(response.body().getMessage());
                            Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        binding.pbRegister.setVisibility(View.GONE);
                        binding.textErrorRegister.setText(R.string.error_register);
                        Toast.makeText(RegisterActivity.this, R.string.error_register, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    binding.pbRegister.setVisibility(View.GONE);
                    binding.textErrorRegister.setText(R.string.error_register);
                    Toast.makeText(RegisterActivity.this, R.string.error_register, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * fungsi untuk menampilkan pemilihan tanggal
     */
    public void showDate(){
        // calendar untuk mendapatkan tanggal sekarang
        final Calendar calendar = Calendar.getInstance();

        // inisialisasi datepicker dialog
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);

                // update textview untuk menampilkan tanggal yang dipilih
                binding.tietRegisterTglLahir.setText(simpleDateFormat.format(newDate.getTime()));
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

}
