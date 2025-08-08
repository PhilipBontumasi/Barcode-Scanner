package com.example.barcodescanner;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    Button btnSubmit, btnCancel;
    EditText edtName, edtEmail, edtPassword, edtAge;
    RadioGroup rgUser, rgGender;
    String userID;

    private FirebaseAuth mAuth;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    RegisterFragmentListener mListener;
    private final String TAG = "RegisterFragment";

    public RegisterFragment() {
        // Required empty public constructor
    }

    interface RegisterFragmentListener {
        void replaceWithItemDetailsFragment();
        void replaceWithCreatorFragment();
        void gotoLogin();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (RegisterFragmentListener) context;
        Log.d(TAG, "onAttach: ");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment RegisterFragment.
     */
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        rgUser = view.findViewById(R.id.radioGroupAccountType);
        rgGender = view.findViewById(R.id.radioGroupGender);

        edtName = view.findViewById(R.id.edtName);
        edtAge = view.findViewById(R.id.edtAge);
        edtEmail = view.findViewById(R.id.edtEmailAddressR);
        edtPassword = view.findViewById(R.id.edtPasswordR);

        btnCancel = view.findViewById(R.id.btnCancel);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    mAuth = FirebaseAuth.getInstance();
                    String name = edtName.getText().toString();
                    String email = edtEmail.getText().toString();
                    String password = edtPassword.getText().toString();
                    String age = edtAge.getText().toString();
                    RadioButton selectedUserType = view.findViewById(rgUser.getCheckedRadioButtonId());
                    RadioButton selectedGender = view.findViewById(rgGender.getCheckedRadioButtonId());
                    RegisterAccount(name,email,password, Integer.parseInt(age),selectedUserType.getText().toString() ,selectedGender.getText().toString() );
                    //new RegisterAsyncTask().execute(name, email, passWord);

                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoLogin();
            }
        });

        return view;
    }


    /**
     * This method is used to check if an input is valid, if it is not it will send a Toast
     * about what is causing the error
     *
     * @return Returns a Boolean of whether the values for account are valid or not
     */
    public Boolean validate() {
        //Declares the strings
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String name = edtName.getText().toString();
        String age = edtAge.getText().toString();

        //Checks name
        if (name.replaceAll("\\s", "").isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.msgNameErrorEmpty), Toast.LENGTH_SHORT).show();
            return false;
        }

        //Checks email
        if (email.replaceAll("\\s", "").isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.msgEmailErrorEmpty), Toast.LENGTH_SHORT).show();
            return false;
        }

        //Checks password
        if (password.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.msgPasswordErrorEmpty), Toast.LENGTH_SHORT).show();
            return false;
        }

        //Checks age
        if (age.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.msgAgeErrorEmpty), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void RegisterAccount(String name, String email, String password, int age, String userType, String genderType) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            userID = user.getUid();
                            Map<String, Object> useracc = new HashMap<>();
                            useracc.put("age", age);
                            useracc.put("userType", userType);
                            useracc.put("genderType" , genderType);
                            db.collection("users").document(userID).set(useracc);
                            Log.d("AccountWrite",userID + useracc.toString());
                            if(userType.equals("User")){
                                mListener.replaceWithItemDetailsFragment();
                            } else {
                                mListener.replaceWithCreatorFragment();
                            }

                            user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();

                            user.updateProfile(profileUpdates);

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG , "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });

    }
}