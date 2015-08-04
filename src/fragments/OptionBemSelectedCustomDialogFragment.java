package fragments;

import models.Bem;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import bens.BensActivity;
import bens.ListBensActivity;

import com.example.patrimoniopessoal.R;

import dao.BensDao;

public class OptionBemSelectedCustomDialogFragment extends DialogFragment{
	
	private BensDao dao;	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		dao = new BensDao(getActivity());
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
		builder.setCancelable(true);
		builder.setView(inflater.inflate(R.layout.custom_dialog, null))
			.setPositiveButton(R.string.bt_alterar_label, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(getActivity(), BensActivity.class);
					System.out.println("Bem Id(Custom): " + ListBensActivity.id);
					intent.putExtra("id", ListBensActivity.id); // é necessário passar o id para que possa verificar a existência de um id para alteração, podendo diferenciar da adição.
					startActivity(intent);
					Toast.makeText(getActivity(), "Altere as informações", Toast.LENGTH_LONG).show();
				}
			})
			.setNegativeButton(R.string.bt_remover_label, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dao.delete(dao.getBem(ListBensActivity.id));
					Toast.makeText(getActivity(), "Item removido", Toast.LENGTH_LONG).show();
					getActivity().recreate();
					dismiss();
				}
			});
		return builder.create();
	}	

}
