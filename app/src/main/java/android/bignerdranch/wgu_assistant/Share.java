package android.bignerdranch.wgu_assistant;

import android.content.Context;
import android.content.Intent;

public class Share {
    public static void shareString(Context context, String stringToShare){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, stringToShare);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
