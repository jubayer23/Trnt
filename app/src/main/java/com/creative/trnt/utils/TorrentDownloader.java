package com.creative.trnt.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import java.util.List;

/**
 * Created by comsol on 26-Aug-17.
 */

public class TorrentDownloader {

    public void openMagneturi(String url, final Context c){
        Log.e("TAG","openMagneturi magnet");

        if(url.startsWith("magnet:")) {
            Log.e("TAG","url starts with magnet");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            PackageManager manager = c.getPackageManager();
            List<ResolveInfo> infos = manager.queryIntentActivities(browserIntent, 0);
            if (infos.size() > 0) {
               // c.startActivity(browserIntent);
               openDialogForCopyOrDownload(browserIntent,c);
                Log.e("TAG","yes act to handle");
            } else {
                Log.e("TAG","No act to handle");
                openDialogForInstallUtorrentApp(c);

            }
        }else{
            Log.e("TAG","url does not starts with magnet");

        }
    }

    public void openDialogForInstallUtorrentApp(final Context c){


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(c);

        alertDialog.setTitle("Error");

        alertDialog.setMessage("You dont have any utorren software in your device. Do you want to download it or just want to download the torrent file in your device?");

        alertDialog.setPositiveButton("Download uTorrent App", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startBrowser("https://play.google.com/store/apps/details?id=com.utorrent.client",c);
                dialog.cancel();

            }
        });

        alertDialog.setNegativeButton("Download torrent file", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               // startBrowser("https://play.google.com/store/apps/details?id=com.utorrent.client",c);
                dialog.cancel();

            }
        });

        alertDialog.show();
    }

    public void openDialogForCopyOrDownload(final Intent intent, final Context c){


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(c);

        alertDialog.setTitle("Error");

        alertDialog.setMessage("Do you want to download torrent file or you want to direct download using uTorrent android app?");

        alertDialog.setPositiveButton("Download using uTorrent", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                c.startActivity(intent);
                dialog.cancel();

            }
        });

        alertDialog.setNegativeButton("Download torrent file", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // startBrowser("https://play.google.com/store/apps/details?id=com.utorrent.client",c);
                dialog.cancel();

            }
        });

        alertDialog.show();
    }

    public void startBrowser(String url, Context c){
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "https://" + url;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        c.startActivity(browserIntent);
    }
}
