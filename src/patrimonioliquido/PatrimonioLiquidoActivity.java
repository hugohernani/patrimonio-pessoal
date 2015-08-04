package patrimonioliquido;

import com.example.patrimoniopessoal.R;
import com.example.patrimoniopessoal.R.layout;
import com.example.patrimoniopessoal.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PatrimonioLiquidoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patrimonio_liquido);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patrimonio_liquido, menu);
		return true;
	}

}
