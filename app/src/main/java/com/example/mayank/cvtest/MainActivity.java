package com.example.mayank.cvtest;


import android.content.ContentValues;
import android.content.res.AssetManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import org.opencv.core.*;
import org.opencv.android.*;

import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity {
    ImageView viewImage;
    Button b;
    double ang;
    String picturePath;
    Mat source;

    private static final int REQUEST_GALLERY = 0;
    private static final int REQUEST_CAMERA = 1;

    public String textt;


    private static final String TAG = MainActivity.class.getSimpleName();

    private TessBaseAPI baseAPI;
    private Uri imageUri;
   // public static int flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!OpenCVLoader.initDebug()) {
            Log.e("error with installation","handle it man");
        }
        b=(Button)findViewById(R.id.btnSelectPhoto);
        viewImage=(ImageView)findViewById(R.id.viewImage);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

       /* b1=(Button)findViewById(R.id.btn1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
               *//* ang= skew();
                deskew(source,ang);*//*

            }
        });*/

        baseAPI = new TessBaseAPI();
        File externalStorageDirectory = Environment.getExternalStorageDirectory();

        File appDir = new File(externalStorageDirectory, "ocrsample");
        if (!appDir.isDirectory())
            appDir.mkdir();

        final File baseDir = new File(appDir, "tessdata");
        if (!baseDir.isDirectory())
            baseDir.mkdir();

        copyAssets();
        baseAPI.init(appDir.getPath(), "eng");
        findViewById(R.id.choose_from_gallery).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    inspect();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                    /*Log.e("inside","onclickcamera");
                    Intent intent = new Intent();
                    intent.setType("image*//*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, REQUEST_GALLERY);
*/
            }
        });


    //  the  text to voice part





    }




    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Log.e("inside Select Img","inside first if");
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Log.e("inside Select Img","inside elseIf");
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    /*  private void skew(const char* filename) {

  // Load in grayscale.
          cv::Mat src = cv::imread(filename, 0);
          cv::Size size = src.size();
          cv::bitwise_not(src, src);
          std::vector<cv::Vec4i> lines;
          cv::HoughLinesP(src, lines, 1, CV_PI/180, 100, size.width / 2.f, 20);
          cv::Mat disp_lines(size, CV_8UC1, cv::Scalar(0, 0, 0));
          double angle = 0.;
          unsigned nb_lines = lines.size();
          for (unsigned i = 0; i < nb_lines; ++i)
          {
              cv::line(disp_lines, cv::Point(lines[i][0], lines[i][1]),
              cv::Point(lines[i][2], lines[i][3]), cv::Scalar(255, 0 ,0));
              angle += atan2((double)lines[i][3] - lines[i][1],
                      (double)lines[i][2] - lines[i][0]);
          }
          angle /= nb_lines; // mean angle, in radians.

          std::cout << "File " << filename << ": " << angle * 180 / CV_PI << std::endl;

          cv::imshow(filename, disp_lines);
          cv::waitKey(0);
          cv::destroyWindow(filename);
      }


  */
   /* public double skew() {
        source = Highgui.imread(picturePath, 0);
        Size size = source.size();
        Core.bitwise_not(source, source);
        Mat lines = new Mat();
        Imgproc.HoughLinesP(source, lines, 1, Math.PI / 180, 100, size.width / 2.f, 20);
        double angle = 0.;
        for (int i = 0; i < lines.height(); i++) {
            for (int j = 0; j < lines.width(); j++) {
                angle += Math.atan2(lines.get(i, j)[3] - lines.get(i, j)[1], lines.get(i, j)[2] - lines.get(i, j)[0]);
            }
        }
        angle /= lines.size().area();
        angle = angle * 180 / Math.PI;
        return angle;
    }
    void deskew(Mat src, double angle) {
        Point center = new Point(src.width()/2, src.height()/2);
        Mat rotImage = Imgproc.getRotationMatrix2D(center, angle, 1.0);
        //1.0 means 100 % scale
        Size size = new Size(src.width(), src.height());
        Imgproc.warpAffine(src, src, rotImage, size, Imgproc.INTER_LINEAR + Imgproc.CV_WARP_FILL_OUTLIERS);
        Bitmap imgda= Bitmap.createBitmap(src.cols(),src.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(src,imgda);
        viewImage.setImageBitmap(imgda);

    }*/




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Mat source1;
        String path=null;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Log.e("onActRes","reqCode 1");
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);


                    Bitmap bb;
                    bb = getResizedBitmap(bitmap, 150, 150);
                    viewImage.setImageBitmap(bb);
                    /*ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, title);
                    values.put(MediaStore.Images.Media.DESCRIPTION, description);
                    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    values.put(MediaStore.MediaColumns.DATA, filepath);

                    getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
*/
                   /* String nameImg = "nice"
                    path = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"nice.jpg", "drawing" );
                    Log.e("inside try ",path+"");
*/


                             /*android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";*/
                   // f.delete();
                    OutputStream outFile = null;
                    File file = new File(path);
                    Log.e("outStrm",file+"");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        Log.e("the outfile",outFile+"");
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.e("path img  gallery...", picturePath+"");
                viewImage.setImageBitmap(thumbnail);

                Log.e("NLN","before");
               // System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
                Log.e("before","COLOR_RGB2GRAY");
                Mat src_img,src_grey,src_blur,src_thresh,src_erode, src_dilate,dest_img;
                File root = Environment.getExternalStorageDirectory();
                File file = new File(root, "temp.jpg");

                src_img=Highgui.imread(picturePath,Imgproc.COLOR_RGB2GRAY);
                Log.e("picPath is  ",picturePath+"");
                if (!src_img.empty())
                    Log.e("not","empty");



                Log.e("after","COLOR_RGB2GRAY");


                src_grey=new Mat(src_img.size(), CvType.CV_8UC4);
                Log.e("after","CV_8UC4_1");

                src_blur=new Mat(src_img.size(), CvType.CV_8UC4);
                Log.e("after","CV_8UC4_blur");

                src_thresh=new Mat(src_img.size(), CvType.CV_8UC4);
                Log.e("after","CV_8UC4_thresh");

                src_erode=new Mat(src_img.size(), CvType.CV_8UC4);

                src_dilate=new Mat(src_img.size(), CvType.CV_8UC4);
                Log.e("after","CV_8UC4_dilate");

                dest_img=Mat.zeros(640,480, CvType.CV_8UC3);
                Log.e("after","CV_8UC4_zeros");

                Core.bitwise_not(dest_img, dest_img);
                Log.e("after","bitwise");

                Highgui.imwrite("dest.jpg", dest_img);
                Log.e("after","dest");

                Imgproc.cvtColor(src_img, src_grey, Imgproc.COLOR_RGB2GRAY);
                Log.e("after","cvtcolor");

                Imgproc.GaussianBlur(src_grey, src_blur, new Size(5, 5), 0);
                Log.e("after","gaussian");

                Imgproc.threshold(src_blur, src_thresh, 80, 255, Imgproc.THRESH_BINARY_INV);
                Log.e("after","thresh");

               //Imgproc.erode(src_thresh, src_erode, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5)));

                Imgproc.dilate(src_thresh, src_dilate, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5)));
                Log.e("after","dilate");

                Highgui.imwrite("Threshold.jpg", src_thresh);
                Log.e("after","thresh.jpg");

                Highgui.imwrite("Dilate.jpg", src_dilate);
                Log.e("after","dilate.jpg");

                Core.bitwise_not(src_dilate, src_dilate);

                File filee = new File(root, "Final.jpg");

                Highgui.imwrite(filee.getAbsolutePath(), src_dilate);

              /* List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

                Mat heirarchy= new Mat();

                Point shift=new Point(150,0);
                Log.e("after","point");

                Imgproc.findContours(src_dilate, contours,heirarchy, Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE,shift);
                Log.e("after","find contours");

                double[] cont_area =new double[contours.size()];

                for(int i=0; i< contours.size();i++)
                {
                    Log.e("inside","for");
                    Rect rect = Imgproc.boundingRect(contours.get(i));
                    cont_area[i]=Imgproc.contourArea(contours.get(i));

                    System.out.println("Hight: "+rect.height);
                    System.out.println("WIDTH: "+rect.width);
                    System.out.println("AREA: "+cont_area[i]);
                    //System.out.println(rect.x +","+rect.y+","+rect.height+","+rect.width);

                    Core.rectangle(src_img, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(0,0,255));
                    Imgproc.drawContours(dest_img, contours, i, new Scalar(0,0,0),-1,8,heirarchy,2,shift);
                    Core.rectangle(dest_img, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(0,255,0));
                }
                //File roott = Environment.getExternalStorageDirectory();
                File filee = new File(root, "Final.jpg");

                Highgui.imwrite(filee.getAbsolutePath(), dest_img);
              */  Log.e("after","final");
              //  Highgui.imwrite("Original.jpg", src_img);
                Log.e("after","final");
            }
        }
    }

    public void inspect() throws FileNotFoundException {

        try {
            Log.e("inside","insepect");
            File root = Environment.getExternalStorageDirectory();
            File filee = new File(root, "Final.jpg");
            FileInputStream is = new FileInputStream(filee.getAbsolutePath());

            BufferedInputStream buf = new BufferedInputStream(is);
            byte[] bMapArray = new byte[buf.available()];
            buf.read(bMapArray);

            //is = getContentResolver().openInputStream(uri);
            Options options = new Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inSampleSize = 2;
            options.inScreenDensity = DisplayMetrics.DENSITY_LOW;
            Bitmap bMap = BitmapFactory.decodeByteArray(bMapArray,0,bMapArray.length,options);
            inspectFromBitmap(bMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inspectFromBitmap(Bitmap bitmap) {
        //baseAPI.setPageSegMode(TessBaseAPI.AVS_MOST_ACCURATE);
        baseAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);
        baseAPI.setImage(bitmap);
        textt = baseAPI.getUTF8Text();
        /*Toast.makeText(this, text, Toast.LENGTH_LONG).show();*/
        Log.e("hope it works ",textt);

        Intent newActivity = new Intent(MainActivity.this,text_show.class);
        newActivity.putExtra("deepali",textt);
        startActivity(newActivity);

        bitmap.recycle();
    }

    private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);

                File externalStorageDirectory = Environment.getExternalStorageDirectory();

                File appDir = new File(externalStorageDirectory, "ocrsample");


                final File baseDir = new File(appDir, "tessdata");

                File outFile = new File(baseDir, filename);
                if(!outFile.isDirectory()) {
                    out = new FileOutputStream(outFile);
                    copyFile(in, out);
                }
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        Log.e("sorry","sorry");
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.e("sorry","sorry");
                    }
                }
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = 1536;
        int height = 2560;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

}


