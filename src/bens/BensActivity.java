package bens;

import java.util.Calendar;
import java.util.List;

import models.Bem;
import models.Categoria;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.patrimoniopessoal.R;

import dao.BensDao;
import dao.CategoriasDao;
import fragments.CategoriaAlertDialogFragment;

@SuppressLint("DefaultLocale")
public class BensActivity extends Activity implements OnCheckedChangeListener, OnItemSelectedListener{
	
	private BensDao daoBem;
	private CategoriasDao daoCategoria;
	private Bundle extras;
	private Bem bem;
	private Categoria categoria;
	private static String dataEscolhida;
		
	private EditText nomeBem;
	private EditText descricaoBem;
	private EditText valorBem;
	private static Button btChooseData;
	private List<Categoria> listaCategorias;
	
	private ArrayAdapter<Categoria> adapter;
	
	private Spinner spCategorias;
	private RadioGroup rGCategorias;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bens);
		
		daoBem = new BensDao(this);
		daoBem.open();
		
		daoCategoria = new CategoriasDao(this);
		daoCategoria.open();
		
		listaCategorias = daoCategoria.getAll();

		extras = getIntent().getExtras();
		
		nomeBem = (EditText) findViewById(R.id.nomeBem);
		descricaoBem = (EditText) findViewById(R.id.descricaoBem);
		valorBem = (EditText) findViewById(R.id.valorBem);
		spCategorias = (Spinner) findViewById(R.id.bensCategorias);
		rGCategorias = (RadioGroup) findViewById(R.id.rg_categorias);

		btChooseData = (Button) findViewById(R.id.btChooseData);
		
	}
	
	@Override
	protected void onResume() {
		daoBem.open();
		daoCategoria.open();
				
		adapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_dropdown_item, listaCategorias);
		spCategorias.setAdapter(adapter);

		if (extras != null) {
			bem = daoBem.getBem(extras.getLong("id"));
			nomeBem.setText(bem.getNome());
			descricaoBem.setText(bem.getDescricao());
			valorBem.setText(String.valueOf(bem.getValor()));
			dataEscolhida = bem.getData();
			if (bem.getIdCategoria() != 0) {
				try {
					categoria = daoCategoria.getCategoria(bem.getIdCategoria());
					if (categoria == null) {
						spCategorias.setSelection(adapter.getPosition(categoria));
					}
				} catch(SQLException s) {
					Log.w("daocategoria", "Erro a tentar pegar a categoria por idCategoria de Bem");
				}
			}
		}
		
		
		// Listeners
		rGCategorias.setOnCheckedChangeListener(this);
		spCategorias.setOnItemSelectedListener(this);
				
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bens, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.salvar_bem) {
			if (camposVazios()) {
				Toast.makeText(getBaseContext(), R.string.campos_vazios, Toast.LENGTH_LONG).show();
			} else {
				if (extras != null) {
					daoBem.update(bem);
					Toast.makeText(getBaseContext(), R.string.bem_saved, Toast.LENGTH_LONG).show();
					finish();
				} else {
					
					if (categoria == null || (rGCategorias.getCheckedRadioButtonId() == R.id.bens_sem_categoria)) {
						bem = daoBem.create(nomeBem.getEditableText().toString(),
								descricaoBem.getEditableText().toString(),
								Double.valueOf(valorBem.getEditableText().toString()),
								dataEscolhida);
								Toast.makeText(this, "Não contém categoria", Toast.LENGTH_SHORT).show();
					} else {
						bem = daoBem.create(nomeBem.getEditableText().toString(),
								descricaoBem.getEditableText().toString(),
								Double.valueOf(valorBem.getEditableText().toString()),
								dataEscolhida, categoria.getId());
					}
					
					Toast.makeText(getBaseContext(), R.string.bem_saved, Toast.LENGTH_LONG).show();
					finish();
				}
			}

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		daoBem.close();
		daoCategoria.close();
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		daoBem.close();
		daoCategoria.close();
		
		super.onDestroy();
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {

		if (rGCategorias.getCheckedRadioButtonId() == R.id.bens_outra_categoria) {
			
			DialogFragment dialog = new CategoriaAlertDialogFragment();
			dialog.show(getFragmentManager(), "categorias");
			
			rGCategorias.clearCheck();
			
		} else if(rGCategorias.getCheckedRadioButtonId() == R.id.bens_sem_categoria) {
			categoria = null;
		}
		


		
	}

	private boolean camposVazios() {
		if (nomeBem.getEditableText().toString().isEmpty() || 
				descricaoBem.getEditableText().toString().isEmpty() ||
				valorBem.getEditableText().toString().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
			int position, long id) {
		categoria = adapter.getItem(position);
		Toast.makeText(this, "Categoria selecionada " + categoria.getNome().toUpperCase(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		return;
	}

	
	public void showDatePicker(View v) {
		DatePickerFragment dpF = new DatePickerFragment();
		dpF.show(getFragmentManager(), "data");
	}
	
public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			dataEscolhida = String.valueOf(day) + "/"
					+ String.valueOf(month+1) + "/"
					+ String.valueOf(year);
			btChooseData.setText(dataEscolhida + "\n"
					+ getString(R.string.choose_data));
			
		}
	}
}
