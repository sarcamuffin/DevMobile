package com.example.contacts;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contacts.R;

public class MainActivity extends AppCompatActivity {
    private TextView result;
    private Button contactId;
    private Button contactDetails;
    private Button call;
    private static final int Perm_CTC = 1;
    private static final int PICK_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactId = (Button) findViewById(R.id.contactId);
        contactDetails = (Button) findViewById(R.id.contactDetail);
        call = (Button) findViewById(R.id.call);
        result = (TextView) findViewById(R.id.result);

        contactDetails.setEnabled(false);
        call.setEnabled(false);

        contactId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check for permission
                showCotactList();

            }
        });


        contactDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the contact ID from the TextView
                String contactId = result.getText().toString();
                // Query the contacts database
                Cursor cursor = getContentResolver().query(Uri.parse(contactId), null, null, null, null);
                // Check if a contact was selected
                if (cursor.moveToFirst()) {
                    // Get the contact name
                    int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    if (nameIndex >= 0) {
                        String name = cursor.getString(nameIndex);
                        // Get the contact phone number
                        int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                        if (idIndex >= 0) {
                            String id = cursor.getString(idIndex);
                            Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                                            ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
                                    new String[]{id},
                                    null);
                            String phone = "";
                            if (cursorPhone.moveToFirst()) {
                                int phoneIndex = cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                                if (phoneIndex >= 0) {
                                    phone = cursorPhone.getString(phoneIndex);
                                }
                            }
                            cursorPhone.close();
                            // Display the contact name and phone number
                            result.setText(name + ": " + phone);
                        }
                    }
                }
                cursor.close();
                call.setEnabled(true);
            }

        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();

            }
        });


    }
    public void call(){
        // Get the contact phone number from the TextView
        String phoneNumber = result.getText().toString().split(":")[1];
        // Create the intent to call the number
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        // Check for permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, Perm_CTC);
        } else {
            // Make the call
            startActivity(callIntent);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//check the permission type using the requestCode
        if (requestCode == Perm_CTC) {
//the array is empty if not granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
                startActivityForResult(pickContactIntent, PICK_REQUEST);
                Toast.makeText(this, "GRANTED permission CALL",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_REQUEST && resultCode == RESULT_OK) {

            if (data == null) {
                result.setText("Operation annulée.");
            } else {

                Uri contactUri = data.getData();
                result.setText(contactUri.toString());

                contactDetails.setEnabled(true);

            }
        }
    }
    private void showCotactList() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, Perm_CTC);
        }
        else {
            // Open contact picker
            Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
            startActivityForResult(pickContactIntent, PICK_REQUEST);
        }
    }
}
