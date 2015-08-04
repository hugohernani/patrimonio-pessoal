package bens;

import models.Bem;
import models.Categoria;

import com.example.patrimoniopessoal.R;
import com.example.patrimoniopessoal.R.layout;
import com.example.patrimoniopessoal.R.menu;

import dao.BensDao;
import dao.CategoriasDao;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewBemActivity extends Activity {
	
	Bundle extras;
	BensDao daoBem;
	CategoriasDao daoCategoria;
	Bem bem;
	
	TextView bemNome;
	TextView valorBem;
	TextView categoriaBem;
	TextView dataBem;
	TextView descricaoBem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bem_view);
		
		bem = new Bem();
		daoBem = new BensDao(this);
		daoCategoria = new CategoriasDao(this);
		extras = getIntent().getExtras();
		
		daoBem.open();
		daoCategoria.open();
		
		bemNome = (TextView) findViewById(R.id.lblNameBem);
		valorBem = (TextView) findViewById(R.id.lblValorBem);
		categoriaBem = (TextView) findViewById(R.id.lblCategoriaBem);
		dataBem = (TextView) findViewById(R.id.lblDatadoBem);
		descricaoBem = (TextView) findViewById(R.id.txtDescricaoBem);
		
	}
	
	@Override
	protected void onResume() {
		
		bem = daoBem.getBem(extras.getLong("id"));
		
		bemNome.setText(bem.getNome());
		valorBem.setText(getString(R.string.vwValorBem, bem.getValor()));
		dataBem.setText(getString(R.string.vwDataBem, bem.getData()));
		categoriaBem.setText(getString(R.string.vwCategoriaBem,
				daoCategoria.getCategoria(bem.getIdCategoria())).toString());
		descricaoBem.setText(bem.getDescricao());

		super.onResume();
	}
	
	@Override
	protected void onPause() {
		daoBem.close();
		daoCategoria.close();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.view_bem, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.item_alterarBem) {
			Intent intent = new Intent(ViewBemActivity.this, BensActivity.class);
			intent.putExtra("id", bem.getId());
			startActivity(intent);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}


}
