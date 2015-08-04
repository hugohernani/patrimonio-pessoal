package bens;

import java.util.HashMap;
import java.util.List;

import com.example.patrimoniopessoal.R;
import com.example.patrimoniopessoal.R.id;
import com.example.patrimoniopessoal.R.layout;

import dao.BensDao;

import models.Bem;
import models.Categoria;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class ExpandableListAdapter extends BaseExpandableListAdapter{
	
	public Context _context;
	private List<Categoria> _listDataHeader;
	
	private HashMap<Categoria, List<Bem>> _listDataChild;
	
//	private Bem BemClick = new Bem();
	private BensDao daoBem;
	
	public ExpandableListAdapter(Context context,
			List<Categoria> listDataHeader,
			HashMap<Categoria, List<Bem>> listDataChild) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listDataChild;
		daoBem = new BensDao(context);
		daoBem.open();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).
				get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
		
		final Bem bemChild = (Bem) getChild(groupPosition, childPosition);
		
		if (convertView == null) {
			LayoutInflater inflate = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflate.inflate(R.layout.list_item, null);
		}
		
		TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
		
		txtListChild.setText(bemChild.toString());
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
		Categoria headerTitle = (Categoria) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_group, null);
		}
		
		// Pega o total de bens na categoria
		Double totalCategoria = 0.0;
		List<Bem> bensByCategory = daoBem.getBemByCategory(headerTitle.getId());
		for (Bem bem : bensByCategory) {
			totalCategoria += bem.getValor();
		}
		
		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle.getNome() + " |--> Total: " + String.valueOf(totalCategoria));
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
/*		BemClick = daoBem.getBem(arg1);
		
		Intent intent = new Intent(_context, ViewBemActivity.class);
		intent.putExtra("id", BemClick.getId());
		Activity a = new Activity();
		a.startActivity(intent);
		a.finish();
*/		return false;
	}

}
