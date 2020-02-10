package com.example.ejemplodb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView cajaHex;

    private Statement st;
    private Connection con;
    private ResultSet rs;

    private String ip_Servidor = "192.188.10.11", Puerto = "1433", Usuario = "sa", Password = "adm123456";
    private String baseDatos = "usuarios1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cajaHex = findViewById(R.id.txtHex);

        Button connect = findViewById(R.id.btnConnect);
        Button gen_hex = findViewById(R.id.btnHex);
        gen_hex.setOnClickListener(this);
        connect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnConnect:
                //no ba ...:C
                Conectar conectar = new Conectar();
                conectar.execute();
                break;
            case R.id.btnHex:
                crearHexadecimal();
                break;
            default:Log.i("click","id no registrado en onclick") ;
        }
    }

    private class Conectar extends AsyncTask<Void, Integer, Boolean> {
        Connection conn;

        @Override
        protected Boolean doInBackground(Void... params) {

            Log.i("conn", "## -- empiza la conexion -- ##");
            try {
                String driver = "com.mysql.cj.jdbc.Driver";
                //+ ip_Servidor + ":" + Puerto+ "/"
                String urlMySQL = "jdbc:mysql://10.0.2.2/ejemplos/" ;
                Class.forName(driver).newInstance();

                conn = DriverManager.getConnection(urlMySQL, Usuario,"");

               st = conn.createStatement();
               st.executeQuery("insert into ejemplos.divice values('pruebas','pruebas@jaja.sd');");

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
