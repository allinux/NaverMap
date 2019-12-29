package com.jinasoft.navermap;





import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;

import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private Fragment NMAP;

    private ArrayList<String> name;
    private ArrayList<String> phone_number;
    private ArrayList<String> address;
    private ArrayList<Double> longitude;
    private ArrayList<Double> latitude;
    private JSONArray jsonArray;
    private TextView textView;


    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            textView =findViewById(R.id.textView) ;
            name = new ArrayList<>();
            phone_number = new ArrayList<>();
            address = new ArrayList<>();
            List<Double>latitude = new ArrayList<>();
            List<Double>longitude = new ArrayList<>();



            MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.NMAP);
            if (mapFragment == null) {
                mapFragment = MapFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.NMAP, mapFragment).commit();
            }

            mapFragment.getMapAsync((OnMapReadyCallback) this);
        }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {


        InsertData task = new InsertData();
        task.execute("http://58.230.203.182/Landpage/Realtor_List.php");


//        List<Double> LNG =longitude;
//        List<Double> LAT =latitude;


//        Marker marker = new Marker();
//        marker.setPosition(new LatLng(LAT,LNG));
//        marker.setMap(naverMap);
//        naverMap.getCameraPosition();

    }
    class InsertData extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);



            try {


                JSONObject Land = new JSONObject(result);


                        JSONArray jsonArray = Land.getJSONArray("Response");

                for(int i = 0 ; i<jsonArray.length(); i++){
                    JSONObject subJsonObject = jsonArray.getJSONObject(i);

                String sNAME = subJsonObject.getString("name");
                String sPHONE = subJsonObject.getString("cell_phone_number");
                String sADD = subJsonObject.getString("address");
                Double sLAT = subJsonObject.getDouble("latitude");
                Double sLNG = subJsonObject.getDouble("longitude");
                name.add(sNAME);
                phone_number.add(sPHONE);
                address.add(sADD);
                latitude.add(sLAT);
                longitude.add(sLNG);

//                double LAT = Double.parseDouble(String.valueOf(latitude));
//                double LNG = Double.parseDouble(String.valueOf(longitude));

//                NaverMap naverMap = null;
//
//                    Marker marker = new Marker();
//                    marker.setPosition(new LatLng(LAT,LNG));
//                    marker.setMap(naverMap);



                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            textView.setText(latitude+"\n" + longitude);





       //    textView.setText(result);
     }


        @Override
        protected String doInBackground(String... params) {



            String postParameters ="";


            try {

                URL url = new URL("http://58.230.203.182/Landpage/Realtor_List.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();


                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {


                return new String("Error: " + e.getMessage());
            }

        }
    }


}





