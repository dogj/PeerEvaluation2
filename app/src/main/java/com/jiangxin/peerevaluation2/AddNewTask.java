package com.jiangxin.peerevaluation2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AddNewTask extends AppCompatActivity {
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    EditText inputGender;
    EditText inputEmail;
    EditText inputAddress;
    String name;
    String gender;
    String email;
    String Address;
    // url to create new product
    private static String url_create_product = "https://dogj.000webhostapp.com/addtest.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new);

        // Edit Text
        inputAddress=(EditText)findViewById(R.id.inputAddress1);
        inputName = (EditText) findViewById(R.id.inputName);
        inputGender = (EditText) findViewById(R.id.inputGender);
        inputEmail = (EditText) findViewById(R.id.inputEmail);

        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreate);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                name = inputName.getText().toString();
                gender = inputGender.getText().toString();
                email = inputEmail.getText().toString();
                Address = inputAddress.getText().toString();
                new CreateNew().execute();
            }
        });
    }

    /**
     * Background Async Task to Create new Patient
     * */
    class CreateNew extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddNewTask.this);
            pDialog.setMessage("Uploading to server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", name));
            params.add(new BasicNameValuePair("password", gender));
            params.add(new BasicNameValuePair("name", email));
            params.add(new BasicNameValuePair("age",Address));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check log cat fro response
//            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
                String message = json.getString(TAG_MESSAGE);

                if (success == 1) {
                    // successfully created product
//                    Intent i = new Intent(getApplicationContext(), patient_info.class);
//                    startActivity(i);
                  Toast.makeText(getApplicationContext(),message.toString(),Toast.LENGTH_SHORT).show();

                    // closing this screen
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),message.toString(),Toast.LENGTH_SHORT).show();
                    // failed to create product
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
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}