package com.example.patrimoniopessoal;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity implements OnClickListener,android.view.View.OnKeyListener{
	final static String APP_PREFS="app_prefs";
	final static String USERNAME_KEY="username";
	final static String PRIMEIRO_LOGIN="true";
	final static String SENHA_KEY="12345";
	
	private EditText caixaTextoSenha;
	private TextView informativo;
	private Button botao;
	private EditText caixaTextoSenha2;
	private boolean primeiroLogin;
	private EditText caixaTextoNome;
	private SharedPreferences banco;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    
		banco = getSharedPreferences(Login.APP_PREFS,MODE_PRIVATE);
		primeiroLogin = banco.getBoolean(Login.PRIMEIRO_LOGIN,true);
		
		if(primeiroLogin){
			setContentView(R.layout.primeiro_login);
			caixaTextoNome = (EditText) findViewById(R.id.nome);
			caixaTextoSenha2 = (EditText) findViewById(R.id.password2);
			caixaTextoSenha2.setOnKeyListener(this);
		}else{
			setContentView(R.layout.login);
			String name = banco.getString(Login.USERNAME_KEY,null);
			TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);
			textViewLogin.setText("Bem Vindo, "+name);
		}
		
		caixaTextoSenha = (EditText) findViewById(R.id.password);
		informativo = (TextView) findViewById(R.id.text);
		botao = (Button) findViewById(R.id.sign_in_button);
		botao.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	

	@Override
	public void onClick(View v) {
		if(primeiroLogin){
			if(informativo.getText().equals(" ")){
				String username = caixaTextoNome.getEditableText().toString();
				Editor editor = banco.edit();
				editor.putString(Login.USERNAME_KEY,username);
				editor.putBoolean(Login.PRIMEIRO_LOGIN, false);
				editor.putString(Login.SENHA_KEY, caixaTextoSenha.getText().toString());
				editor.commit();
				Intent intent = new Intent(Login.this,MenuActivity.class);
				startActivity(intent);
				finish();
			}
		}else{
			String senha = banco.getString(Login.SENHA_KEY,null);
			if(caixaTextoSenha.getText().toString().equals(senha)){
				Intent intent = new Intent(Login.this,MenuActivity.class);
				startActivity(intent);
				finish();
			}else{
				informativo.setText("senha incorreta");
				//codigo abaixo serve para resetar a aplicação e ter o primeiro login novamente
//				Editor editor = banco.edit();
//				editor.putBoolean(Login.PRIMEIRO_LOGIN, true);
//				editor.commit();
			}
		}
		
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		String pass1 = caixaTextoSenha.getText().toString();
		String pass2 = caixaTextoSenha2.getText().toString();
		if (pass1.equals(pass2)){
			informativo.setText(" ");
		}else{
			informativo.setText("*as senhas nao correspondem");
		}
		return false;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.reset_login) {
			Editor editor = banco.edit();
			editor.putBoolean(Login.PRIMEIRO_LOGIN, true);
			editor.commit();
			startActivity(new Intent(Login.this, Login.class));
			finish();
		}
		return super.onMenuItemSelected(featureId, item);
	}

}