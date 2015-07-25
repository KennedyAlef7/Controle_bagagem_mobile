package com.example.controle_bagagem;


import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class Informar_bagagens extends Activity {

	ArrayList<String> bagagens_voo;
	ArrayList<String> bagagens_informar;
	Button btn_confirmar;
	EditText edt_cod_bag;
	TextView txtcod_voo;
	TextView txtquantidade;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.informar_bagagem_);
		btn_confirmar = (Button) findViewById(R.id.btnInsere_bagagem);
		edt_cod_bag = (EditText) findViewById(R.id.edt_cod_bagagem);
		txtcod_voo = (TextView) findViewById(R.id.txtcod_voo);
		txtquantidade = (TextView) findViewById(R.id.txtquantidade_bag);
		bagagens_voo = getIntent().getStringArrayListExtra("bagagens");
		txtcod_voo.setText(getIntent().getStringExtra("cod_voo"));
		txtquantidade.setText(String.valueOf(getIntent().getStringArrayListExtra("bagagens").size()));
		
		btn_confirmar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(!bagagens_voo.contains(String.valueOf(edt_cod_bag.getText())))
				{
					Dialog dialog = new Dialog(Informar_bagagens.this);
					dialog.setTitle("Erro");
					dialog.show();
						
				}
				}
				
			
		});
		
	}

	
}
