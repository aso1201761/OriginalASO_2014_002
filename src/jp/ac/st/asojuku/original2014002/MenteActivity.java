package jp.ac.st.asojuku.original2014002;

import android.app.Activity;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MenteActivity extends Activity implements 
	View.OnClickListener, AdapterView.OnItemClickListener{
	
	SQLiteDatabase sdb = null;
	
	MySQLiteOpenHelper helper = null;
	
	//リストにて選択したHitokotoテーブルのレコードの「_id」カラム値を保持する変数の宣言
	int selectedID = -1;
	//リストにて選択した行番号を保持する変数の宣言
	int lastPosition = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mente);
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		
		//各View部品を操作するidを取得
		Button btnDelete = (Button)findViewById(R.id.btnDELETE);
		Button btnMainte_Back = (Button)findViewById(R.id.btnMAINTE_BACK);
		ListView lstHitokoto= (ListView)findViewById(R.id.LvHITOKOTO);
		
		//各ButtonにOnClickListenerをセット
		btnDelete.setOnClickListener(this);
		btnMainte_Back.setOnClickListener(this);
		
		//ListViewにOnItemListenerをセット
		lstHitokoto.setOnItemClickListener(this);
		
		//ListViewにDBの値をセット
		this.setDBValuetoList(lstHitokoto);
	}
	
	/*
	 * 引数のListViewにDBのデータをセット
	 *@param lstHitokoto 対象となるListView
	 */
	
	private void setDBValuetoList(ListView lstHitokoto){
		SQLiteCursor cursor = null;
		
		//クラスのフィールド変数がNULLなら、データベース空間オープン
		if(sdb == null){
			helper = new MySQLiteOpenHelper(getApplicationContext());
		}
		try{
			sdb = helper.getWritableDatabase();
		}catch(SQLException e){
			//異常終了
			Log.e("ERROR",e.toString());
		}
		// MySQLiteOpenHelperにSELECT分を実行させて結果のカーソルを受け取る
		cursor = this.helper.selectHitokotoList(sdb);
		
		// dblayout: ListViewにさらにレイアウトを指定するもの
		int db_layout = android.R.layout.simple_list_item_activated_1;
		// from: カーソルからListviewに指定するカラムの値を指定するもの
		String[] from ={"phrase"};
		//to: Listviewの中に指定したsb_layoutに配置する、各行のview部品のid
		int[] to = new int[]{android.R.id.text1};
		
		//ListViewにセットするアダプターを生成
		//カーソルをもとに、fromの列から、toのViewへ値のマッピングが行われる
		SimpleCursorAdapter adapter =
			new SimpleCursorAdapter(this,db_layout,cursor,from,to,0);
			
		//アダプターを設定します
			lstHitokoto.setAdapter(adapter);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO 自動生成されたメソッド・スタブ
		// 前に選択中の行があれば、背景色を透明にする
		if(this.selectedID!=-1){
			parent.getChildAt(this.lastPosition).setBackgroundColor(0);
		}
		// 選択中の行の背景色をグレーにする
		view.setBackgroundColor(android.graphics.Color.LTGRAY);
		
		// 選択行のレコードを指し示すカーソルを取得
		SQLiteCursor cursor = (SQLiteCursor)parent.getItemAtPosition(position);
		// カーソルのレコードから、「_id」の値を取得して記憶
		this.selectedID = cursor.getInt(cursor.getColumnIndex("_id"));
		// 何行目を選択したかも記憶
		this.lastPosition = position;
		
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()){ //どのボタンが押されたか判定
		case R.id.btnDELETE: //削除ボタンが押された
			
			// 選択行があれば
			if(this.selectedID != -1){
				this.deleteFromHitokoto(this.selectedID);
				ListView lstHitokoto =(ListView)findViewById(R.id.LvHITOKOTO);
				// ListViewにDBをセット
				this.setDBValuetoList(lstHitokoto);
				// 選択行を忘れる
				this.selectedID = -1;
				this.lastPosition = -1;
			}else{
				//なければ、トースト（簡易メッセージ）を表示
				Toast.makeText(MenteActivity.this,"削除する行を選んでください",Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnMAINTE_BACK: //戻るボタンが押された
			//今の画面Activityを消して、前の画面Activityに戻る
			finish();
			break;
		}
		
	}
	
	/**
	 * Hitokotoテーブルから、引数で指定した「_id」と同じ値を持つレコードを削除
	 * @param id 指定する値
	 */
	private void deleteFromHitokoto(int id){
		//クラスのフィールド変数がNULLなら、データベース空間オープン
				if(sdb == null){
					helper = new MySQLiteOpenHelper(getApplicationContext());
				}
				try{
					sdb = helper.getWritableDatabase();
				}catch(SQLException e){
					//異常終了
					Log.e("ERROR",e.toString());
				}
				//MySQLiteOpenHelperにDELETE文を実行させる
				this.helper.deleteHitokoto(sdb, id);
	}
}