package jp.ac.st.asojuku.original2014002;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	/**
	 * @param context呼び出しコンテキスト
	 * @param name 利用ＤＢ名
	 * @param factory カーソルファクトリー
	 * @param version DBバージョン
	 */
	
	public MySQLiteOpenHelper(Context content){
		
		super(content,"20130021201761.sqlite3",null,1);
		
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自動生成されたメソッド・スタブ
		db.execSQL("CREATE TABLE IF NOT EXISTS " + 
				"Hitokoto( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , phrase TEXT )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自動生成されたメソッド・スタブ
		db.execSQL("drop table Hitokoto");
		onCreate(db);
	}

}