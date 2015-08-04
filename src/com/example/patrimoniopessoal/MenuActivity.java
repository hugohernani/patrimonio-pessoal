package com.example.patrimoniopessoal;

import obrigacoes.ObrigacoesActivity;
import patrimonioliquido.PatrimonioLiquidoActivity;
import settings.ConfiguracoesActivity;
import direitos.DireitosActivity;
import bens.BensActivity;
import bens.ListBensActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	}
	
	public void openFuncionalidade(View view) {
		int idLayout = view.getId();
		Intent intent = null;
		if (idLayout == R.id.btMainItemBens) {
			intent = new Intent(this, ListBensActivity.class);
		} else if (idLayout == R.id.btMainItemDireitos) {
			intent = new Intent(this, DireitosActivity.class);
		} else if (idLayout == R.id.btMainItemObrigacoes) {
			intent = new Intent(this, ObrigacoesActivity.class);
		} else {
			intent = new Intent(this, PatrimonioLiquidoActivity.class);
		}
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.item_adicionarBem) {
			startActivity(new Intent(MenuActivity.this, BensActivity.class));
		} else if(item.getItemId() == R.id.item_configuracao) {
			startActivity(new Intent(MenuActivity.this, ConfiguracoesActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}

}
