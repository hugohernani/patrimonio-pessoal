package fragments;

import models.Categoria;

import com.example.patrimoniopessoal.R;

import dao.CategoriasDao;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.EditText;
import android.widget.Toast;

public class CategoriaAlertDialogFragment extends DialogFragment{
	
	private CategoriasDao dao;
	private EditText nomeCategoria;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		dao = new CategoriasDao(getActivity());
		dao.open();
				
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onPause() {
		dao.close();
		super.onPause();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View v = getActivity().getLayoutInflater().inflate(R.layout.add_categoria, null,false);
		builder.setCancelable(true);
		builder.setView(v)
		.setPositiveButton(R.string.label_add, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				nomeCategoria = (EditText) v.findViewById(R.id.editNameCategoria);
				dao.create(nomeCategoria.getEditableText().toString());
				Toast.makeText(getActivity(), R.string.categoria_salva, Toast.LENGTH_SHORT).show();
				Toast.makeText(getActivity(), "Selecione a nova categoria na lista", Toast.LENGTH_LONG).show();
			}
		})
		.setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Toast.makeText(getActivity(), "Cadastro de categoria cancelado", Toast.LENGTH_LONG).show();
			}
		});
		return builder.create();
	}	
	@Override
	public void onDestroy() {
		if (this.nomeCategoria != null) getActivity().recreate();
		super.onDestroy();
	}

}
