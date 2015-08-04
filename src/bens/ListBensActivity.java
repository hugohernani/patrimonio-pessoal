package bens;

import java.util.List;

import models.Bem;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrimoniopessoal.R;

import dao.BensDao;
import fragments.OptionBemSelectedCustomDialogFragment;

public class ListBensActivity extends ListActivity implements OnItemClickListener, OnItemLongClickListener{
	
	private BensDao dao;
	TextView tituloListActivity;
	public static long id;
	
	private TextView valorTotal;
	private double total;

	ArrayAdapter<Bem> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_bens);

		valorTotal = (TextView) findViewById(R.id.valor_total_bens);
		
		tituloListActivity = (TextView) findViewById(R.id.tituloActivity);
		
		dao = new BensDao(this);
		dao.open();
		
	}
	
	@Override
	protected void onResume() {
		dao.open();
		super.onResume();
		
		
		tituloListActivity.setText("Bens");
		
		List<Bem> bens = dao.getAll();
		
		total = 0;				
		for(int i=0; i < bens.size(); i++) {
			total += bens.get(i).getValor();
		}
		
		adapter = new ArrayAdapter<Bem>(this,
				android.R.layout.simple_list_item_1, bens);
		setListAdapter(adapter);
				
		getListView().setOnItemClickListener(this);
		getListView().setOnItemLongClickListener(this);
		
		valorTotal.setText(String.format(getString(R.string.bens_valor_total), total));
		
	}
	
	@Override
	protected void onPause() {
		dao.close();
		super.onPause();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list_bens, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.add_bem) {
			startActivity(new Intent(this, BensActivity.class));
		} else if (item.getItemId() == R.id.change_view) {
			startActivity(new Intent(this, ListaBens_byCategory_Activity.class));
			finish();
		} else if (item.getItemId() == R.id.zerar_bens) {
			dao.deleteAll();
			Toast.makeText(this, "Bens apagados", Toast.LENGTH_LONG).show();
			recreate();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Bem bem = adapter.getItem(arg2);
		id = bem.getId();
		
		Intent intent = new Intent(ListBensActivity.this, ViewBemActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Bem bem = adapter.getItem(arg2);
		id = bem.getId();

		DialogFragment dialog = new OptionBemSelectedCustomDialogFragment();
		
		dialog.show(getFragmentManager(), "custom");
		return true;
	}
}
