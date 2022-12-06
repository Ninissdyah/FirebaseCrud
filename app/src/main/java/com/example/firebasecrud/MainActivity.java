 package com.example.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

 public class MainActivity extends AppCompatActivity {
    EditText mNameEditText;
    EditText mAddressEditText;

    EditText mUpdateNameEditText;
    EditText mUpdateAddressEditText;

    //memasukkan data ke firebase
    DatabaseReference mDatabaseReferenceStudent, mDatabaseReferenceTeacher;
    Student mStudent;
    Teacher mTeacher;
    String key, key2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //memanggil data di class student
        mDatabaseReferenceStudent = FirebaseDatabase.getInstance().getReference(Student.class.getSimpleName());
        //memanggil data di class teacher
        mDatabaseReferenceTeacher = FirebaseDatabase.getInstance().getReference(Teacher.class.getSimpleName());


        mNameEditText = findViewById(R.id.name_edittext);
        mAddressEditText = findViewById(R.id.address_edittext);
        mUpdateNameEditText = findViewById(R.id.update_name_edittext);
        mUpdateAddressEditText = findViewById(R.id.update_address_edittext);

        findViewById(R.id.insert_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });

        findViewById(R.id.read_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readData();
            }
        });

        findViewById(R.id.update_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

        findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });

        findViewById(R.id.insert_btn_teacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDataTeacher();
            }
        });

        findViewById(R.id.read_btn_teacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readDataTeacher();
            }
        });

        findViewById(R.id.update_btn_teacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataTeacher();
            }
        });

        findViewById(R.id.delete_btn_teacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDataTeacher();
            }
        });
    }

    private void insertData(){
       Student newStudent = new Student();
       String name = mNameEditText.getText().toString();
       String address = mAddressEditText.getText().toString();
       if (name != "" && address != "") {
           newStudent.setName(name);
           newStudent.setAddress(address);

           mDatabaseReferenceStudent.push().setValue(newStudent);
           Toast.makeText(this, "Successfully insert Student data!", Toast.LENGTH_SHORT).show();

       }

    }

    private void insertDataTeacher(){
        Teacher newTeacher = new Teacher();
        String name = mNameEditText.getText().toString();
        String address = mAddressEditText.getText().toString();
        if (name !="" && address !="") {
            newTeacher.setName(name);
            newTeacher.setAddress(address);

            mDatabaseReferenceTeacher.push().setValue(newTeacher);
            Toast.makeText(this, "Successfully insert Teacher data!", Toast.LENGTH_SHORT).show();
        }
    }

    private void readData() {
        mStudent = new Student();
        mDatabaseReferenceStudent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.hasChildren()) {
                   for (DataSnapshot currentData : snapshot.getChildren()) {
                       key = currentData.getKey();
                       mStudent.setName(currentData.child("name").getValue().toString());
                       mStudent.setAddress(currentData.child("address").getValue().toString());
                   }
               }

               mUpdateNameEditText.setText(mStudent.getName());
               mUpdateAddressEditText.setText(mStudent.getAddress());
                Toast.makeText(MainActivity.this, "Data has been shown!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

     private void readDataTeacher() {
         mTeacher = new Teacher();
         mDatabaseReferenceTeacher.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 if (snapshot.hasChildren()) {
                     for (DataSnapshot currentData : snapshot.getChildren()) {
                         key2 = currentData.getKey();
                         mTeacher.setName(currentData.child("name").getValue().toString());
                         mTeacher.setAddress(currentData.child("address").getValue().toString());
                     }
                 }

                 mUpdateNameEditText.setText(mTeacher.getName());
                 mUpdateAddressEditText.setText(mTeacher.getAddress());
                 Toast.makeText(MainActivity.this, "Teacher data has been shown!", Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
     }

    private void updateData() {
        Student updateData = new Student();
        updateData.setName(mUpdateNameEditText.getText().toString());
        updateData.setAddress(mUpdateAddressEditText.getText().toString());

        //untuk update
        mDatabaseReferenceStudent.child(key).setValue(updateData);



    }

     private void updateDataTeacher() {
         Teacher updateData = new Teacher();
         updateData.setName(mUpdateNameEditText.getText().toString());
         updateData.setAddress(mUpdateAddressEditText.getText().toString());

         //untuk update
         mDatabaseReferenceTeacher.child(key2).setValue(updateData);



     }

    void deleteData() {
        //untuk delete
        mDatabaseReferenceStudent.child(key).removeValue();
    }

     void deleteDataTeacher() {
         //untuk delete
         mDatabaseReferenceTeacher.child(key2).removeValue();
     }
}