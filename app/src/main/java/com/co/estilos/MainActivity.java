package com.co.estilos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggableView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;


public class MainActivity extends ActionBarActivity {

    protected FloatingActionButton btnSearch;
    // Progress Dialog
    private ProgressDialog pDialog;

    private static final int DELAY_MILLIS = 10;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> empresaList;

    public DraggableView draggableView;

    // url to get all products list
    private static String url_all_empresas = "http://jsonremota.besaba.com/connect/db_get_all_nombre.php";

    /*
    String protocol = "http://";
    String hots = "jsonremota.besaba.com/";
    String url = "connect/";
    String file = "db_get_all_nombre.php";
    */

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "sitios";
    private static final String TAG_ID = "id";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_UBICACION = "ubicacion";
    private static final String TAG_HORARIO = "horario";
    private static final String TAG_RUTA_IMG = "ruta_img";

    // products JSONArray
    JSONArray products = null;

    ListView lista;
    public TextView nombre, ubicacion, horario;
    public ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        initializeDraggableView();

        draggableView = (DraggableView) findViewById(R.id.draggable_view_resut);

        //nombre = (TextView) findViewById(R.id.txt_nombre_resultado);
       // ubicacion = (TextView) findViewById(R.id.txt_ubicacion_resultado);
       // horario = (TextView) findViewById(R.id.txt_horario_resultado);
        imagen = (ImageView) findViewById(R.id.img_resultado);

        // Hashmap para el ListView
        empresaList = new ArrayList<HashMap<String, String>>();

        // Cargar los productos en el Background Thread
        new LoadAllProducts().execute();
        lista = (ListView) findViewById(R.id.list_main);

        btnSearch = (FloatingActionButton) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Hola has presionado este boton", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(MainActivity.this, Registrar.class);
                //startActivity(intent);
            }
        });

        draggableView.setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {

            }

            @Override
            public void onMinimized() {

            }

            @Override
            public void onClosedToLeft() {

            }

            @Override
            public void onClosedToRight() {

            }
        });

        //nombre = (TextView) findViewById(R.id.txt_nombre_resul);
       // ubicacion = (TextView) findViewById(R.id.txt_ubicacion_result);
       // horario = (TextView) findViewById(R.id.txt_horario_result);
/*
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDraggableView(view);

                System.out.println(empresaList.get(position));

                String aux = "" + empresaList.get(position);
                List params = new ArrayList();
                // getting JSON string from URL

                String nombreS, ubicacionS, horarioS, ruta_imgS;

                try {
                    //JSONObject object = new JSONObject(aux);

                    //nombre = object.getString(TAG_NOMBRE);
                    //ubicacion = object.getString(TAG_UBICACION);

                    JSONObject c = products.getJSONObject(position);

                    nombreS = c.getString(TAG_NOMBRE);
                    ubicacionS = c.getString(TAG_UBICACION);
                    horarioS = c.getString(TAG_HORARIO);
                    ruta_imgS = c.getString(TAG_RUTA_IMG);

                   // nombre.setText(nombreS);
                   // nombre.setText(nombreS);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println("JSON LIST ------------ " + aux);
            }
        });
        */

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*
                openDraggableView(view);

                System.out.println(empresaList.get(position));

                String aux = "" + empresaList.get(position);

                String nombreS, ubicacionS, horarioS, ruta_imgS;

                try {
                    JSONObject c = products.getJSONObject(position);

                    nombreS = c.getString(TAG_NOMBRE);
                    ubicacionS = c.getString(TAG_UBICACION);
                    horarioS = c.getString(TAG_HORARIO);
                    ruta_imgS = c.getString(TAG_RUTA_IMG);

                    Bitmap bitmap;

                    bitmap = BitmapFactory.decodeFile(ruta_imgS);

                    //nombre.setText(nombreS);
                    //ubicacion.setText(ubicacionS);
                    //horario.setText(horarioS);
                    //imagen.setImageBitmap(bitmap);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println("JSON LIST ------------ " + aux);*/

                //openDraggableView(view);
            }
        });
    }


    private void initializeDraggableView() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                draggableView.setVisibility(View.GONE);
                //draggableView.closeToRight();
            }
        }, DELAY_MILLIS);
    }

    public void openDraggableView(View v) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                draggableView.setVisibility(View.VISIBLE);
                draggableView.maximize();
            }
        }, DELAY_MILLIS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando comercios. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * obteniendo todos los productos
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List params = new ArrayList();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_empresas, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    //Log.i("ramiro", "produtos.length" + products.length());
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NOMBRE);
                        String ubicacion = c.getString(TAG_UBICACION);
                        String horario = c.getString(TAG_HORARIO);
                        String rutaImg = c.getString(TAG_RUTA_IMG);

                        // creating new HashMap
                        HashMap map = new HashMap();

                        // adding each child node to HashMap key => value
                        map.put(TAG_ID, id);
                        map.put(TAG_NOMBRE, name);
                        map.put(TAG_UBICACION, ubicacion);
                        map.put(TAG_HORARIO, horario);
                        map.put(TAG_RUTA_IMG, rutaImg);

                        empresaList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            MainActivity.this,
                            empresaList,
                            R.layout.single_post,
                            new String[] {
                                    TAG_ID,
                                    TAG_NOMBRE,
                                    TAG_UBICACION,
                                    TAG_HORARIO,
                                    TAG_RUTA_IMG
                            },
                            new int[] {
                                    R.id.single_post_tv_id,
                                    R.id.txt_nombre_resul,
                                    R.id.txt_ubicacion,
                                    R.id.txt_horario,
                                    R.id.img
                            });
                    // updating listview
                    //setListAdapter(adapter);
                    lista.setAdapter(adapter);
                }
            });
        }
    }
}
