package dao;


import java.util.ArrayList;
import java.util.List;

import models.Categoria;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CategoriasDao {
	
	private SQLiteDatabase database;
	private String[] columns = { PPCustomSQLiteOpenHelper.CATEGORIAS_COLUMN_ID,
			PPCustomSQLiteOpenHelper.CATEGORIAS_COLUMN_NOME};
	private PPCustomSQLiteOpenHelper sqliteOpenHelper;
	
	public CategoriasDao(Context context) {
		sqliteOpenHelper = new PPCustomSQLiteOpenHelper(context);
	}
	
	public void open() throws SQLException {
		database = sqliteOpenHelper.getWritableDatabase();
	}
	
	public void close() {
		sqliteOpenHelper.close();
	}
	
	public Categoria create(String nome) {
		ContentValues values = new ContentValues();
		values.put(PPCustomSQLiteOpenHelper.CATEGORIAS_COLUMN_NOME, nome);
		long insertId = database.insert(PPCustomSQLiteOpenHelper.TABLE_CATEGORIAS,
				null, values);
		Cursor cursor = database.query(PPCustomSQLiteOpenHelper.TABLE_CATEGORIAS, 
				columns, PPCustomSQLiteOpenHelper.CATEGORIAS_COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.close();
		Categoria newCategoria = new Categoria();
		newCategoria.setId(insertId);
		newCategoria.setNome(nome);
		return newCategoria;
	}
	
	public void delete(Categoria categoria) {
		long id = categoria.getId();
		database.delete(PPCustomSQLiteOpenHelper.TABLE_CATEGORIAS,
				PPCustomSQLiteOpenHelper.CATEGORIAS_COLUMN_ID + " = " + id,
				null);
	}

	public void deleteAll() {
		database.delete(PPCustomSQLiteOpenHelper.TABLE_CATEGORIAS, "1 = 1", null);
	}

	
	public List<Categoria> getAll() {
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		Cursor cursor = database.query(PPCustomSQLiteOpenHelper.TABLE_CATEGORIAS,
				columns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Categoria categoria = new Categoria();
			categoria.setId(cursor.getLong(0));
			categoria.setNome(cursor.getString(1));
			categorias.add(categoria);
			cursor.moveToNext();
		}
		
		cursor.close();
		return categorias;
	}
	
	public Categoria getCategoria(long id) {
		Cursor cursor = database.query(PPCustomSQLiteOpenHelper.TABLE_CATEGORIAS,
				columns, PPCustomSQLiteOpenHelper.CATEGORIAS_COLUMN_ID+"="+id, null, null, null, null);
		
		Categoria categoria = new Categoria();

		cursor.moveToNext();
		categoria.setId(id);
		categoria.setNome(cursor.getString(1));
		cursor.close();
		System.out.println("Categoria id/Nome (getCategoria): " + categoria.getId() + "/" + categoria.getNome());

		return categoria;
		
	}

	public Categoria update(Categoria categoria) {
		
		ContentValues values = new ContentValues();
		values.put(PPCustomSQLiteOpenHelper.CATEGORIAS_COLUMN_NOME, categoria.getNome());
		database.update(PPCustomSQLiteOpenHelper.TABLE_CATEGORIAS, values,
				PPCustomSQLiteOpenHelper.CATEGORIAS_COLUMN_ID + "=" + categoria.getId(),
				null);
		return categoria;
		
	}
}