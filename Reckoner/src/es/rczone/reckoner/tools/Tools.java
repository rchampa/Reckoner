package es.rczone.reckoner.tools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

public class Tools {

	public static String dateToString(Date date) {
		String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(date);
		return dateString;
	}

	public static void askConfirmation(Context context, String title,
			String message, int iconID, String positiveOption,
			String negativeOption, final IDialogOperations idialog) {

		AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);

		// Setting Dialog Title
		alertDialog2.setTitle(title);

		// Setting Dialog Message
		alertDialog2.setMessage(message);

		// Setting Icon to Dialog
		alertDialog2.setIcon(iconID);

		// Setting Positive "Yes" Btn
		alertDialog2.setPositiveButton(positiveOption,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						idialog.positiveOperation();
					}
				});
		// Setting Negative "NO" Btn
		alertDialog2.setNegativeButton(negativeOption,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						idialog.negativeOperation();

					}
				});

		// Showing Alert Dialog
		alertDialog2.show();

	}

	public static int powerOfTwo(int exp) {

		return (int) Math.pow(2, exp);
	}

	// Funcion que devuelve un objeto File de una imagen descargada desde el
	// servidor, sólo si no está descargada ya en el móvil.
	// Si ya estuviese descargada, simplemente se devuelven un File de la imagen
	// que está en la tarjeta SD
	// pathFolder /sdcard/app/tmp/
	public static File getImage(String pathFolder, String filename, String url) {
		String localFilename = new File(filename).getName();

		File img = new File(pathFolder + localFilename);

		// Se crea el directorio sino existe
		new File(pathFolder).mkdirs();

		// solo se descargan nuevas imagenes
		if (!img.exists()) {
			try {
				if("".equals(url) || url ==null)
					return null;
				URL imageUrl = new URL(url);
				InputStream in = imageUrl.openStream();
				OutputStream out = new BufferedOutputStream(
						new FileOutputStream(img));

				for (int b; (b = in.read()) != -1;) {
					out.write(b);
				}
				out.close();
				in.close();
			} catch (MalformedURLException e) {
				Log.e("Buscador", "Malformed URLException capturado : " + e);
				img = null;
			} catch (IOException e) {
				Log.e("Buscador", "IOException capturado : " + e);
				img = null;
			}
		}
		return img;
	}
	
	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}

}
