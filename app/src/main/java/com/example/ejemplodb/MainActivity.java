package com.example.ejemplodb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int id = 1;
    TextView cajaHex;//se tiene que concatenar con el tipo de alarma

    private Statement st;
    private Connection con;
    private ResultSet rs;

    private String ip_Servidor = "192.188.10.114", Puerto = "3306", Usuario = "root", Password = "";
    private String baseDatos = "usuarios1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cajaHex = findViewById(R.id.txtHex);
        //me gusta mas esta manera de controlar que se clica
        findViewById(R.id.btnConnect).setOnClickListener(this::onClick);
        findViewById(R.id.btnHex).setOnClickListener(this::onClick);
        findViewById(R.id.btnStart).setOnClickListener(this::onClick);
        findViewById(R.id.btnStop).setOnClickListener(this::onClick);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        id = Integer.parseInt(sharedPreferences.getString("list_preference", "1"));

        ip_Servidor = sharedPreferences.getString("url_db" + id, null);
        baseDatos = sharedPreferences.getString("name_db" + id, null);
        Usuario = sharedPreferences.getString("user_name" + id, null);
        Password = sharedPreferences.getString("user_pass" + id, null);

        Log.d("id", String.valueOf(id));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConnect:
                //no ba ...:C
                Conectar conectar = new Conectar();
                conectar.execute();
                break;

            case R.id.btnHex:
                crearHexadecimal();
                break;

            case R.id.btnStart:
                new Intent(MainActivity.this, ejemplobatery.class);
                break;

            case R.id.btnStop:
                new Intent(MainActivity.this, ejemplobatery.class);

                break;

            default:
                Log.i("click", "id no registrado en onclick");
        }
    }

    // ############################ menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pref, menu);
        return true; /** true -> el menú ya está visible */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.conf) {
            startActivity(new Intent(this, Preferences.class));
            return true;
        }
        if (id == R.id.dinamicas) {
            startActivity(new Intent(this, Main2Activity.class));
            return true;
        }
        if (id == R.id.ejfragmentpref) {
            startActivity(new Intent(this, SettingHeaderActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ############################ conn db y crear hexadecimal
    private class Conectar extends AsyncTask<Void, Integer, Boolean> {
        Connection conn;

        @Override
        protected Boolean doInBackground(Void... params) {

            Log.i("conn", "## -- empiza la conexion -- ##");
            try {
                String driver = "com.mysql.cj.jdbc.Driver";
                //+ ip_Servidor + ":" + Puerto+ "/"
                String url = "jdbc:mysql://" + ip_Servidor + "/";
                String urlMySQL = url + baseDatos + ":" + Puerto + "/";
                Class.forName(driver).newInstance();

                conn = DriverManager.getConnection(urlMySQL, Usuario, Password);

                st = conn.createStatement();
                st.executeQuery("insert into pruebas values('008','pruebas','pruebas@jaja.sd');");

                if (conn == null) {
                    return false;
                }
            } catch (NoClassDefFoundError e) {
                Log.e("catch Definicion de clase", e.getMessage());
            } catch (ClassNotFoundException e) {
                Log.e("catch Clase no encontrada", e.getMessage());
            } catch (Exception e) {
                Log.e("catch ERROR Conexion: ", e.getCause().toString());
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            if (resultado) {
                Toast.makeText(getBaseContext(), "Conectado", Toast.LENGTH_SHORT).show();
                Log.d("LOG:", "## -- conectado -- ##");
            } else {
                Toast.makeText(getBaseContext(), "No conectado", Toast.LENGTH_SHORT).show();
                Log.d("LOG:", "## -- no conectado -- ##");
            }
        }
    }

    private void crearHexadecimal() {
        Random rand = new Random();
        int myRandomNumber = rand.nextInt(0xfff) + 0xfff; // Generates a random number between 0x10 and 0x20
        //System.out.printf("%x\n",myRandomNumber); // Prints it in hex, such as "0x14" or....
        String result = Integer.toHexString(myRandomNumber); // Random hex number in result
        cajaHex.setText(result);
    }
}