package bens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.patrimoniopessoal.R;
import com.example.patrimoniopessoal.R.id;
import com.example.patrimoniopessoal.R.layout;
import com.example.patrimoniopessoal.R.menu;

import models.Bem;
import models.Categoria;

import dao.BensDao;
import dao.CategoriasDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class ListaBens_byCategory_Activity extends Activity {
	
	private BensDao daoBens;
	private CategoriasDao  daoCategorias;
	private ExpandableListAdapter listAdapter;
	private ExpandableListView expListView;
	private List<Categoria> listDataHeader;
	private HashMap<Categoria, List<Bem>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_by_category);
		daoBens = new BensDao(this);
		daoBens.open();
		daoCategorias = new CategoriasDao(this);
		daoCategorias.open();
		
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		
		prepareListData();
		
		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
		
		expListView.setAdapter(listAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_bens, menu);
		return true;
	}
	
	
	@Override
	protected void onPause() {
		daoBens.close();
		daoCategorias.close();
		super.onPause();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.add_bem) {
			startActivity(new Intent(this, BensActivity.class));
		} else if (item.getItemId() == R.id.change_view) {
			startActivity(new Intent(this, ListBensActivity.class));
			finish();
		} else if (item.getItemId() == R.id.zerar_bens) {
			daoBens.deleteAll();
			Toast.makeText(this, "Bens apagados", Toast.LENGTH_LONG).show();
			recreate();
		} else if (item.getItemId() == R.id.zerar_categorias) {
			daoCategorias.deleteAll();
			Toast.makeText(this, "Categorias apagadas", Toast.LENGTH_LONG).show();
			recreate();
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void prepareListData() {
		listDataHeader = daoCategorias.getAll();
		listDataChild = new HashMap<Categoria, List<Bem>>();
		
		for(int i = 0; i < listDataHeader.size(); i++) {
			List<Bem> bens = daoBens.getBemByCategory(listDataHeader.get(i).getId());
			listDataChild.put(listDataHeader.get(i), bens);
		}
		Categoria c = new Categoria();
		c.setId(200); // número alto qualquer
		c.setNome("Sem categoria");
		listDataHeader.add(c);
		List<Bem> bens = daoBens.getBemSemCategoria();
		listDataChild.put(listDataHeader.get(listDataHeader.size()-1), bens);
		
	}

}
