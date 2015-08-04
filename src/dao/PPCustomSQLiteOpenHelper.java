package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PPCustomSQLiteOpenHelper extends SQLiteOpenHelper{
	
	
	// Campos tabela Bens
	public static final String TABLE_BENS = "bens";
	public static final String BENS_COLUMN_ID = "_id";
	public static final String BENS_COLUMN_NOME = "nome";
	public static final String BENS_COLUMN_DESCRICAO = "descricao";
	public static final String BENS_COLUMN_VALOR = "valor";
	public static final String BENS_COLUMN_DATE = "data";
	public static final String BENS_COLUMN_CATEGORIAS = "categorias_id";
	
	// Campos tabela Categorias
	public static final String TABLE_CATEGORIAS = "categorias";
	public static final String CATEGORIAS_COLUMN_ID = "_id";
	public static final String CATEGORIAS_COLUMN_NOME = "nome";

	// Campos database
	private static final String DATABASE_NAME = "PatrimonioPessoal.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement to Categorias
	private static final String DATABASE_CREATE_CATEGORIA = "create table "
			+ TABLE_CATEGORIAS + "(" + CATEGORIAS_COLUMN_ID
			+ " integer primary key autoincrement, " + CATEGORIAS_COLUMN_NOME
			+ " text not null);";

	// Database creation sql statement
	private static final String DATABASE_CREATE_BENS = "create table "
			+ TABLE_BENS + "(" + BENS_COLUMN_ID
			+ " integer primary key autoincrement, " + BENS_COLUMN_NOME
			+ " text not null, " + BENS_COLUMN_DESCRICAO 
			+ " text not null, " + BENS_COLUMN_VALOR
			+ " real not null, " + BENS_COLUMN_DATE
			+ " text not null, " + BENS_COLUMN_CATEGORIAS
			+ " integer REFERENCES " + TABLE_CATEGORIAS + ");";
	
	public PPCustomSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_CATEGORIA);
		db.execSQL(DATABASE_CREATE_BENS);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIAS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BENS);
		onCreate(db);
	}

}
