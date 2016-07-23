package yamblz.hack.learning.network;

/**
 * Created by vorona on 23.07.16.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import yamblz.hack.learning.db.Helper;
import yamblz.hack.learning.db.Word;

/**
 * Created by vorona on 19.07.16.
 */

public class JsonLoader extends AsyncTaskLoader<Word> {
    private Word response;
    private String request;
    private int dir;
    private static final String key =
            "dict.1.1.20160722T221101Z.cd274f79e52e36f9.52ff53c690429e4d02ad3adf9018e3d4d6ddb6aa";


    public JsonLoader(Context context, int dir, String request) {
        super(context);
        this.request = request;
        this.dir = dir;
    }

    /**
     * This is where the bulk of our work is done.  This function is
     * called in a background thread and should generate a new set of
     * data to be published by the loader.
     */
    @Override
    public Word loadInBackground() {
        try {
            response = getResponse(dir, request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    private String makeRequest(int lang, String text) {
        String direction = (lang == Helper.DIRECTION_EN_RU) ? "en-ru" : "ru-en";
        return key+"&lang="+direction+"&text="+text;
    }

    private Word getResponse(int lang, String request) throws IOException {

        URL url = new URL("https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=" + makeRequest(lang, request));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream in = connection.getInputStream();
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(in));
            Word res = readJson(reader);
            reader.close();
            return res;
        } finally {
            if (in != null) {
                in.close();
            }
            connection.disconnect();
        }

    }

    /**
     * Combine all performers
     */
    private Word readJson(JsonReader reader) throws IOException {
        Word cur = new Word();
        reader.beginObject();
        while (reader.hasNext()) {
            cur = takeData(reader);
        }
        reader.endObject();
        return cur;
    }

    /**
     * Read information about individual performers
     */
    private Word takeData(JsonReader reader) throws IOException {
        Word res = new Word();

        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "def":
                    reader.beginArray();
                    while (reader.hasNext()) {
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String n = reader.nextName();
                            switch (n) {
                                case "text":
                                    res.setWord(reader.nextString());
                                    break;
                                case "tr":
                                    reader.beginArray();
                                    while (reader.hasNext()) {
                                        reader.beginObject();
                                        name = reader.nextName();
                                        if (name.equals("text")) {
                                            res.addTranslation(reader.nextString());
                                        }
                                        while (reader.hasNext())
                                            reader.skipValue();
                                        reader.endObject();
                                    }

                                    reader.endArray();
                                    break;
                                default:
                                    reader.skipValue();
                            }
                        }
                        reader.endObject();
                    }
                    reader.endArray();
                    break;
                default:
                    reader.skipValue();
            }
        }
        return res;
    }


    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override
    public void deliverResult(Word sin) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (sin != null) {
                onReleaseResources(sin);
            }
        }
        response = sin;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(sin);
        }
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        if (response != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(response);
        }

        if (takeContentChanged() || response == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(Word singers) {
        super.onCanceled(singers);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(singers);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (response != null) {
            onReleaseResources(response);
            response = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     * @param singers
     */
    protected void onReleaseResources(Word singers) {
        // For a simple List<> there is nothing to do.  For something
        // like a Cursor, we would close it here.
    }
}