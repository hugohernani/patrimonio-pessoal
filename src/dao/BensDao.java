package dao;


import java.util.ArrayList;
import java.util.List;

import models.Bem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BensDao {
	
	private SQLiteDatabase database;
	private String[] columns = { PPCustomSQLiteOpenHelper.BENS_COLUMN_ID,
			PPCustomSQLiteOpenHelper.BENS_COLUMN_NOME, PPCustomSQLiteOpenHelper.BENS_COLUMN_DESCRICAO,
			PPCustomSQLiteOpenHelper.BENS_COLUMN_VALOR, PPCustomSQLiteOpenHelper.BENS_COLUMN_DATE,
			PPCustomSQLiteOpenHelper.BENS_COLUMN_CATEGORIAS};
	private PPCustomSQLiteOpenHelper sqliteOpenHelper;
	
	public BensDao(Context context) {
		sqliteOpenHelper = new PPCustomSQLiteOpenHelper(context);
	}
	
	public void open() throws SQLException {
		database = sqliteOpenHelper.getWritableDatabase();
	}
	
	public void close() {
		sqliteOpenHelper.close();
	}
	
	public Bem create(String nome, String descricao, double valor, String data, long idCategoria) {
		ContentValues values = new ContentValues();
		values.put(PPCustomSQLiteOpenHelper.BENS_COLUMN_NOME, nome);
		values.put(PPCustomSQLiteOpenHelper.BENS_COLUMN_DESCRICAO, descricao);
		values.put(PPCustomSQLiteOpenHelper.BENS_COLUMN_VALOR, valor);
		values.put(PPCustomSQLiteOpenHelper.BENS_COLUMN_DATE, data);
		values.put(PPCustomSQLiteOpenHelper.BENS_COLUMN_CATEGORIAS, idCategoria);
		long insertId = database.insert(PPCustomSQLiteOpenHelper.TABLE_BENS,
				null, values);
		Cursor cursor = database.query(PPCustomSQLiteOpenHelper.TABLE_BENS, 
				columns, PPCustomSQLiteOpenHelper.BENS_COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Bem newBem = new Bem();
		newBem.setId(cursor.getLong(0));
		newBem.setNome(cursor.getString(1));
		newBem.setDescricao(cursor.getString(2));
		newBem.setValor(cursor.getDouble(3));
		newBem.setData(cursor.getString(4));
		newBem.setIdCategoria(cursor.getLong(5));
		cursor.close();
		return newBem;
	}

	public Bem create(String nome, String descricao, double valor, String data) {
		ContentValues values = new ContentValues();
		values.put(PPCustomSQLiteOpenHelper.BENS_COLUMN_NOME, nome);
		values.put(PPCustomSQLiteOpenHelper.BENS_COLUMN_DESCRICAO, descricao);
		values.put(PPCustomSQLiteOpenHelper.BENS_COLUMN_VALOR, valor);
		values.put(PPCustomSQLiteOpenHelper.BENS_COLUMN_DATE, data);
		long insertId = database.insert(PPCustomSQLiteOpenHelper.TABLE_BENS,
				null, values);
		Cursor cursor = database.query(PPCustomSQLiteOpenHelper.TABLE_BENS, 
				columns, PPCustomSQLiteOpenHelper.BENS_COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Bem newBem = new Bem();
		newBem.setId(cursor.getLong(0));
		newBem.setNome(cursor.getString(1));
		newBem.setDescricao(cursor.getString(2));
		newBem.setValor(cursor.getDouble(3));
		newBem.setData(cursor.getString(4));
		cursor.close();
		return newBem;
	}

	
	public void delete(Bem bem) {
		long id = bem.getId();
		database.delete(PPCustomSQLiteOpenHelper.TABLE_BENS,
				PPCustomSQLiteOpenHelper.BENS_COLUMN_ID + " = " + id,
				null);
	}
	
	public void deleteAll() {
		database.delete(PPCustomSQLiteOpenHelper.TABLE_BENS, "1 = 1", null);
	}
	
	public List<Bem> getAll() {
		List<Bem> bens = new ArrayList<Bem>();
		
		Cursor cursor = database.query(PPCustomSQLiteOpenHelper.TABLE_BENS,
				columns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Bem bem = new Bem();
			bem.setId(cursor.getLong(0));
			bem.setNome(cursor.getString(1));
			bem.setValor(cursor.getDouble(3));
			bens.add(bem);
			cursor.moveToNext();
		}
		
		cursor.close();
		return bens;
	}
	
	public List<Bem> getBemByCategory(long idCategoria) {
		List<Bem> bens = new ArrayList<Bem>();

		Cursor cursor = database.query(PPCustomSQLiteOpenHelper.TABLE_BENS,
				columns, PPCustomSQLiteOpenHelper.BENS_COLUMN_CATEGORIAS+"="+idCategoria, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Bem bem = new Bem();
			bem.setId(cursor.getLong(0));
			bem.setNome(cursor.getString(1));
			bem.setDescricao(cursor.getString(2));
			bem.setValor(cursor.getDouble(3));
			bem.setIdCategoria(idCategoria);
			
			bens.add(bem);
			cursor.moveToNext();
		}
		
		cursor.close();
		return bens;
	}
	
	public Bem getBem(long id) {
		Cursor cursor = database.query(PPCustomSQLiteOpenHelper.TABLE_BENS,
				columns, PPCustomSQLiteOpenHelper.BENS_COLUMN_ID+"="+id, null, null, null, null);
		
		Bem bem = new Bem();

		cursor.moveToFirst();
		bem.setId(id);
		bem.setNome(cursor.getString(1));
		bem.setDescricao(cursor.getString(2));
		bem.setValor(cursor.getDouble(3));
		bem.setData(cursor.getString(4));
		bem.setIdCategoria(cursor.getLong(5));
		cursor.moveToNext();
		cursor.close();

		return bem;
		
	}

	public Bem update(Bem bem) {
		
		ContentValues values = new ContentValues();
		values.put(PPCustomSQLiteOpenHelper.BENS_COLUMN_NOME, bem.getNome());
		values.put(PPCustomSQLiteOpenHelper.BENS_COLUMN_DESCRICAO, bem.getDescricao());
		values.put(PPCustomSQLiteOpenHelper.BENS_COLUMN_VALOR, bem.getValor());
		values.put(PPCustomSQLiteOpenHelper.BENS_COLUMN_DATE, bem.getData());
		values.put(PPCustomSQLiteOpenHelper.BENS_COLUMN_CATEGORIAS, bem.getIdCategoria());
		database.update(PPCustomSQLiteOpenHelper.TABLE_BENS, values,
				PPCustomSQLiteOpenHelper.BENS_COLUMN_ID + "=" + bem.getId(),
				null);
		return bem;
		
	}

	public List<Bem> getBemSemCategoria() {
		List<Bem> bens = new ArrayList<Bem>();

		Cursor cursor = database.query(PPCustomSQLiteOpenHelper.TABLE_BENS,
				columns, PPCustomSQLiteOpenHelper.BENS_COLUMN_CATEGORIAS+" IS NULL", null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Bem bem = new Bem();
			bem.setId(cursor.getLong(0));
			bem.setNome(cursor.getString(1));
			bem.setDescricao(cursor.getString(2));
			bem.setValor(cursor.getDouble(3));
			
			bens.add(bem);
			cursor.moveToNext();
		}
		
		cursor.close();
		return bens;
	}
}
