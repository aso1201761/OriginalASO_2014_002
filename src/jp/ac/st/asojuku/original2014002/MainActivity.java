package jp.ac.st.asojuku.original2014002;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		//ボタンをIDで探してボタン変数をつくる
		Button btnMente = (Button)findViewById(R.id.btnMente);
		Button btnReg = (Button)findViewById(R.id.btnReg);
		Button btnChk = (Button)findViewById(R.id.btnChk);
		//ボタン変数にリスナーを登録する
		btnMente.setOnClickListener(this);
		btnReg.setOnClickListener(this);
		btnChk.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		
		switch(v.getId()){//どのボタンが押されたか判定
		
		case R.id.btnMente://btnMenteが押された
			//インテントのインスタンス生成
			Intent intent =new Intent(MainActivity.this, MenteActivity.class);
			//次画面のアクティビティ起動
			startActivity(intent);
			break;
		case R.id.btnChk://btnChkが押された
			//インテントのインスタンス生成
			Intent intentChk =new Intent(MainActivity.this, WordActivity.class);
			//次画面のアクティビティ起動
			startActivity(intentChk);
			break;
		case R.id.btnReg://btnRegが押された
//			EditText etv = (EditText)findViewById(R.id.edtHitokoto);
//			String inputMsg = etv.getText().toString();
			break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
