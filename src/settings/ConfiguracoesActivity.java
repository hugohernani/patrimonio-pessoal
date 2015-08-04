package settings;

import java.util.List;

import com.example.patrimoniopessoal.R;
import com.example.patrimoniopessoal.R.id;
import com.example.patrimoniopessoal.R.layout;
import com.example.patrimoniopessoal.R.menu;
import com.example.patrimoniopessoal.R.string;

import models.Categoria;

import dao.CategoriasDao;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConfiguracoesActivity extends ListActivity implements OnItemClickListener, OnClickListener, OnKeyListener{
	
	TextView tituloTabCategoria;
	EditText nameCategoria;
	TextView tituloListaTabCategoria;
	private boolean alterar;
	
	Categoria categoria;
	
	CategoriasDao daoCategoria;
	List<Categoria> listaCategorias;
	ArrayAdapter<Categoria> adapter;
	Button botao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracoes);
		
		tituloTabCategoria = (TextView) findViewById(R.id.infoTitleCategoria);
		nameCategoria = (EditText) findViewById(R.id.editNameCategoria);
		tituloListaTabCategoria = (TextView) findViewById(R.id.tituloActivity);
		
		daoCategoria = new CategoriasDao(this);
		daoCategoria.open();
		
		categoria = new Categoria();
		
		listaCategorias = daoCategoria.getAll();
		adapter = new ArrayAdapter<Categoria>(getBaseContext(), android.R.layout.simple_list_item_1,listaCategorias);
		setListAdapter(adapter);
		
//		botao = (Button) findViewById(R.id.btMainItemBens);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		tituloTabCategoria.setVisibility(View.GONE);
		tituloListaTabCategoria.setText("Categorias");
		
		botao.setOnClickListener(this);

		getListView().setOnItemClickListener(this);
		
	}
	
	@Override
	protected void onPause() {
		daoCategoria.close();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.configuracoes, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.zerar_categorias) {
			daoCategoria.deleteAll();
			Toast.makeText(this, "Categorias zeradas", Toast.LENGTH_LONG).show();
			recreate();
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		botao.setText("Alterar");
		alterar = true;
		categoria = (Categoria) adapter.getItem(arg2);
		nameCategoria.setText(categoria.getNome());
		nameCategoria.setOnKeyListener(this);
		Toast.makeText(getBaseContext(), "Altera a categoria", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onClick(View arg0) {
		
		salvar();
		
	}

	private void salvar() {
		if (!nameCategoria.getEditableText().toString().isEmpty()) {
			if (!alterar) {
				daoCategoria.create(nameCategoria.getEditableText().toString());
				Toast.makeText(getBaseContext(), R.string.categoria_salva, Toast.LENGTH_LONG).show();
				nameCategoria.setText("");
				recreate();
			} else {
				categoria.setNome(nameCategoria.getEditableText().toString());
				daoCategoria.update(categoria);
				Toast.makeText(getBaseContext(), R.string.categoria_salva, Toast.LENGTH_LONG).show();
				nameCategoria.setText("");
				alterar = false;
				recreate();
			}
		} else {
			Toast.makeText(getBaseContext(), R.string.campos_vazios, Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
		if (nameCategoria.getEditableText().toString().isEmpty()) {
			if (!alterar) {
				botao.setText("Adicionar");
			}
		}
		return false;
	}


}
