package com.deni.surat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.deni.surat.activity.BiodataDetailActivity;
import com.deni.surat.activity.BiodataWargaActivity;
import com.deni.surat.activity.DownloadSuratActivity;
import com.deni.surat.activity.LoginActivity;
import com.deni.surat.activity.PermohonanSuratActivity;
import com.deni.surat.activity.ValidasiActivity;
import com.deni.surat.databinding.ActivityMainBinding;
import com.deni.surat.model.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    private PreferenceManager preference;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
        setTitle("Menu");

        // inisialisasi sharedPreference
        preference = new PreferenceManager(this);
        isLogin();
        if (preference.getIdJabatan().equals("1") || preference.getIdJabatan().equals("2") || preference.getIdJabatan().equals("3")){
            binding.llWarga.setVisibility(View.GONE);
        }else {
            binding.llDataValidasi.setVisibility(View.GONE);
        }
        binding.tvNamaUser.setText(preference.getNamaUser());
        binding.tvJabatanUser.setText(preference.getNamaJabatan());
    }
    /**
     * fungsi untuk mengecek sudah login atau belum, jika belum maka akan diarahkan ke login activity
     */
    private void isLogin(){
        if (!preference.isLogin()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * method untuk memanggil / menampilkan option menu ke mainactivity
     * @param menu : menu
     * @return : boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * untuk memberikan perintah atau fungsi ketika menu di pilih
     * @param menuItem : menu yang ada di menu item
     * @return : boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.log_out){
            logOut();
        }
        return true;
    }

    /**
     * fungsi untuk log out dan menghapus data2 yang sebelumnya login
     */
    private void logOut(){
        Toast.makeText(this, "log out", Toast.LENGTH_SHORT).show();
        preference.setLogin(false);
        preference.setToken(null);
        preference.setIdUser(null);
        preference.setNoHp(null);
        preference.setNamaUser(null);
        preference.setNamaJabatan(null);
        preference.setIdJabatan(null);
        isLogin();
    }

    /**
     * fungsi untuk ke biodata
     * @param view = cardview biodata
     */
    public void goToBiodataDetail(View view){
        Intent intent = new Intent(this, BiodataDetailActivity.class);
        intent.putExtra("ID_USER", preference.getIdUser());
        startActivity(intent);
    }

    /**
     * fungsi untuk ke biodata warga
     * @param view = cardview biodata warga
     */
    public void goToBiodataWarga(View view){
        Intent intent = new Intent(this, BiodataWargaActivity.class);
        startActivity(intent);
    }

    /**
     * fungsi untuk ke permohonan surat
     * @param view = cardview permohonan surat
     */
    public void goToPermohonanSurat(View view){
        Intent intent = new Intent(this, PermohonanSuratActivity.class);
        startActivity(intent);
    }

    /**
     * fungsi untuk ke validasi
     * @param view = cardview validasi
     */
    public void goToValidasi(View view){
        Intent intent = new Intent(this, ValidasiActivity.class);
        startActivity(intent);
    }

    /**
     * fungsi untuk ke download surat
     * @param view = cardview download surat
     */
    public void goToDownloadSurat(View view){
        Intent intent = new Intent(this, DownloadSuratActivity.class);
        startActivity(intent);
    }

}
