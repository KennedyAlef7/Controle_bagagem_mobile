package com.example.controle_bagagem;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import android.view.View.OnClickListener; 
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Request_WS extends Activity {

	LongOperation operation ;
	ArrayList<String> bagagens ;
	EditText edtcod_voo;
	EditText edtData_voo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request__ws);
		final String serverURL = "http://192.168.202.1/Controle_bagagem/busca_bagagens.php";
		Button btn = (Button) findViewById(R.id.btnrequest);
		edtData_voo = (EditText) findViewById(R.id.edtdata_Voo);
		edtcod_voo = (EditText) findViewById(R.id.edt_cod_voo);
		bagagens = new ArrayList<String>();
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				JSONObject json = new JSONObject();
				try {
					json.put("cod_voo",edtcod_voo.getText().toString());
					json.put("data_voo",edtData_voo.getText().toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//new LongOperation("8655").execute(serverURL);
				bagagens = new ArrayList<String>();
				//Toast.makeText(Request_WS.this, json.toString(), Toast.LENGTH_SHORT).show();
				operation = new LongOperation(String.valueOf(json.toString()));
				operation.execute(serverURL);
				
				
			
			}
		});
	
	
	}

	  private class LongOperation  extends AsyncTask<String, Void, Void> {   
			
			
		  	
		    private final HttpClient Client = new DefaultHttpClient();
		    private String Content;
		    private String Error = null;
		    private ProgressDialog Dialog = new ProgressDialog(Request_WS.this);
		    String data ="";  
		    int sizeData = 0;  
		    String id = "";
		    public LongOperation(String id){
				  this.id = id;

				  
			  }
	
		     
		    protected void onPreExecute() {
		        // NOTE: You can call UI Element here.
		          
		        //Start Progress Dialog (Message)
		        bagagens = new ArrayList<String>();
		        Dialog.setMessage("Please wait..");
		        Dialog.show();
		        
		        /*
		        try{
		            // Set Request parameter
		            data +="&" + URLEncoder.encode("cod_voo", "UTF-8") + "="+id;
		                 
		        } catch (UnsupportedEncodingException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        } 
		         */
		    }

		    // Call after onPreExecute method
		    protected Void doInBackground(String... urls) {
		         
		    	
		    	Content  = Request_WS(urls[0],id);
		       return null; 
		        /************ Make Post Call To Web Server ***********/
		        /*
		    	BufferedReader reader=null;

		             // Send data 
		            try
		            { 
		               
		               // Defined URL  where to send data
		               URL url = new URL(urls[0]);
		                  
		              // Send POST data request
		    
		              URLConnection conn = url.openConnection(); 
		              conn.setRequestProperty("Content-Type","application/json"); 
		              conn.setDoOutput(true); 
		              OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
		              wr.write( data ); 
		              wr.flush(); 
		              Toast.makeText(Request_WS.this, data, Toast.LENGTH_LONG).show();
		              // Get the server response 
		                
		              reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		              StringBuilder sb = new StringBuilder();
		              String line = null;
		             
		                // Read Server Response
		                while((line = reader.readLine()) != null)
		                    {
		                           // Append server response in string
		                           sb.append(line + "");
		                    }
		                 
		                // Append Server Response To Content String 
		               Content = sb.toString();
		            }
		            catch(Exception ex)
		            {
		                Error = ex.getMessage();
		            }
		            finally
		            {
		                try
		                {
		      
		                    reader.close();
		                }
		    
		                catch(Exception ex) {}
		            }
		         
		        /*****************************************************/
		     //   return null;
		     
		    }
		      
		    public String Request_WS (String uri, String json) {
		        HttpURLConnection urlConnection;
		        String data = json;
		        String url;
		        String result = null;
		        try {
		            //Connect 
		            urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
		            urlConnection.setDoOutput(true);
		            urlConnection.setRequestProperty("Content-Type", "application/json");
		            urlConnection.setRequestProperty("Accept", "application/json");
		            urlConnection.setRequestMethod("POST");
		            urlConnection.connect();

		            //Write
		            OutputStream outputStream = urlConnection.getOutputStream();
		            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
		            writer.write(data);
		            writer.close();
		            outputStream.close();

		            //Read
		            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

		            String line = null;
		            StringBuilder sb = new StringBuilder();

		            while ((line = bufferedReader.readLine()) != null) {
		                sb.append(line);
		            }

		            bufferedReader.close();
		            result = sb.toString();
		            //Toast.makeText(Request_WS.this, String.valueOf(value)result, Toast.LENGTH_SHORT).show();

		        } catch (UnsupportedEncodingException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        return result;
		    }
		    
		    
		    protected void onPostExecute(Void unused) {
		        // NOTE: You can call UI Element here.
		          
		        // Close progress dialog
		        Dialog.dismiss();
		      //  Toast.makeText(Request_WS.this, Content, Toast.LENGTH_LONG).show();   
		        System.out.println(Content);
		        if (Error != null) {
		              
		            
		            Toast.makeText(Request_WS.this, Error, Toast.LENGTH_SHORT).show();
		              
		        } else {
		           
		            // Show Response Json On Screen (activity)
		           
		             
		         /****************** Start Parse Response JSON Data *************/
		             
		            String OutputData = "";
		            JSONObject jsonResponse;
		                   
		            try {
		                   
		                 /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
		                 jsonResponse = new JSONObject(Content);
		                   
		                 /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
		                 /*******  Returns null otherwise.  *******/
		                 JSONArray jsonMainNode = jsonResponse.optJSONArray("Bagagem");
		                   
		                 /*********** Process each JSON Node ************/

		                 int lengthJsonArr = jsonMainNode.length();  

		                 for(int i=0; i < lengthJsonArr; i++) 
		                 {
		                     /****** Get Object for each JSON node.***********/
		                     JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
		                       
		                     /******* Fetch node values **********/
		                     bagagens.add(jsonChildNode.optString("cod_bagagem").toString());
		                    // String descricao     = jsonChildNode.optString("status_1").toString();
		                     //String preco = jsonChildNode.optString("status_3").toString();
		                     // String status_3 = jsonChildNode.optString("status_3").toString();
		                       
		                     
		                     OutputData += " - "+bagagens.get(i).toString() 
		                                 +"--------------------------------------------------";
		                     
		                      
		                }
		             /****************** End Parse Response JSON Data *************/    
		                  
		                 //Show Parsed Output on screen (activity)
		              //   jsonParsed.setText( OutputData );
		               //  Toast.makeText(Request_WS.this, OutputData, Toast.LENGTH_SHORT).show();
		                  
		                   
		             } catch (JSONException e) {
		       
		                 e.printStackTrace();
		             }

		              
		         }
		        Intent i = new Intent(Request_WS.this,Informar_bagagens.class);
				i.putStringArrayListExtra("bagagens", bagagens);
				Toast.makeText(Request_WS.this, String.valueOf(bagagens.size()), Toast.LENGTH_LONG).show();
				i.putExtra("cod_voo", String.valueOf(edtcod_voo.getText()));
				startActivity(i);
		    }

		
	  }
}
