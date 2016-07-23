package yamblz.hack.learning.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import yamblz.hack.learning.R;
import yamblz.hack.learning.db.Helper;
import yamblz.hack.learning.db.Word;
import yamblz.hack.learning.network.*;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Word> {
    private static final String LANG_DIR = "Lang";
    private static final String TEXT = "Text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        makeRequest(Helper.DIRECTION_EN_RU, "cat");
        makeRequest(Helper.DIRECTION_RU_EN, "кошка");
        setTitle(R.string.title);
//        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
//        setSupportActionBar(mActionBarToolbar);
        if (getFragmentManager().findFragmentById(R.id.fragment_content) == null) {
            Fragment fragment = ListFragment.newInstance();
            FragmentTransaction fTrans = getFragmentManager().beginTransaction();
            fTrans.add(R.id.fragment_content, fragment);
            fTrans.addToBackStack(null);
            fTrans.commit();
        }
    }

    private void makeRequest(int lang, String text) {
        Bundle args = new Bundle();
        args.putString(TEXT, text);
        args.putInt(LANG_DIR, lang);
        getLoaderManager().initLoader(0, args, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public Loader<Word> onCreateLoader(int id, Bundle args) {
        return new JsonLoader(this,args.getInt(LANG_DIR), args.getString(TEXT));
    }

    @Override
    public void onLoadFinished(Loader<Word> loader, Word data) {
        if (data != null) {
//            ((TextView)findViewById(R.id.hello)).setText(data.getWord() + " -> " + data.getTranslation());
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


}
