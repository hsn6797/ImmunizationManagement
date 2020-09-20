package com.example.immunizationmanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immunizationmanagement.DataBase.DataSource;
import com.example.immunizationmanagement.Model.Baby;
import com.example.immunizationmanagement.Model.Gender;
import com.example.immunizationmanagement.R;
import com.example.immunizationmanagement.Utills.Function;
import com.example.immunizationmanagement.Utills.ImageFactory;
import com.example.immunizationmanagement.Utills.SString;


import java.util.Calendar;
import java.util.Date;

public class AddBabyActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 1;

    TextView titleText;
    ImageView babyImage;
    Button selectImage;
    Button selectDob;
    Button saveBaby;
    EditText babyName;
    TextView babyDob;

    RadioButton babyBoy;
    RadioButton babyGirl;

    int mYear,mMonth,mDay;
    Gender gender = Gender.M;
    Calendar c = null;
    String selectedImagePath = null;

    String action = "Add";
    String id = "0";

    DataSource ds = null;
    private Baby baby;

    // user defined Methods
    void init(){
        babyImage = (ImageView) findViewById(R.id.babyIV);
        selectImage = (Button) findViewById(R.id.babyIVB);
        selectDob = (Button) findViewById(R.id.babyDobB);
        saveBaby = (Button) findViewById(R.id.babySaveB);
        babyName = (EditText) findViewById(R.id.babyNameET);
        babyDob  = (TextView) findViewById(R.id.babyDobTV);

        babyBoy  = (RadioButton) findViewById(R.id.babyMaleRB);
        babyGirl  = (RadioButton) findViewById(R.id.babyFemaleRB);
        babyBoy.setChecked(true);

        titleText = (TextView) findViewById(R.id.titleTextTV);


        Drawable placeHolder = AddBabyActivity.this.getResources().getDrawable( R.drawable.ic_person_black_24dp );
        babyImage.setImageDrawable(placeHolder);

        ds = new DataSource(AddBabyActivity.this);

    }




//  TODO -  Move this code to baby details page

//    private void getImages() {
//        String[] filenames = new String[0];
//        File pictureDirPath = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        File path = new File(pictureDirPath + "/VaccinationImages");
//        if (path.exists()) {
//            filenames = path.list();
//        }
//        ArrayList<String> imagesPathArrayList = new ArrayList<>();
//
//        for (int i = 0; i < filenames.length; i++) {
//            imagesPathArrayList.add(path.getPath() + "/" + filenames[i]);
//            Log.e("FAV_Images", imagesPathArrayList.get(i));
//            ///Now set this bitmap on imageview
//        }
//    }


    private void saveBaby(Baby baby){

        // Save or Edit in database
        if(!action.equals("Edit")){
            // Add Baby
            baby.addBaby(AddBabyActivity.this);

        }else{
            // Edit Baby
            baby.editBaby(AddBabyActivity.this);
        }

        // Save image in phone storage
        saveImageInStorage(selectedImagePath,baby.getId());


    }
    private void saveImageInStorage(String selectedImagePath,String imageName){

        if(!ImageFactory.saveImage(selectedImagePath,imageName)){
            // If image not save successfully
            if(!action.equals("Edit")){
                ds.DeleteBaby(id+"");
                Toast.makeText(AddBabyActivity.this,"Baby not added",Toast.LENGTH_SHORT).show();
            }else{
//                Toast.makeText(AddBabyActivity.this,"Baby image not updated",Toast.LENGTH_SHORT).show();
            }
        }
//        SFile imageFile = new SFile(selectedImagePath);
//        String extention = "jpg";
////        String extention = imageFile.getExtention();
//
//
//        File pictureDirPath = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        SFile imageSaveDir = new SFile(pictureDirPath,"VaccinationImages");
//
//
//        SFile newImageFile = new SFile(imageSaveDir,imageName+"."+extention);

//        try {
//            FileUtils.copyFile(imageFile,newImageFile);
//
//        }catch (Exception ex){
//            if(!action.equals("Edit")){
//                ds.DeleteBaby(id+"");
//                Toast.makeText(AddBabyActivity.this,"Baby not added",Toast.LENGTH_SHORT).show();
//            }else{
////                Toast.makeText(AddBabyActivity.this,"Baby image not updated",Toast.LENGTH_SHORT).show();
//            }
//            ex.printStackTrace();
//        }

    }
    private void getCurrentDate(){
        c = Calendar.getInstance();
        int Year = c.get(Calendar.YEAR);
        int Month = c.get(Calendar.MONTH);
        int Day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddBabyActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        babyDob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        mDay = dayOfMonth;
                        mMonth = monthOfYear;
                        mYear = year;
                    }
                }, Year, Month, Day);
        datePickerDialog.show();
    }


    // Get the image from gallery
    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg"};
//        , "image/png"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);

    }
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();

                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    babyImage.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    selectedImagePath = imgDecodableString;

                    Log.d("Path:", selectedImage.toString());
//                    Log.d("Path:", imgDecodableString);

                    break;

//                     TODO -  transfer this on baby details page
//                    SFile image = getImage("6");
//                    Bitmap bitmap =  BitmapFactory.decodeFile(image.getAbsolutePath());
//                    Log.d("Path:", bitmap.toString());
//                    babyImage.setImageBitmap(bitmap);

            }
        }

    }


    // Activity lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baby);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        // get id from previous Activity
        Bundle extras = getIntent().getExtras();
        if(extras != null){

            action = extras.getString("Action");
            id = extras.getString("Id");

            if(action.equals("Edit")){

                // change the Title text to Edit baby
                titleText.setText("Edit Baby");
                AddBabyActivity.this.setTitle("Edit Baby");


                // Get baby Object from Database
                baby = ds.getBaby(id);

                // assign initial values
                babyName.setText(baby.getName());
                babyDob.setText(Function.timestampToString(baby.getDob()));

                if(baby.getGender().toString().equalsIgnoreCase("M")){
                    babyBoy.setChecked(true);
                    gender = baby.getGender();
                }else{
                    babyGirl.setChecked(true);
                    gender = baby.getGender();
                }

                c = Calendar.getInstance();
                c.setTimeInMillis(baby.getDob());

                mDay = c.get(Calendar.DAY_OF_MONTH);
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);

                Log.d("Date:", mDay+"-"+mMonth+"-"+mYear);


                if(ImageFactory.getImage(id) != null){
                    selectedImagePath = ImageFactory.getImage(id).getPath();
                    babyImage.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
                }


            }
        }else{
            // change the Title text to Add baby
            titleText.setText("Add Baby");
            AddBabyActivity.this.setTitle("Add Baby");

            baby = new Baby();
        }


        //select Image Event
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // get the image from gallery
                pickFromGallery();

            }
        });

        //select DOB Event
        selectDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                getCurrentDate();
            }
        });

        //Save Baby Event
        saveBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long dob = Function.DateToTimeStamp(mDay,mMonth,mYear);
                Date current = new Date();
                if(dob > current.getTime() ){
                    Toast.makeText(AddBabyActivity.this,"You selected the future Date",Toast.LENGTH_SHORT).show();
                    return;
                }


                if(babyName.getText().length() == 0){
                    Toast.makeText(AddBabyActivity.this,"Enter baby name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedImagePath == null){
                    Toast.makeText(AddBabyActivity.this,"Select baby Image",Toast.LENGTH_SHORT).show();
                    return;
                }

                baby.setName(SString.toTitleCase(babyName.getText().toString().trim()));
                baby.setDob(dob);
                baby.setGender(gender);
                baby.setImageName("NULL");

                saveBaby(baby);

                finish();
//                Log.d("Baby", ds.getBaby("2").toString());
            }
        });

        //Radio Buttons Events
        babyBoy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    gender = gender.M;
                }
            }
        });
        babyGirl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    gender = Gender.F;

                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
