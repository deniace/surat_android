package com.deni.surat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.deni.surat.MainActivity;
import com.deni.surat.R;
import com.deni.surat.databinding.ActivityLoginBinding;
import com.deni.surat.model.BiodataResponseOne;
import com.deni.surat.model.BiodataSingle;
import com.deni.surat.model.LoginRequest;
import com.deni.surat.model.LoginResponse;
import com.deni.surat.model.PreferenceManager;
import com.deni.surat.network.RetrofitInstance;
import com.deni.surat.network.endpoint.Biodata;
import com.deni.surat.network.endpoint.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private String TAG = "LoginActivity";
    private ActivityLoginBinding binding;
    private PreferenceManager preferenceManager;
    private Retrofit retrofit;
    private LoginRequest loginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setActivity(this);
        setTitle("Login");
        loginRequest = new LoginRequest();
        binding.setUser(loginRequest);
        preferenceManager = new PreferenceManager(this);
        isLogin();
        setUser();
        retrofit = RetrofitInstance.getRetrofit(this);
    }

    /**
     * untuk mengecek apakah sudah login apa belum jika sudah login maka akan ke main activity,
     * jika belum maka akan stay
     */
    private void isLogin() {
        if (preferenceManager.isLogin()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * pindah ke register
     *
     * @param view button rergister
     */
    public void goToRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.putExtra("TITLE", "Register");
        startActivity(intent);
    }
    /**
     * mengghilangkan / membersihkan error message yang ada di tampilan
     */
    private void nullError() {
        binding.tilLoginEmail.setErrorEnabled(false);
        binding.tilLoginEmail.setError(null);
        binding.tilLoginPassword.setErrorEnabled(false);
        binding.tilLoginPassword.setError(null);
    }

    private void rememberMe() {
        if (binding.cbRememberLogin.isChecked()) {
            preferenceManager.setEmail(loginRequest.getEmail());
            preferenceManager.setPassword(loginRequest.getPassword());
            preferenceManager.setRememberMe(true);
        } else {
            preferenceManager.setEmail("");
            preferenceManager.setPassword("");
            preferenceManager.setRememberMe(false);
        }
    }

    /**
     * mengeset user dan password kedalam text view jika user memilih untuk mengingat password
     */
    private void setUser() {
        if (preferenceManager.isRememberMe()) {
            loginRequest.setEmail(preferenceManager.getEmail());
            loginRequest.setPassword(preferenceManager.getPassword());
            binding.setUser(loginRequest);
            binding.cbRememberLogin.setChecked(true);
        } else {
            binding.cbRememberLogin.setChecked(false);
            loginRequest = new LoginRequest();
            binding.setUser(loginRequest);
        }
    }

    /**
     * untuk menyimpan data user ke shared preference
     * @param loginResponse = data dari respon body
     */
    private void saveResponse(LoginResponse loginResponse){
        // simpan ke preference manager
        preferenceManager.setToken(loginResponse.getToken());
        preferenceManager.setLogin(true);
        preferenceManager.setIdUser(loginResponse.getIdUser());
    }

    /**
     * fungsi untuk login ke server
     *
     * @param view btn login
     */
    public void login(View view) {
        nullError();
        binding.textErrorLogin.setText(null);
        if (isDataValid()) {
            binding.pbLogin.setVisibility(View.VISIBLE);
            rememberMe();
            User user = retrofit.create(User.class);
            Call<LoginResponse> result = user.login(loginRequest);

            result.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body().isStatus()){
                            // menyimpan response ke shared preferences
                            saveResponse(response.body());
                            //get profile/ biodata user
                            getProfile(response.body().getToken(), response.body().getIdUser());

                        }else {
                            binding.pbLogin.setVisibility(View.GONE);
                            binding.textErrorLogin.setText(response.body().getMessage());
                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        binding.pbLogin.setVisibility(View.GONE);
                        binding.textErrorLogin.setText(R.string.error_login);
                        Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    t.printStackTrace();
                    binding.pbLogin.setVisibility(View.GONE);
                    binding.textErrorLogin.setText(R.string.error_login);
                    Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    /**
     * mengecek apakah data yang di input oleh user sudah valid atau belum
     * @return true jika valid, false jika tidak valid
     */
    private boolean isDataValid() {
        String error = "";
        boolean r = false;
        if (binding.tietLoginEmail.getText().toString().length() < 1) {
            error = "Email harus di isi";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.textErrorLogin.setText(error);
            binding.tilLoginEmail.setErrorEnabled(true);
            binding.tilLoginEmail.setError(error);
            r = false;
        } else if (binding.tietLoginPassword.getText().toString().length() < 1) {
            error = "Password Tidak boleh kosong";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            binding.textErrorLogin.setText(error);
            binding.tilLoginPassword.setErrorEnabled(true);
            binding.tilLoginPassword.setError(error);
            r = false;
        } else {
            r = true;
        }
        return r;
    }

    private void getProfile(String token, String idUser){
        Biodata biodata = retrofit.create(Biodata.class);

        Call<BiodataResponseOne> call = biodata.getProfileById(token, idUser);

        call.enqueue(new Callback<BiodataResponseOne>() {
            @Override
            public void onResponse(Call<BiodataResponseOne> call, Response<BiodataResponseOne> response) {
                if (response.isSuccessful()){
                    if (response.body().isStatus()){
                        // menyimpan response ke shared preferences
                        saveProfile(response.body().getBiodataSingle());
                        Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                        binding.pbLogin.setVisibility(View.GONE);
                        // Intent to main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        binding.pbLogin.setVisibility(View.GONE);
                        binding.textErrorLogin.setText(response.body().getMessage());
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    binding.pbLogin.setVisibility(View.GONE);
                    binding.textErrorLogin.setText(R.string.error_login);
                    Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BiodataResponseOne> call, Throwable t) {
                t.printStackTrace();
                binding.pbLogin.setVisibility(View.GONE);
                binding.textErrorLogin.setText(R.string.error_login);
                Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveProfile(BiodataSingle biodata){
        preferenceManager.setNamaUser(biodata.getNamaUser());
        preferenceManager.setNamaJabatan(biodata.getNamaJabatan());
        preferenceManager.setIdJabatan(biodata.getIdJabatan());
        preferenceManager.setNoHp(biodata.getNoHp());
        preferenceManager.setRt(biodata.getRt());
        preferenceManager.setRw(biodata.getRw());
    }
}
